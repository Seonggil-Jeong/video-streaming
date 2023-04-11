package com.sample.videostreaming.app.controller

import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class DefaultPageController {
    companion object : KLogging() {

    }

    @RequestMapping(value = ["/index", "/"])
    fun moveToIndexPage(): String {
        return "/index"
    }

    @RequestMapping(value = ["/chunk"])
    fun moveToChunkUploadPage(): String {
        return "/chunk-upload"
    }
}