package com.fc.service;

import com.fc.entity.User;
import com.fc.vo.ResultVo;

public interface UserService {
    ResultVo add(User user);

    ResultVo getList(Integer pageNo, Integer pageSize, Long id);

    ResultVo update(User user);

    ResultVo del(Long id);

}
