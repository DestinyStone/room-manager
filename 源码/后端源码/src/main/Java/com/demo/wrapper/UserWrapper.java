package com.demo.wrapper;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.demo.common.core.BaseEntityWrapper;
import com.demo.entity.BusRoom;
import com.demo.entity.User;
import com.demo.service.BusRoomService;
import com.demo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

    private static BusRoomService roomService;


    @Autowired
    public void setRoomService(BusRoomService roomService) {
        UserWrapper.roomService = roomService;
    }

    public static UserWrapper build() {
        return new UserWrapper();
    }

    @Override
    public UserVO entityVO(User entity) {
        UserVO vo = Objects.requireNonNull(BeanUtil.copyProperties(entity, UserVO.class));


        vo.setMoney(new BigDecimal(0));
        vo.setCommentCount(0L);

        return vo;
    }
}
