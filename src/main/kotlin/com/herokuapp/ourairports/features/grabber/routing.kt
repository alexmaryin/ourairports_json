package com.herokuapp.ourairports.features.grabber

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.content.asByteStream
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.io.File
import java.nio.file.Paths

fun Application.updateRemoteFiles() {

    val grabber: AirportsGrabber by inject()

    routing {
        get("/api/v1/updatebase") {
            grabber.grabToDatabase(FilesLinks.files, FilesLinks.BASE_URL)
            call.respond(HttpStatusCode.OK, "Downloading and mapping started")
        }

        get("/api/v1/backup") {
            if (File("files/database_v1.mv.db").exists()) {
                putS3Object(
                    bucketName = "our-airports",
                    objectKey = "database_v1.mv.db",
                    objectPath = "files/database_v1.mv.db"
                )
                call.respond(HttpStatusCode.OK, "Database back up was deployed on Amazon S3 bucket")
            } else {
                call.respond(HttpStatusCode.NoContent, "Database file is not exists!")
            }
        }
    }
}

suspend fun putS3Object(bucketName: String, objectKey: String, objectPath: String) {

    val metadataVal = mutableMapOf<String, String>()
    metadataVal["time"] = System.currentTimeMillis().toString()

    val request = PutObjectRequest {
        bucket = bucketName
        key = objectKey
        metadata = metadataVal
        this.body = Paths.get(objectPath).asByteStream()
    }

    S3Client.fromEnvironment() { region = "eu-central-1" }.use { s3 ->
        val response =s3.putObject(request)
        println("Tag information is ${response.eTag}")
    }
}