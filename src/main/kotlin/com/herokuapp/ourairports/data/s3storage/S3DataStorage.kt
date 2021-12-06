package com.herokuapp.ourairports.data.s3storage

/**
 * Interface for access to S3 bucket storage of application in Amazon Cloud
 */
interface S3DataStorage {
    /**
     * Upload file to bucket in S3 Cloud
     * @param bucketName [String] - name of the bucket, default is [APP_BUCKET]
     * @param objectKey [String] - name of the file which will be created into bucket
     * @param objectPath [String] - path to file on local
     *
     * @return [Boolean] true if success
     */
    suspend fun putS3Object(objectKey: String, objectPath: String, bucketName: String = APP_BUCKET): Boolean

    /**
     * Download file from bucket in S3 Cloud
     * @param bucketName [String] - name of the bucket, default is [APP_BUCKET]
     * @param objectKey [String] - name of the file into bucket
     * @param path [String] - local path to save the file
     *
     * @return [Boolean] true if success
     */
    suspend fun downloadS3Object(objectKey: String, path: String, bucketName: String = APP_BUCKET): Boolean

    companion object {
        const val REGION = "eu-central-1"
        const val APP_BUCKET = "our-airports"
    }
}