package com.demo.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.demo.common.core.BaseEntityWrapper;
import com.demo.common.utils.Func;
import com.demo.entity.BusGroup;
import com.demo.entity.BusRoom;
import com.demo.service.BusGroupService;
import com.demo.vo.BusRoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class BusRoomWrapper extends BaseEntityWrapper<BusRoom, BusRoomVO> {

	public static BusGroupService groupService;

	@Autowired
	public void setGroupService(BusGroupService groupService) {
		BusRoomWrapper.groupService = groupService;
	}

	public static BusRoomWrapper build() {
        return new BusRoomWrapper();
    }

    @Override
    public BusRoomVO entityVO(BusRoom entity) {
        BusRoomVO vo = Objects.requireNonNull(BeanUtil.copyProperties(entity, BusRoomVO.class));

        if (StrUtil.isNotBlank(entity.getConvers())) {
        	vo.setConvers(Func.toStrList(entity.getConvers()));
		}

		if (entity.getGroupId() != null) {
			BusGroup group = groupService.getById(entity.getGroupId());
			vo.setGroupName(Optional.of(group).map(BusGroup::getName).orElse(""));
		}

        return vo;
    }
}
