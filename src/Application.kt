package com.pandey

import com.pandey.repository.DatabaseFactory
import com.pandey.repository.StudentRepository
import com.pandey.routes.student
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.locations.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    DatabaseFactory.init()
    val db = StudentRepository()

    install(Locations){}
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
/*
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
        }*/

        student(db)
    }
}


const val API_VERSION ="v1/"


data class User(
    val id: Int,
    val name: String
)

