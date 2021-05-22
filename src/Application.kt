package com.pandey

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {

        get("/") {
            call.respondText("Hello Ktor")
        }

        post("/user") {

            call.respond(User(1, "saurabh"))
        }

        //get example 2
        get("/user/{id}") {
           val id = call.parameters["id"]
            call.respondText("id is $id")
        }
    }
}

data class User(
    val id: Int,
    val name: String
)
