package com.fc.controller;

import com.fc.service.UploadImgService;
import com.fc.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Autowired
    private UploadImgService uploadImgService;

    @RequestMapping("uploadImg")
    public ResultVo uploadImg(MultipartFile file, String type) {
        return uploadImgService.uploadImg(file, type);
    }

}
