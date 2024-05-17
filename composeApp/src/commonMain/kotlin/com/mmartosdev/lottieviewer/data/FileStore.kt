package com.mmartosdev.lottieviewer.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.net.URI

interface FileStore {
    suspend fun readFileContent(uri: URI): Flow<String>
}

class FileStoreImpl : FileStore {

    override suspend fun readFileContent(uri: URI): Flow<String> = flow {
        emit(File(uri).readText())
    }.flowOn(Dispatchers.IO)

}