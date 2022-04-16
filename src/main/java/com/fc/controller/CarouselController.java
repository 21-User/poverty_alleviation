package com.fc.controller;

import com.fc.entity.Carousel;
import com.fc.service.CarouselService;
import com.fc.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("carousel")
@CrossOrigin
public class CarouselController {
    @Autowired
    private CarouselService carouselService;

    @PostMapping("add")
    public ResultVo add(@RequestBody Carousel carousel) {
        return carouselService.add(carousel);
    }

    @GetMapping("delete")
    public ResultVo delete(@RequestParam Integer id) {
        return carouselService.delete(id);
    }

    @PostMapping("update")
    public ResultVo update(@RequestBody Carousel carousel) {
        return carouselService.update(carousel);
    }

    @GetMapping("getlist")
    public ResultVo getList(@RequestParam(value = "pageNum")Integer pageNo,
                            @RequestParam(value = "pageSize")Integer pageSize,
                            Integer id) {
        return carouselService.getList(pageNo, pageSize, id);
    }

    @GetMapping("state")
    public ResultVo changeStatus(@RequestParam Integer id) {
        return carouselService.changeStatus(id);
    }

}
