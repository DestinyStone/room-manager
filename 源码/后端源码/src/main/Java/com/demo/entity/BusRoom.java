package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "bus_room")
public class BusRoom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //
    private String name;

    //
    private String remark;

    //
    private String code;

    //
    private String convers;

    //
    private BigDecimal startMoney;

    //
    private BigDecimal moneyDay;

    // 状态 0 空闲 1入住中
    private Integer status;

    // 房间状态 0 正常
    private Integer roomStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

    private String tags;

    private String address;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;

    private Integer number;

}
