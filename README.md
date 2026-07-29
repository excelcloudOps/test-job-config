# test-job-config

Demo project that builds a Maven library and publishes the JAR to a Nexus **raw** repository from GitHub Actions.

## What it does

- Builds and tests `com.excelcloud.demo:sample-lib`
- On pushes to `main` (and manual runs), uploads the JAR to Nexus `raw-releases`
- After a successful publish, posts a notification to Slack
- Pull requests build and test only (no publish)

## Required GitHub Actions secrets

Already configured on this repo:

| Secret | Purpose |
| --- | --- |
| `NEXUS_URL` | Nexus base URL, e.g. `https://nexus.example.com` (no trailing slash needed) |
| `NEXUS_USERNAME` | Nexus deploy user |
| `NEXUS_PASSWORD` | Nexus deploy password / token |
| `SLACK_WEBHOOK_URL` | Slack Incoming Webhook URL for publish success notifications |

## Optional repository variables

| Variable | Default | Purpose |
| --- | --- | --- |
| `NEXUS_RAW_REPO` | `raw-releases` | Hosted raw repository name |

Grant the deploy user write access to that raw hosted repo.

## Local build

```bash
mvn -B clean verify
```

## Publish path

Artifacts are uploaded via the Nexus Components API to:

```text
{NEXUS_URL}/repository/raw-releases/com/excelcloud/demo/sample-lib/{version}/sample-lib-{version}.jar
```

## Workflow

`.github/workflows/publish-nexus.yml`

- Trigger: push/`workflow_dispatch` publish; PRs verify only
- Manual run can override the version (e.g. `1.0.1`)
- `notify-slack` runs only after a successful non-PR publish

## Artifact coordinates

- Group: `com.excelcloud.demo`
- Artifact: `sample-lib`
- Default version: `1.0.0`
- Nexus repo: `raw-releases`

## Troubleshooting

### `403 Forbidden` on upload

1. Confirm `raw-releases` exists as a **hosted raw** repository
2. Give `NEXUS_USERNAME` write privileges on that repo
3. Keep `NEXUS_URL` as the Nexus base URL only (no `/repository/...` suffix)

### Slack notification skipped or failed

1. Add repo secret `SLACK_WEBHOOK_URL` from a Slack Incoming Webhook
2. Confirm the webhook still targets the intended channel
