package com.fc.controller;

import com.fc.entity.Alleviation;
import com.fc.entity.Collection;
import com.fc.service.AlleviationService;
import com.fc.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("alleviation")
@CrossOrigin
public class AlleviationController {
    @Autowired
    private AlleviationService alleviationService;

    @PostMapping("add")
    public ResultVo add(@RequestBody Alleviation alleviation) {
        return alleviationService.add(alleviation);
    }

    @GetMapping("delete")
    public ResultVo delete(@RequestParam Long id) {
        return alleviationService.delete(id);
    }

    @PostMapping("update")
    public ResultVo update(@RequestBody Alleviation alleviation) {
        return alleviationService.update(alleviation);
    }

    @GetMapping("getlist")
    public ResultVo getList(@RequestParam(value = "pageNum")Integer pageNo,
                            @RequestParam(value = "pageSize")Integer pageSize,
                            Long id) {
        return alleviationService.getList(pageNo, pageSize, id);
    }

    @PostMapping("click")
    public ResultVo click(@RequestBody Alleviation alleviation) {
        return alleviationService.click(alleviation.getId(), alleviation.getLastClickTime());
    }
}
