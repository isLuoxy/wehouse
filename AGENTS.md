# Repository Guidelines

## Project Structure & Module Organization
This repository is a Maven multi-module Java 8 project. The root [`pom.xml`](D:\Idea Project\wehouse\pom.xml) aggregates:

- `wehouse-web`: Spring Boot web layer and static assets under `src/main/resources/static`.
- `wehouse-service`: business logic and the service-side Spring Boot entrypoint.
- `wehouse-dao`: MyBatis DAOs and XML mappers in `src/main/resources/mapper`.
- `wehouse-interface`: Dubbo service interfaces.
- `wehouse-pojo`: shared domain models, DTOs, enums, and response objects.
- `wehouse-redis`, `wehouse-map`, `wehouse-utils`: infrastructure and utility modules.

Tests live beside each module under `src/test/java`.

## Build, Test, and Development Commands
- `mvn clean test`: run all module tests from the repository root.
- `mvn clean package`: compile all modules and build Spring Boot artifacts.
- `mvn -pl wehouse-web spring-boot:run`: start the web application locally.
- `mvn -pl wehouse-service spring-boot:run`: start the service provider locally.

Run commands from the repo root unless you are working inside one module only.

## Coding Style & Naming Conventions
Follow the existing Java style: 4-space indentation, `UpperCamelCase` for classes, `lowerCamelCase` for methods and fields, and package names under `cn.l99.wehouse`. Keep controllers in `controller`, service implementations in `service.impl`, DAOs in `dao`, and MyBatis XML mapper names aligned with DAO/domain names such as `HouseMapper.xml`.

No formatter or linter configuration is checked in, so match surrounding code closely and keep methods small and explicit.

## Testing Guidelines
The project uses JUnit 4 with Spring test support (`@RunWith(SpringRunner.class)`, `@SpringBootTest`). Name test classes with the `*Test` suffix, for example `UserServiceImplTest`. DAO tests use `AbstractTest` and open real MyBatis sessions, so contributors should expect local database access. Service and web tests may also require MySQL, Redis, and Dubbo/ZooKeeper settings from `application.yml`.

## Commit & Pull Request Guidelines
Recent history mixes short imperative summaries in English and Chinese, for example `Bump com.alibaba:fastjson...` and `完成用户接口、通用接口开发工作`. Keep commits focused, one change per commit, and use a concise subject line that states the behavior changed.

For pull requests, include:

- a short problem/solution summary,
- affected modules,
- config or data setup needed to verify,
- screenshots or API examples when `wehouse-web` behavior changes.

## Configuration Tips
Do not commit real secrets or environment-specific hosts. Review `application.yml` before running locally; several profiles reference external Redis and ZooKeeper endpoints.
