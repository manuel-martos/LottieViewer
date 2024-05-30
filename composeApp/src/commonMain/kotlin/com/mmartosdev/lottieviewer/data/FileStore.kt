package com.mmartosdev.lottieviewer.data

import kotlinx.coroutines.flow.Flow

interface FileDesc

fun interface FileStore {
    suspend fun readFileContent(fileDesc: FileDesc): Flow<String>
}

expect fun createFileStore(): FileStore
