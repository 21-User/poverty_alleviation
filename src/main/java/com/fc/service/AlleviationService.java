package com.fc.service;

import com.fc.entity.Alleviation;
import com.fc.vo.ResultVo;

import java.util.Date;

public interface AlleviationService {
    ResultVo add(Alleviation alleviation);

    ResultVo delete(Long id);

    ResultVo update(Alleviation alleviation);

    ResultVo getList(Integer pageNo, Integer pageSize, Long id);

    ResultVo click(Long id, Date lastClickTime);

}
