package com.fc.service;

import com.fc.entity.MessageBoardWithBLOBs;
import com.fc.vo.ResultVo;

public interface MsgBoardService {

    ResultVo add(MessageBoardWithBLOBs msgBoard);

    ResultVo delete(Long id);

    ResultVo update(MessageBoardWithBLOBs msgBoard);

    ResultVo getList(Integer pageNo, Integer pageSize, Long id);
}
