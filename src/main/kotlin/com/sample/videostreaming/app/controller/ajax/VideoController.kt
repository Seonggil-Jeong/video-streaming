package com.sample.videostreaming.app.controller.ajax

import com.sample.videostreaming.app.controller.support.DefaultControllerSupport
import com.sample.videostreaming.app.service.VideoServiceImpl
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class VideoController(
    private val videoService: VideoServiceImpl
) : DefaultControllerSupport() {

    companion object : KLogging() {

    }

    /**
     * upload from ajax
     */
    @PostMapping("/videos")
    fun uploadVideo(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("description") description: String
    ): String {
        videoService.simpleSaveVideoFile(
            file = file
        )

        return "업로드 성공 : file Name : ${file.originalFilename}, (description : $description)"
    }

    @PostMapping("/videos/chunk")
    fun uploadChunkVideo(
        @RequestParam("chunk") file: MultipartFile,
        @RequestParam("chunkNumber") chunkNumber: Int,
        @RequestParam("totalChunks") totalChunks: Int
    ): ResponseEntity<String> {

        return when (videoService.chunkSaveVideoFile(
            file = file,
            chunkNumber = chunkNumber,
            totalChunks = totalChunks
        )) {
            true -> {
                ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfully")
            }
            else -> {
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build()
            }
        }
    }
}