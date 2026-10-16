# VersatiaExample-DI-SharedBeans

Two plugins: a provider exposing a singleton and a prototype, and a consumer that lists it under `depend:` and injects both.

Part of the [Versatia examples](https://github.com/VersatiaFramework/VersatiaExamples); documentation: [Beans across plugins](https://versatiaframework.github.io/VersatiaDocs/docs/dependency-injection/cross-plugin).

**Start with** [`provider/.../SharedBeans.kt`](provider/src/main/kotlin/com/github/marcoral/versatia/example/sharedbeans/SharedBeans.kt), then [`consumer/.../Report.kt`](consumer/src/main/kotlin/com/github/marcoral/versatia/example/sharedbeans/consumer/Report.kt).

## What the log shows

```
[VersatiaExample-DI-SharedBeans-Provider] Clock created, instance 1234567
[VersatiaExample-DI-SharedBeans-Provider] Session #1 created for VersatiaExample-DI-SharedBeans-Provider
[VersatiaExample-DI-SharedBeans-Provider] ProviderReport: clock instance 1234567, session #1
[VersatiaExample-DI-SharedBeans-Consumer] Session #2 created for VersatiaExample-DI-SharedBeans-Consumer
[VersatiaExample-DI-SharedBeans-Consumer] Report: clock instance 1234567, same as the provider's; session #2, made for VersatiaExample-DI-SharedBeans-Consumer
```

## Things to try

- Change the `Session` parameter to `ProviderPlugin`: the build fails, because a prototype receives
  the plugin it is made for and not every plugin is a `ProviderPlugin`.
- Inject `ProviderReport` into `Report`: it compiles (the class is visible) but the consumer fails to
  enable with `no bean provides ... ProviderReport`, because local beans are not shared.
- Stop the server: the consumer, which depends on the provider, is disabled first, so the exposed
  beans outlive every plugin using them. Disabling only the provider at runtime, with a plugin
  manager, does not disable the consumer.

## Building

Requires JDK 21 and VersatiaStarter with what it exports (VersatiaAPI, the module APIs) in your
local Maven repository (`./gradlew publishToMavenLocal` in each checkout; see
[Building the framework](https://versatiaframework.github.io/VersatiaDocs/docs/contributing/building));
running it requires a Paper 1.21.4 server with VersatiaCore installed.

```bash
./gradlew build
```

To have the JAR copied to your server after every build, copy `local.properties.example` to
`local.properties` and set `pluginsDir`. The generated descriptor is in
`build/generated/ksp/main/kotlin`; open it to see exactly what the runtime executes.

## Layout

Two Gradle modules, one plugin each. The consumer compiles against the provider with
`compileOnly(project(":provider"))`; on the server it gets the classes through `depend:`.
