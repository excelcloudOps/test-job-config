# test-job-config

Demo project that builds a Maven library and publishes the JAR to a Nexus **raw** repository from GitHub Actions.

## What it does

- Builds and tests `com.excelcloud.demo:sample-lib`
- On pushes to `main` (and manual runs), uploads the JAR to Nexus `raw-release`
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
| `NEXUS_RAW_REPO` | `raw-release` | Hosted raw repository name |

Grant the deploy user write access to that raw hosted repo.

## Local build

```bash
mvn -B clean verify
```

## Publish path

Artifacts are uploaded via the Nexus Components API to:

```text
{NEXUS_URL}/repository/raw-release/com/excelcloud/demo/sample-lib/{version}/sample-lib-{version}.jar
```

## Workflow

`.github/workflows/publish-nexus.yml`

- Trigger: push/`workflow_dispatch` publish; PRs verify only
- Manual run can override the version (e.g. `1.0.1`)

## Artifact coordinates

- Group: `com.excelcloud.demo`
- Artifact: `sample-lib`
- Default version: `1.0.0`
- Nexus repo: `raw-release`

## Troubleshooting

### `403 Forbidden` on upload

1. Confirm `raw-release` exists as a **hosted raw** repository
2. Give `NEXUS_USERNAME` write privileges on that repo
3. Keep `NEXUS_URL` as the Nexus base URL only (no `/repository/...` suffix)
