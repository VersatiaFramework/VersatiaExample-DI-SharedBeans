package com.github.marcoral.versatia.example.sharedbeans.consumer

import org.bukkit.plugin.java.JavaPlugin

/** Logs through the plugin that owns the receiver's class, prefixed with the class name. */
internal fun Any.log(message: String) = JavaPlugin.getProvidingPlugin(javaClass).logger.info("[${javaClass.simpleName}] $message")
