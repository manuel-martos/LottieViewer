package com.mmartosdev.lottieviewer.data

import com.mmartosdev.lottieviewer.utils.JsFileDesc
import kotlinx.coroutines.flow.flow
import org.w3c.files.FileReader
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual fun createFileStore(): FileStore = FileStore { fileDesc ->
    flow {
        (fileDesc as? JsFileDesc)?.run {
            suspendCoroutine { continuation ->
                val reader = FileReader()
                reader.onload = {
                    continuation.resume(reader.result.toString())
                }
                reader.readAsText(fileDesc.file)
            }.run { emit(this) }
        } ?: throw IllegalArgumentException("Unsupported FileDesc")
    }
}