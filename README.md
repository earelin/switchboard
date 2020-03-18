# Switchboard

[![Build Status](https://jenkins-prod.api-platforms.telegraph.co.uk/buildStatus/icon?job=Dashboard%2Fswitchboard%2F1.x.x)](https://jenkins-prod.api-platforms.telegraph.co.uk/job/Dashboard/view/Utils/job/switchboard/job/1.x.x/)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/telegraph/switchboard?sort=semver)

![Switchboard photo by Joseph A. Carr](/media/switchboard.jpg "Switchboard photo by Joseph A. Carr")

Switchboard is a [feature flag](https://martinfowler.com/articles/feature-toggles.html) configuration service.

## Features

- Allows to manage a set of applications with it own feature flags
- Feature flags are enabled or disabled based on a configurable set of rules
- Type of rules
  - Simple enable/disable
  - Enable feature after a date
  - Enable a feature for a group of users
  - Enable a feature for a context

## Development

Run unit tests

```shell script
./gradlew test
```

Run unit and integration tests

```shell script
./gradlew check
```

Run functional tests. Functional test expect by default a running instance listening on 
port 9000.

```shell script
./gradlew functionalTest
```

Build application docker image

```shell script
./gradlew docker
```

Launch testing environment with docker compose

```shell script
./gradlew docker
docker-compose up -d
```
