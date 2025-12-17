# Selenium TestNG Experiments
Practical Selenium + TestNG project with a single config file, hardened checkout flow (overlay/stale handling), and parallel execution setup.

## What’s inside
- `BaseTest` with ThreadLocal WebDriver, safe teardown, CLI override (`-Dbrowser=...`).
- `BasePage` helpers: `waitVisible`, `waitClickable`, `waitInvisible`, `clickWhenClickable`, `type`, plus text wait helpers.
- `DriverManager` for Chrome/Firefox and a single config file (`config.properties`).
- Checkout flow waits/retries to avoid overlay and stale element issues.
- Parallel execution via TestNG suite (`testng.xml`) and Maven Surefire config.

## Quick start
1) Update `src/test/resources/config.properties` with `baseUrl`, `username`, `password`, `browser`, `implicitWaitSeconds`, `explicitWaitSeconds`.
2) Run suite (parallel) with Maven: `mvn test` (uses Surefire’s parallel settings) or right-click `testng.xml` in IntelliJ.
3) To add tests/pages: put page objects under `src/test/java/.../pages` and tests under `.../tests`.

## Notes
- Keep secrets local; override via `-D` if needed (e.g., `-Dbrowser=firefox`).
- `.gitignore` filters IDE noise and build output.
