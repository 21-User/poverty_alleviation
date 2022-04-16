package com.fc.service.impl;

import com.fc.dao.CarouselMapper;
import com.fc.entity.Carousel;
import com.fc.service.CarouselService;
import com.fc.vo.DataVo;
import com.fc.vo.ResultVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public ResultVo add(Carousel carousel) {
        ResultVo resultVo;

        //如果没有告知轮播图的状态默认是不看可用的(false)
        if (carousel.getAvailable() == null) {
            carousel.setAvailable(false);
        }

        int affectedRows = carouselMapper.insertSelective(carousel);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "图片添加成功", true, carousel);
        } else {
            resultVo = new ResultVo(4000, "图片添加失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo delete(Integer id) {
        ResultVo resultVo;

        int affectedRows = carouselMapper.deleteByPrimaryKey(id);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "删除图片成功", true, null);
        } else {
            resultVo = new ResultVo(4000, "删除图片失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo update(Carousel carousel) {
        ResultVo resultVo;

        int affectedRows = carouselMapper.updateByPrimaryKeySelective(carousel);

        if (affectedRows > 0) {
            //更新完成之后查询最新的结果
            carousel = carouselMapper.selectByPrimaryKey(carousel.getId());

            resultVo = new ResultVo(2000, "更新图片成功", true, carousel);
        } else {
            resultVo = new ResultVo(4000, "更新图片失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo getList(Integer pageNo, Integer pageSize, Integer id) {
        //返回的结果
        ResultVo resultVo;

        //返回结果中的data对象
        DataVo<Carousel> dataVo;

        //轮播图信息的数组
        List<Carousel> carousels;

        //查询单个
        if (id != null) {
            carousels = new ArrayList<>();

            Carousel carousel = carouselMapper.selectByPrimaryKey(id);

            //如果用户中没有该条数据的情况
            if (carousel == null) {
                dataVo = new DataVo<>(0L, carousels, pageNo, pageSize);

                resultVo = new ResultVo(4100, "没有此图", false, dataVo);
            } else {
                //将该条数据放到数组中
                carousels.add(carousel);

                dataVo = new DataVo<>(1L, carousels, pageNo, pageSize);

                resultVo = new ResultVo(2100, "成功查到了该图", true, dataVo);
            }
        } else {
            //开启分页
            PageHelper.startPage(pageNo, pageSize);

            carousels = carouselMapper.selectByExample(null);

            //如果数据库没有数据
            if (carousels.size() == 0) {
                dataVo = new DataVo<>(0L, carousels, pageNo, pageSize);

                resultVo = new ResultVo(4200, "没有查到pictures", false, dataVo);
            } else {
                PageInfo<Carousel> pageInfo = new PageInfo<>(carousels);

                dataVo = new DataVo<>(pageInfo.getTotal(), carousels, pageNo, pageSize);

                resultVo = new ResultVo(2200, "轮播图片查询成功", true, dataVo);
            }
        }
        return resultVo;
    }

    @Override
    public ResultVo changeStatus(Integer id) {
        ResultVo resultVo;

        Integer affectedRows = carouselMapper.changeStatus(id);

        if (affectedRows > 0) {
            //更新完成之后查询最新的结果
            Carousel carousel = carouselMapper.selectByPrimaryKey(id);

            resultVo = new ResultVo(2000, "修改轮播图状态成功", true, carousel);
        } else {
            resultVo = new ResultVo(4000, "修改轮播图状态失败", false, null);
        }

        return resultVo;
    }
}
