package com.github.marcoral.versatia.example.sharedbeans

import com.github.marcoral.versatia.VersatiaPlugin
import com.github.marcoral.versatia.di.ExposePrototype
import com.github.marcoral.versatia.di.ExposeSingleton
import com.github.marcoral.versatia.di.LocalBean
import java.util.concurrent.atomic.AtomicInteger

/** One instance for the whole server; every plugin that depends on this one and asks for a `Clock` gets it. */
@ExposeSingleton
class Clock(plugin: ProviderPlugin) {
    init {
        plugin.logger.info("Clock created, instance ${System.identityHashCode(this)}")
    }
}

/** A fresh instance for every plugin that asks, this one included; `plugin` is the plugin the instance is made for. */
@ExposePrototype
class Session(val plugin: VersatiaPlugin) {
    val id = counter.incrementAndGet()

    init {
        plugin.logger.info("Session #$id created for ${plugin.name}")
    }

    private companion object {
        val counter = AtomicInteger()
    }
}

/** Local: visible to the consumer's compiler, but not injectable there. */
@LocalBean
class ProviderReport(clock: Clock, session: Session, plugin: ProviderPlugin) {
    init {
        plugin.logger.info("ProviderReport: clock instance ${System.identityHashCode(clock)}, session #${session.id}")
    }
}
