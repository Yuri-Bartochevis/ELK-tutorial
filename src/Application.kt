package com.bartochevis.tutorial.elk

import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import org.slf4j.event.Level
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    val lorem: Lorem = LoremIpsum.getInstance()
    Scheduler {
        log.trace(lorem.getWords(5, 10))
        log.debug(lorem.getWords(5, 10))
        log.info(lorem.getWords(5, 10))
        log.warn(lorem.getWords(5, 10))
    }.scheduleExecution(Every(3, TimeUnit.SECONDS))

    Scheduler {
        log.error(lorem.getWords(3), Exception("Random Exception"))
    }.scheduleExecution(Every(60, TimeUnit.SECONDS))

}
