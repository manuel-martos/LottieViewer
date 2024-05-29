package com.mmartosdev.lottieviewer.data

import UriFileDesc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.net.URI

actual fun createFileStore(): FileStore = object : FileStore {
    override suspend fun readFileContent(fileDesc: FileDesc): Flow<String> = flow {
        (fileDesc as? UriFileDesc)?.run {
            emit(File(URI.create(fileDesc.uri)).readText())
        } ?: throw IllegalArgumentException("Unsupported FileDesc")
    }.flowOn(Dispatchers.IO)
}