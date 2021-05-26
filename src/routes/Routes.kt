package com.pandey.routes

import com.pandey.API_VERSION
import com.pandey.repository.StudentRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

const val CREATE_STUDENT = "$API_VERSION/student"

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(CREATE_STUDENT)
class CreateStudent


@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.student(db: StudentRepository) {

    post<CreateStudent> {
        val parameter = call.receive<Parameters>()

        val name =
            parameter["name"] ?: return@post call.respondText("MISSING FIELD", status = HttpStatusCode.Unauthorized)

        val age =
            parameter["age"] ?: return@post call.respondText("MISSING FIELD", status = HttpStatusCode.Unauthorized)


        try {

            val student = db.insert(name, age.toInt())

            student?.userId?.let {
                call.respond(status = HttpStatusCode.OK, student)
            }

        } catch (e: Throwable) {

            call.respondText("${e.message}")
        }

    }

    get<CreateStudent> {

        try {
            val studentList = db.getAllStudent()
            call.respond(status = HttpStatusCode.OK, studentList)

        } catch (e: Throwable) {

            call.respondText("${e.message}")
        }

    }


    delete("$API_VERSION/student/{id}") {

        val id = call.parameters["id"] ?: return@delete call.respondText(
            "id not found",
            status = HttpStatusCode.NotFound
        )

        val result = db.deleteById(id.toInt())

        try{
            if (result==1){
                call.respondText("$id deleted")
            }else{
                call.respondText("$id not found")
            }

        }catch (e: Throwable) {

            call.respondText("${e.message}")
        }


    }


}