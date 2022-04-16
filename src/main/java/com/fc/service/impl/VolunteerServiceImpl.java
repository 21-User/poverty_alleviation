package com.fc.service.impl;

import com.fc.dao.VolunteerRecruitmentMapper;
import com.fc.entity.VolunteerRecruitment;
import com.fc.service.VolunteerService;
import com.fc.vo.DataVo;
import com.fc.vo.ResultVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VolunteerServiceImpl implements VolunteerService {
    @Autowired
    private VolunteerRecruitmentMapper volunteerMapper;

    @Override
    public ResultVo add(VolunteerRecruitment volunteer) {
        ResultVo resultVo;

        int affectedRows = volunteerMapper.insertSelective(volunteer);

        if (volunteer.getCreateTime() == null) {
            volunteer.setCreateTime(new Date());
        }

        // 新创建的扶贫政策，点击量应该是0
        volunteer.setClickNum(0);
        volunteer.setLastClickTime(null);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "志愿者添加成功", true, volunteer);
        } else {
            resultVo = new ResultVo(4000, "添加支援者失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo delete(Long id) {
        ResultVo resultVo;

        int affectedRows = volunteerMapper.deleteByPrimaryKey(id);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "志愿者信息删除成功", true, null);
        } else {
            resultVo = new ResultVo(4000, "志愿者信息删除失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo update(VolunteerRecruitment volunteer) {
        ResultVo resultVo;

        int affectedRows = volunteerMapper.updateByPrimaryKeySelective(volunteer);

        if (affectedRows > 0) {
            //修改成功好查询最新的数据
            volunteer = volunteerMapper.selectByPrimaryKey(volunteer.getId());

            resultVo = new ResultVo(2000, "志愿者信息修改成功", true, volunteer);
        } else {
            resultVo = new ResultVo(4000, "志愿者信息修改失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo getList(Integer pageNo, Integer pageSize, Long id) {
        ResultVo resultVo;

        DataVo<VolunteerRecruitment> dataVo;

        List<VolunteerRecruitment> volunteers;

        if (id != null) {
            volunteers = new ArrayList<>();

            VolunteerRecruitment volunteer = volunteerMapper.selectByPrimaryKey(id);

            //用户不存在情况
            if (volunteer == null) {
                dataVo = new DataVo<>(0L, volunteers, pageNo, pageSize);

                resultVo = new ResultVo(4100, "此志愿者不存在", false, dataVo);
            } else {
                //查询单个点击量加1
                click(volunteer.getId(), null);

                volunteer.setClickNum(volunteer.getClickNum() + 1);

                volunteers.add(volunteer);

                dataVo = new DataVo<>(1L, volunteers, pageNo, pageSize);

                resultVo = new ResultVo(2100, "成功查到此志愿者", true, dataVo);
            }
        } else {
            //分页
            PageHelper.startPage(pageNo, pageSize);

            volunteers = volunteerMapper.selectByExampleWithBLOBs(null);

            //数据库为空情况
            if (volunteers.size() == 0) {
                dataVo = new DataVo<>(0L, volunteers, pageNo, pageSize);

                resultVo = new ResultVo(4200, "没有志愿者招聘信息", false, dataVo);
            } else {
                PageInfo<VolunteerRecruitment> pageInfo = new PageInfo<>(volunteers);

                dataVo = new DataVo<>(pageInfo.getTotal(),volunteers, pageNo, pageSize);

                resultVo = new ResultVo(2200, "志愿者招聘信息查询成功", true, dataVo);
            }
        }

        return resultVo;
    }

    @Override
    public ResultVo click(Long id, Date lastClickTime) {
        ResultVo resultVo;

        if (lastClickTime == null) {
            lastClickTime = new Date();
        }

        Integer affectedRows = volunteerMapper.click(id, lastClickTime);

        if (affectedRows > 0) {
            //查询一下
            VolunteerRecruitment volunteer = volunteerMapper.selectByPrimaryKey(id);

            resultVo = new ResultVo(2000, "志愿者招聘单击次数成功加1", true, volunteer);
        } else {
            resultVo = new ResultVo(4000, "志愿者招聘点击失败", false, null);
        }

        return resultVo;
    }
}
