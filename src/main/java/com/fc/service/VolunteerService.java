package com.fc.service;

import com.fc.entity.VolunteerRecruitment;
import com.fc.vo.ResultVo;

import java.util.Date;

public interface VolunteerService {
    ResultVo add(VolunteerRecruitment volunteer);

    ResultVo delete(Long id);

    ResultVo update(VolunteerRecruitment volunteer);

    ResultVo getList(Integer pageNo, Integer pageSize, Long id);

    ResultVo click(Long id, Date lastClickTime);

}
