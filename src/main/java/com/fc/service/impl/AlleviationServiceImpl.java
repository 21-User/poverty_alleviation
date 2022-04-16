package com.fc.service.impl;

import com.fc.dao.AlleviationMapper;
import com.fc.entity.Alleviation;
import com.fc.service.AlleviationService;
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
public class AlleviationServiceImpl implements AlleviationService {
    @Autowired
    private AlleviationMapper alleviationMapper;

    @Override
    public ResultVo add(Alleviation alleviation) {
        ResultVo resultVo;

        int affectedRows = alleviationMapper.insertSelective(alleviation);

        if (alleviation.getCreateTime() == null) {
            alleviation.setCreateTime(new Date());
        }
        //新建的扶贫政策单击次数肯定为0,所有没有最后一次单击的时间
        alleviation.setClickNum(0);
        alleviation.setLastClickTime(null);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "扶贫政策添加成功", true, alleviation);
        } else {
            resultVo = new ResultVo(4000, "扶贫政策添加失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo delete(Long id) {
        ResultVo resultVo;

        int affectedRows = alleviationMapper.deleteByPrimaryKey(id);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "扶贫政策删除成功", true, null);
        } else {
            resultVo = new ResultVo(4000, "扶贫政策删除失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo update(Alleviation alleviation) {
        ResultVo resultVo;

        int affectedRows = alleviationMapper.updateByPrimaryKeySelective(alleviation);

        if (affectedRows > 0) {
            //修改成功之后查询修改后的数据,保证数据的最新性
            alleviation = alleviationMapper.selectByPrimaryKey(alleviation.getId());

            resultVo = new ResultVo(2000, "扶贫政策修改成功", true, alleviation);
        } else {
            resultVo = new ResultVo(4000, "扶贫政策修改失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo getList(Integer pageNo, Integer pageSize, Long id) {
        //返回的结果
        ResultVo resultVo;

        //结果中的data对象
        DataVo dataVo;

        //data对象中分数组信息
        List<Alleviation> alleviations;

        //如果id存在
        if (id != null) {
            alleviations = new ArrayList<>();

            Alleviation alleviation = alleviationMapper.selectByPrimaryKey(id);

            //数据库没有该条政策的情况
            if (alleviation == null) {
                dataVo = new DataVo(0L, alleviations, pageNo, pageSize);

                resultVo = new ResultVo(4100, "没有这条扶贫政策", false, dataVo);
            } else{
                //如果查到单个的话点击量+1
                click(alleviation.getId(), new Date());

                //将查到的信息放到数组中
                alleviations.add(alleviation);

                dataVo = new DataVo(1L, alleviations, pageNo, pageSize);

                resultVo = new ResultVo(2100, "查询此条政策成功", true, dataVo);
            }
        } else {
            //查询全部的情况
            //开启分页信息
            PageHelper.startPage(pageNo, pageSize);

            //查全部的数据信息将他放到数组中
            alleviations = alleviationMapper.selectByExampleWithBLOBs(null);

            //数据库为空的情况
            if (alleviations.size() == 0) {
                dataVo = new DataVo(0L, alleviations, pageNo, pageSize);

                resultVo = new ResultVo(4200, "没有扶贫政策可查询", false, dataVo);
            } else {
                PageInfo<Alleviation> pageInfo = new PageInfo<>(alleviations);

                dataVo = new DataVo(pageInfo.getTotal(), alleviations, pageNo, pageSize);

                resultVo = new ResultVo(2200, "扶贫政策查询成功", true, dataVo);
            }
        }

        return resultVo;
    }

    @Override
    public ResultVo click(Long id, Date lastClickTime) {
        ResultVo resultVo;

        Integer affectedRows = alleviationMapper.click(id, lastClickTime);

        if (lastClickTime == null) {
            lastClickTime = new Date();
        }

        if (affectedRows > 0) {
            //点击成功之后查询一下
            Alleviation alleviation = alleviationMapper.selectByPrimaryKey(id);

            resultVo = new ResultVo(2000, "点击量成功加1", true, alleviation);
        } else {
            resultVo = new ResultVo(4000, "点击量加1失败", false, null);
        }

        return resultVo;
    }
}
