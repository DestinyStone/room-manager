package com.demo.common.api;

import java.io.Serializable;

/**
 * @Author zhou xiao fen
 * @Date 2023/8/31 22:01
 **/
public interface IResultCode extends Serializable {
    String getMessage();

    int getCode();
}
