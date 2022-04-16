package com.fc.service.impl;

import com.fc.dao.PoorMapper;
import com.fc.entity.Poor;
import com.fc.entity.PoorWithBLOBs;
import com.fc.service.PoorService;
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
public class PoorServiceImpl implements PoorService {
    @Autowired
    private PoorMapper poorMapper;

    @Override
    public ResultVo add(PoorWithBLOBs poor) {
        ResultVo resultVo;

        if (poor.getCreateTime() == null) {
            poor.setCreateTime(new Date());
        }

        poor.setClickNum(0);
        poor.setLastClickTime(null);

        int affectedRows = poorMapper.insert(poor);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "贫困户添加成功", true, poor);
        } else {
            resultVo = new ResultVo(4000, "贫困户添加失败！！", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo delete(Long id) {
        ResultVo resultVo;

        int affectedRows = poorMapper.deleteByPrimaryKey(id);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "贫困户删除成功", true, null);
        } else {
            resultVo = new ResultVo(4000, "贫困户删除失败！！", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo update(PoorWithBLOBs poor) {
        ResultVo resultVo;

        int affectedRows = poorMapper.updateByPrimaryKeyWithBLOBs(poor);

        if (affectedRows > 0) {
            poor  = poorMapper.selectByPrimaryKey(poor.getId());

            resultVo = new ResultVo(2000, "贫困户修改成功", true, poor);
        } else {
            resultVo = new ResultVo(4000, "贫困户修改失败", false, null);
        }

        return resultVo;
    }

    @Override
    public ResultVo getList(Integer pageNo, Integer pageSize, Long id) {
        ResultVo resultVo;

        DataVo<PoorWithBLOBs> dataVo;

        List<PoorWithBLOBs> poors;

        if (id != null){
            poors = new ArrayList<>();

            PoorWithBLOBs poor = poorMapper.selectByPrimaryKey(id);

            if (poor == null){
                dataVo = new DataVo<>(0L, poors, pageNo, pageSize);

                resultVo = new ResultVo(4100, "没有该贫困户", false, dataVo);
            } else {
                click(poor.getId(), null);

                poor.setClickNum(poor.getClickNum() + 1);

                poors.add(poor);

                dataVo = new DataVo<>(1L, poors, pageNo, pageSize);

                resultVo = new ResultVo(2100, "查询该贫困户成功", true, dataVo);
            }
        } else {
            PageHelper.startPage(pageNo, pageSize);

            poors = poorMapper.selectByExampleWithBLOBs(null);

            if (poors.size() == 0) {
                dataVo = new DataVo<>(0L, poors, pageNo, pageSize);

                resultVo = new ResultVo(4200, "没有贫困户信息", false, dataVo);
            } else {
                PageInfo<PoorWithBLOBs> pageInfo = new PageInfo<>(poors);

                dataVo = new DataVo<>(pageInfo.getTotal(), poors, pageNo, pageSize);

                resultVo = new ResultVo(2200, "贫困户信息查询成功", true, dataVo);
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

        Integer affectedRows = poorMapper.click(id, lastClickTime);

        if (affectedRows > 0) {
            resultVo = new ResultVo(2000, "贫困户成功加1点击量", true, null);
        } else {
            resultVo = new ResultVo(4000, "贫困户加1点击量失败", false, null);
        }

        return resultVo;
    }


}
