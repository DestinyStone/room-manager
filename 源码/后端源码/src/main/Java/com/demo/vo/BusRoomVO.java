package com.demo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class BusRoomVO {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	//
	private String name;

	//
	private String remark;

	//
	private String code;

	//
	private List<String> convers;

	//
	private BigDecimal startMoney;

	//
	private BigDecimal moneyDay;

	// 状态 0 空闲 1入住中
	private Integer status;

	// 房间状态 0 正常 1 清洁中 2 维修中
	private Integer roomStatus;


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long groupId;

	private String groupName;

	private String tags;

	private String address;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long createUser;

	private Integer number;
}
