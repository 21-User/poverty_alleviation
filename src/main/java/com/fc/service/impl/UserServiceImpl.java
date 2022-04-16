package com.fc.service.impl;

import com.fc.dto.UserResponseDto;
import com.fc.dao.UserMapper;
import com.fc.entity.User;
import com.fc.service.UserService;
import com.fc.vo.DataVo;
import com.fc.vo.ResultVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVo add(User user) {
        ResultVo resultVo ;

        //如果用户没有创建时间就给他一个当前时间
        if (user.getCreateTime() == null) {
            user.setCreateTime(new Date());
        }

        int affectedRows = userMapper.insertSelective(user);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "用户添加成功", true, user);
        } else {
            resultVo = new ResultVo(4000, "用户添加失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo del(Long id) {
        ResultVo resultVo ;

        int affectedRows = userMapper.deleteByPrimaryKey(id);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "用户删除成功", true, null);
        } else {
            resultVo = new ResultVo(4000, "用户删除失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo update(User user) {
        ResultVo resultVo ;

        int affectedRows = userMapper.updateByPrimaryKey(user);

        if (affectedRows > 0) {
            // 修改完成之后，再重新查询一次，保证返回给前端的是最新最全的数据
            user = userMapper.selectByPrimaryKey(user.getId());

            resultVo = new ResultVo(2000, "用户更改成功", true, user);
        } else {
            resultVo = new ResultVo(4000, "用户更改失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo getList(Integer pageNo, Integer  pageSize, Long id) {
        //返回的结果
        ResultVo resultVo;

        //data中分页的数据
        DataVo<User> dataVo;

        //data中用户信息的数组
        List<User> users;

        //两种情况,id!null || id 为null
        if (id != null) {
            //新建一个数组用于放用户信息
            users = new ArrayList<>();

            //根据主键查询单个
            User user = userMapper.selectByPrimaryKey(id);

            //没有查到用户信息的情况(id不存在)
            if (user == null) {
                dataVo = new DataVo<>(0L, users, pageNo, pageSize);

                resultVo = new ResultVo(4000, "查无此人!", false, dataVo);

            } else {
                //将查到的用户放到集合中
                users.add(user);

                dataVo = new DataVo<>(1L, users, pageNo, pageSize);

                resultVo = new ResultVo(2000, "该用户查询成功", true, dataVo);
            }
        } else {
            //开始分页
            PageHelper.startPage(pageNo, pageSize);

            //查询全部
            users = userMapper.selectByExample(null);

            //如何数据库没有数据的情况
            if (users.size() == 0) {
                dataVo = new DataVo<>(0L, users, pageNo, pageSize);

                resultVo = new ResultVo(4100, "数据库中没有用户!", false, dataVo);
                //有数据查到的情况
            } else {
                //获取用户的分页信息
                PageInfo<User> pageInfo = new PageInfo<>(users);

                dataVo = new DataVo<>(pageInfo.getTotal(), users, pageNo, pageSize);

                resultVo = new ResultVo(2100, "用户查询成功", true, dataVo);
            }
        }
        return resultVo;
    }



}
