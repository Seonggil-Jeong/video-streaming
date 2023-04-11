package com.sample.videostreaming.app.service

import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*


@Service
class VideoServiceImpl {

    companion object : KLogging() {

    }

    /**
     * upload Video File
     */
    fun simpleSaveVideoFile(file: MultipartFile) {
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

    /**
     * upload Video File with Split
     */
    fun chunkSaveVideoFile(file: MultipartFile, chunkNumber: Int, totalChunks: Int): Boolean {
        val uploadDir = createUploadDir("video")

        val fileName: String = file.originalFilename + ".part" + chunkNumber
        val filePath: Path = Paths.get(uploadDir, fileName)
        Files.write(filePath, file.bytes)

        if (chunkNumber == totalChunks - 1) {
            val split: List<String>? = file.originalFilename?.split("\\.")
            val outputFileName: String = UUID.randomUUID().toString() + "." + split?.get(split.size - 1)
            val outputFile: Path = Paths.get(uploadDir, outputFileName)
            Files.createFile(outputFile)


            for (i in 0 until totalChunks) {
                val chunkFile = Paths.get(uploadDir, file.originalFilename + ".part" + i)
                Files.write(outputFile, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND)

                Files.delete(chunkFile)
            }

            logger.info { "upload file successfully" }

            return true
        } else {
            return false
        }

    }


    private fun createUploadDir(createdDirPath: String): String {
        val dir = File(createdDirPath)
        if (!dir.exists()) {
            logger.info { "dir created : Path : $createdDirPath" }
            dir.mkdirs()
        }
        return createdDirPath
    }
}