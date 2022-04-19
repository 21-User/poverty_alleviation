package com.fc.service.impl;

import com.fc.service.UploadImgService;
import com.fc.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadImgServiceImpl implements UploadImgService {

    @Override
    public ResultVo uploadImg(MultipartFile file, String type) {
        //文件存放的路径
        String path = "D:\\server\\apache-tomcat-8.5.37\\webapps\\upload\\Poverty-Alleviation\\" + type;

        File pathFile = new File(path);

        //如果文件不存在就创建多级路径
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        //获取原始文件名
        String filename = file.getOriginalFilename();

        //获取日期时间的格式化器
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        //格式化之后的时间
        String formatDate = formatter.format(new Date());

        //获取文件的后缀名
        String suffix = filename.substring(filename.lastIndexOf('.'));

        filename = formatDate + suffix;

        ResultVo resultVo = new ResultVo();

        try {
            //文件上传  路径+文件名
            file.transferTo(new File(pathFile, filename));

            Map<String, Object> map = new HashMap<>();

            //浏览器访问的路径
            map.put("imgurl", "http://localhost:8081/upload/Poverty-Alleviation/" + type + "/" + filename);

            resultVo.setCode(200);
            resultVo.setMessage("图片上传成功");
            resultVo.setSuccess(true);
            resultVo.setData(map);

        } catch (IOException e) {
            e.printStackTrace();

            resultVo.setCode(400);
            resultVo.setMessage("图片上传失败");
            resultVo.setSuccess(false);
            resultVo.setData(null);
        }

        return resultVo;
    }
}
