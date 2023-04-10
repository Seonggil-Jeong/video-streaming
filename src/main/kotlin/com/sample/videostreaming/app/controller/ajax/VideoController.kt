package com.sample.videostreaming.app.controller.ajax

import com.sample.videostreaming.app.controller.support.DefaultControllerSupport
import com.sample.videostreaming.app.service.VideoServiceImpl
import mu.KLogging
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

    @PostMapping("/videos")
    fun uploadVideo(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("description") description: String
    ): String {
        videoService.saveVideoFile(
            file = file
        )

        return "업로드 성공 : file Name : ${file.originalFilename}, (description : $description)"
    }
}