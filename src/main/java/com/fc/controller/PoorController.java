package com.fc.controller;

import com.fc.entity.Poor;
import com.fc.entity.PoorWithBLOBs;
import com.fc.service.PoorService;
import com.fc.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("poor")
@CrossOrigin
public class PoorController {
    @Autowired
    private PoorService poorService;

    @PostMapping("add")
    public ResultVo add(@RequestBody PoorWithBLOBs poor) {
        return poorService.add(poor);
    }

    @GetMapping("delete")
    public ResultVo delete(@RequestParam Long id) {
        return poorService.delete(id);
    }

    @PostMapping("update")
    public ResultVo update(@RequestBody PoorWithBLOBs poor) {
        return poorService.update(poor);
    }

    @GetMapping("getlist")
    public ResultVo getList(@RequestParam(value = "pageNum")Integer pageNo,
                            @RequestParam(value = "pageSize")Integer pageSize,
                            Long id) {
        return poorService.getList(pageNo, pageSize, id);
    }

    @PostMapping("click")
    public ResultVo click(@RequestBody PoorWithBLOBs poor) {
        return poorService.click(poor.getId(), poor.getLastClickTime());
    }
}
