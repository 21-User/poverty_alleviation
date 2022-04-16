package com.fc.service.impl;

import com.fc.service.UploadImgService;
import com.fc.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadImgServiceImpl implements UploadImgService {

    @Override
    public ResultVo uploadImg(MultipartFile file, String type) {
        return null;
    }
}
