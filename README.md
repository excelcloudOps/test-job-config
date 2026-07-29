# test-job-config

Demo project that builds a Maven library and publishes it to Sonatype Nexus from GitHub Actions.

## What it does

- Builds and tests `com.excelcloud.demo:sample-lib`
- On pushes to `main` (and manual runs), deploys the JAR + POM to Nexus
- Pull requests build and test only (no publish)

## Required GitHub Actions secrets

Already configured on this repo:

| Secret | Purpose |
| --- | --- |
| `NEXUS_URL` | Nexus base URL, e.g. `https://nexus.example.com` (no trailing slash needed) |
| `NEXUS_USERNAME` | Nexus deploy user |
| `NEXUS_PASSWORD` | Nexus deploy password / token |

## Optional repository variables

| Variable | Default | Purpose |
| --- | --- | --- |
| `NEXUS_RELEASES_REPO` | `maven-releases` | Hosted Maven releases repo name |
| `NEXUS_SNAPSHOTS_REPO` | `maven-snapshots` | Hosted Maven snapshots repo name |

Create matching **hosted** Maven 2 repos in Nexus if they do not already exist, and grant the deploy user write access.

## Local build

```bash
export NEXUS_URL=https://your-nexus.example.com
mvn -B clean verify
```

To publish locally, add a `~/.m2/settings.xml` server entry with `id` `nexus` and matching credentials, then:

```bash
export NEXUS_URL=https://your-nexus.example.com
export NEXUS_USERNAME=...
export NEXUS_PASSWORD=...
mvn -B deploy
```

## Workflow

`.github/workflows/publish-nexus.yml`

- Trigger: push/`workflow_dispatch` publish; PRs verify only
- Manual run can override the version (e.g. `1.0.0` for a release, or keep `*-SNAPSHOT` for snapshots)

## Artifact coordinates

- Group: `com.excelcloud.demo`
- Artifact: `sample-lib`
- Default version: `1.0.0-SNAPSHOT` → published to the snapshots repo
