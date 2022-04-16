package com.fc.service;

import com.fc.entity.Carousel;
import com.fc.vo.ResultVo;

public interface CarouselService {
    ResultVo add(Carousel carousel);

    ResultVo delete(Integer id);

    ResultVo update(Carousel carousel);

    ResultVo getList(Integer pageNo, Integer pageSize, Integer id);

    ResultVo changeStatus(Integer id);
}
