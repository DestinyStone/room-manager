package com.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.demo.common.core.Query;
import com.demo.common.core.R;
import com.demo.common.utils.AuthUtil;
import com.demo.common.utils.Func;
import com.demo.dto.BusRoomDTO;
import com.demo.entity.BusRoom;
import com.demo.service.BusRoomService;
import com.demo.vo.BusRoomVO;
import com.demo.wrapper.BusRoomWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/blade-bus/room")
public class BusRoomController {

    @Autowired
    private BusRoomService service;

    @PostMapping("/save")
    public R save(@RequestBody BusRoomDTO dto) {
		BusRoom convert = dto.convert();
		convert.setCreateTime(new Date());
		convert.setStatus(0);
		convert.setRoomStatus(0);
		convert.setCreateUser(AuthUtil.getUserId());
        return R.status(service.save(convert));
    }

	@GetMapping("/list")
	public R<List<BusRoomVO>> groupList() {
		List<BusRoom> list = service.list();
		return R.data(BusRoomWrapper.build().listVO(list));
	}

    @GetMapping("/delete")
    public R delete(@RequestParam("ids") String ids) {
        return R.status(service.removeByIds(Func.toLongList(ids)));
    }

    @PostMapping("/update/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody BusRoomDTO dto) {
		BusRoom entity = dto.convert();
        entity.setId(id);
        return R.status(service.updateById(entity));
    }

    @GetMapping("/detail")
    public R<BusRoomVO> detail(@RequestParam("id") Long id) {
        BusRoom entity = service.getById(id);
        return R.data(BusRoomWrapper.build().entityVO(entity));
    }
}
