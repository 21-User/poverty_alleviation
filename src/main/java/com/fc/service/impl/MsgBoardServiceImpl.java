package com.fc.service.impl;

import com.fc.dao.MessageBoardMapper;
import com.fc.entity.MessageBoard;
import com.fc.entity.MessageBoardWithBLOBs;
import com.fc.service.MsgBoardService;
import com.fc.vo.DataVo;
import com.fc.vo.ResultVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MsgBoardServiceImpl implements MsgBoardService {
    @Autowired
    private MessageBoardMapper msgBoardMapper;


    @Override
    public ResultVo add(MessageBoardWithBLOBs msgBoard) {
        ResultVo resultVo;

        if (msgBoard.getCreateTime() == null) {
            msgBoard.setCreateTime(new Date());
        }

        int affectedRows = msgBoardMapper.insertSelective(msgBoard);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "留言成功", true, msgBoard);
        } else {
            resultVo = new ResultVo(4000, "留言错误", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo delete(Long id) {
        ResultVo resultVo;

        int affectedRows = msgBoardMapper.deleteByPrimaryKey(id);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "删除留言成功", true, null);
        } else {
            resultVo = new ResultVo(4000, "删除留言失败", false, null);

        }
        return resultVo;
    }

    @Override
    public ResultVo update(MessageBoardWithBLOBs msgBoard) {
        ResultVo resultVo;

        int affectedRows = msgBoardMapper.updateByPrimaryKeyWithBLOBs(msgBoard);

        if (affectedRows > 0) {
            msgBoard = msgBoardMapper.selectByPrimaryKey(msgBoard.getId());

            resultVo = new ResultVo(2000, "更新留言成功", true, msgBoard);
        } else {
            resultVo = new ResultVo(4000, "更新留言失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo getList(Integer pageNo, Integer pageSize, Long id) {
        ResultVo resultVo;

        DataVo<MessageBoardWithBLOBs> dataVo;

        List<MessageBoardWithBLOBs> msgBoards;

        if (id != null) {
            msgBoards = new ArrayList<>();

            MessageBoardWithBLOBs msgBoard = msgBoardMapper.selectByPrimaryKey(id);

            if (msgBoard == null) {
                dataVo = new DataVo<>(0L, msgBoards, pageNo, pageSize);

                resultVo = new ResultVo(4100, "没有查到此留言", false, dataVo);
            } else {
                msgBoards.add(msgBoard);

                dataVo = new DataVo<>(1L, msgBoards, pageNo, pageSize);

                resultVo = new ResultVo(2100, "查到了此留言", true, dataVo);
            }

        } else {
            PageHelper.startPage(pageNo, pageSize);

            msgBoards = msgBoardMapper.selectByExampleWithBLOBs(null);

            //如果数据库为空
            if (msgBoards.size() == 0) {
                dataVo = new DataVo<>(0L, msgBoards, pageNo, pageSize);

                resultVo = new ResultVo(4200, "没有留言信息", false, dataVo);
            } else {
                PageInfo<MessageBoardWithBLOBs> pageInfo = new PageInfo<>(msgBoards);

                dataVo = new DataVo<>(pageInfo.getTotal(), msgBoards, pageNo, pageSize);

                resultVo = new ResultVo(2200, "留言信息查询成功", true, dataVo);
            }

        }

        return resultVo;
    }
}
