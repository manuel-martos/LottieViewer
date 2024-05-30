package com.mmartosdev.lottieviewer.data

import com.mmartosdev.lottieviewer.utils.UriFileDesc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

actual fun createFileStore(): FileStore = FileStore { fileDesc ->
    flow {
        (fileDesc as? UriFileDesc)?.run {
            emit(File(fileDesc.uri).readText())
        } ?: throw IllegalArgumentException("Unsupported FileDesc")
    }.flowOn(Dispatchers.IO)
}