package com.demo.dto;

import cn.hutool.core.bean.BeanUtil;
import com.demo.common.utils.Func;
import com.demo.entity.BusRoom;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BusRoomDTO {

	private Long groupId;

	//
	private String name;

	//
	private String code;

	//
	private List<String> convers;

	//
	private BigDecimal startMoney;

	//
	private BigDecimal moneyDay;

	private String tags;

	private String address;

	private Integer number;

	public BusRoom convert() {
		BusRoom copy = BeanUtil.copyProperties(this, BusRoom.class);
		copy.setConvers(Func.join(convers));
		return copy;
	}
}
