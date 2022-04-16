package com.fc.service;

import com.fc.entity.Collection;
import com.fc.vo.ResultVo;

public interface CollectionService {
    ResultVo add(Collection collection);

    ResultVo update(Collection collection);

    ResultVo delete(Long id);

    ResultVo getList(Integer pageNo, Integer pageSize, Long id);

}
