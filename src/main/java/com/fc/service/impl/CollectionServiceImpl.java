package com.fc.service.impl;

import com.fc.dao.CollectionMapper;
import com.fc.entity.Collection;
import com.fc.entity.User;
import com.fc.service.CollectionService;
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
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public ResultVo add(Collection collection) {
        ResultVo resultVo;

        //如果没有创建时间就给他一个当前时间
        if (collection.getCreateTime() == null) {
            collection.setCreateTime(new Date());
        }

        int affectedRows = collectionMapper.insertSelective(collection);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "添加收藏成功", true, collection);
        } else {
            resultVo = new ResultVo(4000, "添加收藏失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo delete(Long id) {
        ResultVo resultVo;

        int affectedRows = collectionMapper.deleteByPrimaryKey(id);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "收藏删除成功", true, null);
        } else {
            resultVo = new ResultVo(4000, "收藏删除失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo update(Collection collection) {
        ResultVo resultVo;

        int affectedRows = collectionMapper.updateByPrimaryKey(collection);

        if (affectedRows > 0) {
            collection = collectionMapper.selectByPrimaryKey(collection.getId());

            resultVo = new ResultVo(2000, "修改收藏成功", true, collection);
        } else {
            resultVo = new ResultVo(4000, "修改收藏失败", false, null);
        }

        return resultVo;
    }


    @Override
    public ResultVo getList(Integer pageNo, Integer pageSize, Long id) {
        //返回给前端的信息
        ResultVo resultVo;

        //data对象
        DataVo<Collection> dataVo;

        //查到的用户信息数组
        List<Collection> collections;

        //不传id的情况
        if (id != null) {
            collections = new ArrayList<>();

            Collection collection = collectionMapper.selectByPrimaryKey(id);

            //如果数据库中没有该用户的情况
            if (collection == null) {
                dataVo = new DataVo<>(0L, collections, pageNo, pageSize);

                resultVo = new ResultVo(4000, "没有查到该条收藏", false, dataVo);
            } else {
                //查到了就给他放到list中
                collections.add(collection);

                dataVo = new DataVo<>(1L, collections, pageNo, pageSize);

                resultVo = new ResultVo(2000, "查到该条收藏!!", true, dataVo);
            }
        } else {
            //没传id就查询全部然后开始分页
            PageHelper.startPage(pageNo, pageSize);

            collections = collectionMapper.selectByExample(null);

            //如果数据库中一条数据都没有的情况
            if (collections.size() == 0) {
                dataVo = new DataVo<>(0L, collections, pageNo, pageSize);

                resultVo = new ResultVo(4000, "没有收藏信息!!", false, dataVo);
            } else {
                //获取分页信息
                PageInfo<Collection> pageInfo = new PageInfo<>(collections);

                dataVo = new DataVo<>(pageInfo.getTotal(), collections, pageNo, pageSize);

                resultVo = new ResultVo(2000, "收藏信息查询成功", true, dataVo);
            }
        }

        return resultVo;
    }


}
