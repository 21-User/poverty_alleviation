package com.fc.controller;

import com.fc.entity.Collection;
import com.fc.service.CollectionService;
import com.fc.vo.ResultVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("collection")
@CrossOrigin
public class CollectionController {
    @Autowired
    private CollectionService collectionService;
    
    @PostMapping("add")
    public ResultVo add(@RequestBody Collection collection) {
        return collectionService.add(collection);
    }

    @PostMapping("update")
    public ResultVo update(@RequestBody Collection collection) {
        return collectionService.update(collection);
    }

    @GetMapping("delete")
    public ResultVo delete(@RequestParam Long id) {
        return collectionService.delete(id);
    }

    @GetMapping("getlist")
    public ResultVo getList(@RequestParam(value = "pageNum") Integer pageNo,
                            @RequestParam(value = "pageSize") Integer pageSize,
                            Long id) {
        return collectionService.getList(pageNo,pageSize,id);
    }
}
