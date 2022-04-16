package com.fc.service;

import com.fc.entity.Poor;
import com.fc.entity.PoorWithBLOBs;
import com.fc.vo.ResultVo;

import java.util.Date;

public interface PoorService {

    ResultVo add(PoorWithBLOBs poor);

    ResultVo delete(Long id);

    ResultVo update(PoorWithBLOBs poor);

    ResultVo getList(Integer pageNo, Integer pageSize, Long id);

    ResultVo click(Long id, Date lastClickTime);

}
