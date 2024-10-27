package com.demo.controller;

import com.demo.common.utils.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/blade-bus/file")
public class FileController {
    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        try {
            String upload = FileUtils.upload(file.getInputStream());
            return upload;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping("/download")
    public void upload(@RequestParam("uuid") String uuid, HttpServletResponse response) {
       FileUtils.download(uuid, response);
    }
}
