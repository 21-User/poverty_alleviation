package com.fc.controller;

import com.fc.entity.VolunteerRecruitment;
import com.fc.service.VolunteerService;
import com.fc.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("volunteer")
@CrossOrigin
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;

    @PostMapping("add")
    public ResultVo add(@RequestBody VolunteerRecruitment volunteer) {
        return volunteerService.add(volunteer);
    }

    @GetMapping("delete")
    public ResultVo delete(@RequestParam Long id) {
        return volunteerService.delete(id);
    }

    @PostMapping("update")
    public ResultVo update(@RequestBody VolunteerRecruitment volunteer) {
        return volunteerService.update(volunteer);
    }

    @GetMapping("getlist")
    public ResultVo getList(@RequestParam(value = "pageNum")Integer pageNo,
                            @RequestParam(value = "pageSize")Integer pageSize,
                            Long id) {
        return volunteerService.getList(pageNo, pageSize, id);
    }

    @PostMapping("click")
    public ResultVo click(@RequestBody VolunteerRecruitment volunteer) {
        return volunteerService.click(volunteer.getId(), volunteer.getLastClickTime());
    }
}
