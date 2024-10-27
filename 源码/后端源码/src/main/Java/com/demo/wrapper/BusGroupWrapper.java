package com.demo.wrapper;


import cn.hutool.core.bean.BeanUtil;
import com.demo.common.core.BaseEntityWrapper;
import com.demo.entity.BusGroup;
import com.demo.vo.BusGroupVO;

import java.util.Objects;

public class BusGroupWrapper extends BaseEntityWrapper<BusGroup, BusGroupVO> {

    public static BusGroupWrapper build() {
        return new BusGroupWrapper();
    }

    @Override
    public BusGroupVO entityVO(BusGroup entity) {
        BusGroupVO vo = Objects.requireNonNull(BeanUtil.copyProperties(entity, BusGroupVO.class));
        return vo;
    }
}
