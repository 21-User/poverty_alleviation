package com.fc.controller;

import com.fc.entity.MessageBoard;
import com.fc.entity.MessageBoardWithBLOBs;
import com.fc.service.MsgBoardService;
import com.fc.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("msgboard")
@CrossOrigin
public class MsgBoardController {
    @Autowired
    private MsgBoardService msgBoardService;

    @RequestMapping("get")
    public Map<String, Object> get() {
        Map<String, Object> map = new HashMap<>();

        map.put("code",200);
        map.put("message", "跨越请求成功");

        return map;
    }

    @PostMapping("add")
    public ResultVo add(@RequestBody MessageBoardWithBLOBs msgBoard) {
        return msgBoardService.add(msgBoard);
    }

    @GetMapping("delete")
    public ResultVo delete(@RequestParam Long id) {
        return msgBoardService.delete(id);
    }

    @PostMapping("update")
    public ResultVo update(@RequestBody MessageBoardWithBLOBs msgBoard) {
        return msgBoardService.update(msgBoard);
    }

    @GetMapping("getlist")
    public ResultVo getList(@RequestParam(value = "pageNum")Integer pageNo,
                            @RequestParam(value = "pageSize")Integer pageSize,
                            Long id) {
        return msgBoardService.getList(pageNo, pageSize, id);
    }

}
