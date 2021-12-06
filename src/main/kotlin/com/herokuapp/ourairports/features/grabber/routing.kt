package com.herokuapp.ourairports.features.grabber

import com.herokuapp.ourairports.data.s3storage.S3DataStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.io.File

fun Application.updateRemoteFiles() {

    val grabber: AirportsGrabber by inject()
    val s3DataStorage: S3DataStorage by inject()

    routing {
        get("/api/v1/updatebase") {
            grabber.grabToDatabase(FilesLinks.files, FilesLinks.BASE_URL)
            call.respond(HttpStatusCode.OK, "Downloading and mapping started")
        }

        get("/api/v1/backup") {
            if (File("files/database_v1.mv.db").exists()
                && s3DataStorage.putS3Object(
                    objectKey = "database_v1.mv.db",
                    objectPath = "files/database_v1.mv.db"
                )) {
                call.respond(HttpStatusCode.OK, "Database back up was deployed on Amazon S3 bucket")
            } else {
                call.respond(HttpStatusCode.NoContent, "Database file is not exists!")
            }
        }
    }
}
