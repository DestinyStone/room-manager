package com.demo.dto;

import com.demo.entity.User;
import lombok.Data;

@Data
public class LoginDTO extends User {
    private String code;
}
