package com.example.jetpackcomposesetup.common

import android.content.Context
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File

fun File.fileToBase64(): String {
    return ByteArrayOutputStream().use { outputStream ->
        this.inputStream().use { inputStream ->
            outputStream.write(Base64.encode(inputStream.readBytes(), Base64.DEFAULT))
        }
        return@use outputStream.toString("UTF-8")
    }
}

fun String.base64ToTmpFile(ext: String): File {
    return File.createTempFile("attachment_", ext).apply {
        outputStream().use { out ->
            out.write(Base64.decode(this@base64ToTmpFile.toByteArray(), Base64.DEFAULT))
        }
    }
}

fun Uri.contentUriToTmpFile(
    context: Context, prefix: String, suffix: String
): File? {
    try {
        context.contentResolver.openInputStream(this).use { input ->
            if (input != null) {
                val file = File.createTempFile(prefix, suffix)
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
                return file
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}
