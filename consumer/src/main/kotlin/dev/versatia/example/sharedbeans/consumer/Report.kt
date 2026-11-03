package dev.versatia.example.sharedbeans.consumer

import dev.versatia.di.LocalBean
import dev.versatia.example.sharedbeans.Clock
import dev.versatia.example.sharedbeans.Session
import java.util.logging.Logger

/** `Clock` is the provider's instance; `Session` is a new one made for this plugin, and it knows it. */
@LocalBean
class Report(clock: Clock, session: Session, logger: Logger) {
    init {
        logger.info("Report: clock instance ${System.identityHashCode(clock)}, same as the provider's; session #${session.id}, made for ${session.plugin.name}")
    }
}
