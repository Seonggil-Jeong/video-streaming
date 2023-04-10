package com.sample.videostreaming.app.service

import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


@Service
class VideoServiceImpl {

    companion object : KLogging() {

    }

    fun saveVideoFile(file: MultipartFile) {
        when {
            !file.isEmpty -> try {
                val filepath: Path = Paths.get("video", file.originalFilename)
                Files.newOutputStream(filepath).use { os -> os.write(file.bytes) }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            else -> {
                throw IOException("file is Empty!")

            }
        }

    }
}