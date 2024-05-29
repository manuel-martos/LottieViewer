package com.mmartosdev.lottieviewer.data

import JsFileDesc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex

external class FileReader : JsAny {
    fun readAsText(file: JsAny)
    var onload: () -> Unit
    val result: String
}

actual fun createFileStore(): FileStore = object : FileStore {
    override suspend fun readFileContent(fileDesc: FileDesc): Flow<String> = flow {
        (fileDesc as? JsFileDesc)?.run {
            val mutex = Mutex(locked = true)
            val reader = FileReader()
            reader.onload = {
                mutex.unlock()
            }
            reader.readAsText(fileDesc.file)
            mutex.lock()
            emit(reader.result)
        } ?: throw IllegalArgumentException("Unsupported FileDesc")
    }
}