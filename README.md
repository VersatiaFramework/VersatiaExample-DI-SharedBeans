    # VersatiaExample-DI-SharedBeans

    Two plugins: a provider exposing a singleton and a prototype, and a consumer that lists it under `depend:` and injects both.

    Part of the [Versatia examples](https://github.com/VersatiaFramework/VersatiaExamples); documentation: [Beans across plugins](https://versatiaframework.github.io/VersatiaDocs/docs/dependency-injection/cross-plugin).

    **Start with** [`provider/.../SharedBeans.kt`](provider/src/main/kotlin/com/github/marcoral/versatia/example/sharedbeans/SharedBeans.kt), then [`consumer/.../Report.kt`](consumer/src/main/kotlin/com/github/marcoral/versatia/example/sharedbeans/consumer/Report.kt).

    ## What the log shows

    ```
    [Clock] created, instance 1234567
[Session] created session #1 for VersatiaExample-DI-SharedBeans-Provider
[ProviderReport] clock instance 1234567, session #1
[Session] created session #2 for VersatiaExample-DI-SharedBeans-Consumer
[Report] clock instance 1234567, same as the provider's; session #2, made for VersatiaExample-DI-SharedBeans-Consumer
    ```


## Things to try

- Change the `Session` parameter to `ProviderPlugin`: the build fails, because a prototype receives
  the plugin it is made for and not every plugin is a `ProviderPlugin`.
- Inject `ProviderReport` into `Report`: it compiles (the class is visible) but the consumer fails to
  enable with `no bean provides ... ProviderReport`, because local beans are not shared.
- Stop the provider with `/plugman` or similar: the consumer, which depends on it, is disabled
  first, and the exposed beans go with the provider.

## Building

Requires JDK 21 and VersatiaAPI in your local Maven repository (`./gradlew publishToMavenLocal`
in its checkout); running it requires a Paper 1.21.4 server with VersatiaCore installed.

```bash
./gradlew build
```

To have the JAR copied to your server after every build, copy `local.properties.example` to
`local.properties` and set `pluginsDir`. The generated descriptor is in
`build/generated/ksp/main/kotlin`; open it to see exactly what the runtime executes.

## Layout

Two Gradle modules, one plugin each. The consumer compiles against the provider with
`compileOnly(project(":provider"))`; on the server it gets the classes through `depend:`.
