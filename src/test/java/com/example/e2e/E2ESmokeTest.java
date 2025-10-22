package com.example.e2e;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class E2ESmokeTest {

    static final String TEST_ENV = System.getProperty("test.env", "local");

    static KafkaContainer kafka;
    static LocalStackContainer localstack;
    static OracleContainer oracle;

    @BeforeAll
    static void startDeps() throws Exception {
        if ("k8s".equalsIgnoreCase(TEST_ENV)) {
            System.out.println("Running in k8s smoke mode: using external endpoints.");
            return;
        }

        kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.2"))
                .waitingFor(Wait.forListeningPort());
        kafka.start();

        localstack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:3.6"))
                .withServices(LocalStackContainer.Service.S3)
                .waitingFor(Wait.forLogMessage(".*Ready\\.\n", 1));
        localstack.start();

        oracle = new OracleContainer(DockerImageName.parse("gvenzl/oracle-xe:21-slim"))
                .withUsername("system")
                .withPassword("oracle");
        oracle.start();

        System.setProperty("kafka.bootstrap", kafka.getBootstrapServers());
        System.setProperty("s3.endpoint", localstack.getEndpointOverride(LocalStackContainer.Service.S3).toString());
        System.setProperty("s3.access", localstack.getAccessKey());
        System.setProperty("s3.secret", localstack.getSecretKey());
        System.setProperty("oracle.url", oracle.getJdbcUrl());
        System.setProperty("oracle.user", oracle.getUsername());
        System.setProperty("oracle.pass", oracle.getPassword());
    }

    @Test
    @Order(1)
    void testPipeline() throws Exception {
        // TODO: Replace with real end-to-end verification:
        String url = System.getProperty("oracle.url");
        String user = System.getProperty("oracle.user");
        String pass = System.getProperty("oracle.pass");
        try (Connection c = DriverManager.getConnection(url, user, pass);
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT 1 FROM dual")) {
            assertTrue(rs.next(), "Oracle reachable");
        }

        URI s3 = URI.create(System.getProperty("s3.endpoint"));
        assertTrue(s3.getHost() != null && !s3.getHost().isEmpty(), "S3 endpoint set");
    }

    @AfterAll
    static void stopDeps() {
        if (kafka != null) kafka.stop();
        if (localstack != null) localstack.stop();
        if (oracle != null) oracle.stop();
    }
}