package com.demo.vo;

import com.demo.entity.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserVO extends User {
    private Long commentCount;

    private BigDecimal money;
}
