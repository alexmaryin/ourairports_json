package com.herokuapp.ourairports.data.s3storage

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.smithy.kotlin.runtime.content.asByteStream
import aws.smithy.kotlin.runtime.content.writeToFile
import java.io.File
import java.nio.file.Paths

class S3DataStorageImpl : S3DataStorage {
    override suspend fun putS3Object(objectKey: String, objectPath: String, bucketName: String): Boolean {
        val metadataVal = mutableMapOf<String, String>()
        metadataVal["time"] = System.currentTimeMillis().toString()

        return try {
            S3Client.fromEnvironment() { region = S3DataStorage.REGION }.use { s3 ->
                val response = s3.putObject {
                    bucket = bucketName
                    key = objectKey
                    metadata = metadataVal
                    body = Paths.get(objectPath).asByteStream()
                }
                println("Tag information is ${response.eTag}")
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun downloadS3Object(objectKey: String, path: String, bucketName: String): Boolean {
        return try {
            S3Client.fromEnvironment() { region = S3DataStorage.REGION }.use { s3 ->
                val request = GetObjectRequest {
                    key = objectKey
                    bucket = bucketName
                }
                s3.getObject(request) { response ->
                    val file = File("$path/$objectKey")
                    response.body?.writeToFile(file)
                }
                println("File $objectKey successfully downloaded to $path")
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}