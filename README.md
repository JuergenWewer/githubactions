# quarkus-lib-example

Kleines Java **Library**-Projekt (Maven), ideal um GitHub-Workflows (Build, Test, Sonar, Publish) zu testen.
Es erzeugt ein **Jar**, das in anderen Projekten eingebunden werden kann.

## Build
```bash
mvn -B -DskipTests=false clean package
```
Artefakt: `target/quarkus-lib-example-0.1.0.jar`

## Nutzung (lokal)
```xml
<dependency>
  <groupId>org.example</groupId>
  <artifactId>quarkus-lib-example</artifactId>
  <version>0.1.0</version>
</dependency>
```

## GitHub Packages (optional)
1) In `pom.xml` den Block `<distributionManagement>` aktivieren und `OWNER/REPO` anpassen.
2) Im Workflow `actions/setup-java@v4` mit `server-id: github` nutzen und `GITHUB_TOKEN` als Credentials übergeben.
3) `mvn deploy` ausführen (per Workflow oder lokal mit Settings).

## Quarkus-Hinweis
Die Bibliothek ist **Quarkus-freundlich**: `GreetingService` ist mit `@Singleton` annotiert. In Quarkus-Projekten kann der Service per CDI injiziert werden, in normalen Java-Projekten ganz regulär instanziiert.
