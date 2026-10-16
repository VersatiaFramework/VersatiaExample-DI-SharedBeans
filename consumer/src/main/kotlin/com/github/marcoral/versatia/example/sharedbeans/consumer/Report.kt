package com.github.marcoral.versatia.example.sharedbeans.consumer

import com.github.marcoral.versatia.di.LocalBean
import com.github.marcoral.versatia.example.sharedbeans.Clock
import com.github.marcoral.versatia.example.sharedbeans.Session

/** `Clock` is the provider's instance; `Session` is a new one made for this plugin, and it knows it. */
@LocalBean
class Report(clock: Clock, session: Session, plugin: ConsumerPlugin) {
    init {
        plugin.logger.info("Report: clock instance ${System.identityHashCode(clock)}, same as the provider's; session #${session.id}, made for ${session.plugin.name}")
    }
}
