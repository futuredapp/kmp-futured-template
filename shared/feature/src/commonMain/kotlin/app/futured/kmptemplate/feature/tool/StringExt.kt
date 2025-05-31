package app.futured.kmptemplate.feature.tool

fun String.gsToHttpGcloudUrl(): String {
    return replace("gs://", "https://storage.googleapis.com/")
}
