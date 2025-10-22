# SUSE + Maven + Testcontainers + Kubernetes

**Secrets needed in GitHub:**

- `IMAGE_REGISTRY` (e.g. `ghcr.io`)
- `IMAGE_REPO` (e.g. `org/project`)
- `REGISTRY_USER`, `REGISTRY_PASSWORD`
- `KUBECONFIG_B64` (base64 of kubeconfig)

**How to use:**

1. Copy `.github/workflows/suse-testcontainers-ci-k8s.yml` into your repo.
2. Add `pom.testcontainers.snippet.xml` content into your `pom.xml` (or import it).
3. Place `E2ESmokeTest.java` under `src/test/java/com/example/e2e/` and adapt to your service.
4. Adjust `k8s/microservice-deployment.yaml` env vars to your app.
5. Commit, push, run the workflow.

The workflow:
- Runs tests locally with Testcontainers (Kafka, S3 via LocalStack, Oracle XE).
- Builds & pushes the Docker image.
- Deploys Kafka+MinIO via Helm, Oracle XE via manifest, and your microservice into namespace `test`.
- Runs smoke tests against the cluster (`-Pk8s-smoke` profile).