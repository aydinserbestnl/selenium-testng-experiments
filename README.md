# Selenium TestNG Template
Simple, reusable starter for Selenium + TestNG with page objects, config loader, and environment switching.

## What’s included
- TestNG lifecycle base (`BaseTest`) with ThreadLocal WebDriver, safe teardown, CLI override (`-Dbrowser=... -Denv=...`).
- Page Object base (`BasePage`) with configurable `baseUrl` and explicit waits.
- Driver factory (`DriverManager`) for Chrome/Firefox, default browser from config.
- Config utilities (`ConfigLoader`, `PropertyUtils`, `EnvType`) loading `stg_config.properties` / `prod_config.properties` from classpath.
- JSON helper (`JacksonUtils`) for fixture loading.

## Quick start
1. Fill `src/test/resources/stg_config.properties` and `prod_config.properties` with your URLs/credentials, or override via CLI.
2. Run: `mvn test -Denv=STAGE -Dbrowser=chrome` (defaults: env=STAGE, browser=chrome).
3. Add your page objects under `src/test/java/.../pages` and tests under `.../tests`.

## Notes
- No hardcoded secrets; config files hold placeholders. Add real values locally or pass via `-D`.
- `.gitignore` excludes IDE noise, build output, and test reports.
