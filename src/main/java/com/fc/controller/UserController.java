package com.fc.controller;

import com.fc.dto.UserResponseDto;
import com.fc.entity.User;
import com.fc.service.UserService;
import com.fc.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("add")
    public ResultVo add(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("del")
    public ResultVo del(@RequestParam Long id) {
        return userService.del(id);
    }

    @PostMapping("update")
    public ResultVo update(@RequestBody User user) {
        return userService.update(user);
    }


    @GetMapping("getlist")
    public ResultVo getList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                Long id) {
        return userService.getList(pageNo, pageSize, id);
    }

}
