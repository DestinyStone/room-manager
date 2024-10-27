package com.demo.controller;

import cn.hutool.core.bean.BeanUtil;
import com.demo.common.core.R;
import com.demo.common.utils.AuthUtil;
import com.demo.common.utils.Func;
import com.demo.dto.BusGroupDTO;
import com.demo.entity.BusGroup;
import com.demo.service.BusGroupService;
import com.demo.vo.BusGroupVO;
import com.demo.wrapper.BusGroupWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/blade-bus/group")
public class BusGroupController {

    @Autowired
    private BusGroupService service;

    @PostMapping("/save")
    public R save(@RequestBody BusGroupDTO dto) {
        BusGroup entity = BeanUtil.copyProperties(dto, BusGroup.class);
        entity.setCreateTime(new Date());
        entity.setCreateUser(AuthUtil.getUserId());
        return R.status(service.save(entity));
    }

    @GetMapping("/delete")
    public R delete(@RequestParam("ids") String ids) {
        return R.status(service.removeByIds(Func.toLongList(ids)));
    }

    @PostMapping("/update/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody BusGroupDTO dto) {
        BusGroup entity = BeanUtil.copyProperties(dto, BusGroup.class);
        entity.setId(id);
        return R.status(service.updateById(entity));
    }

	@GetMapping("/list")
	public R<List<BusGroupVO>> groupList() {
		List<BusGroup> list = service.list();
		return R.data(BusGroupWrapper.build().listVO(list));
	}

    @GetMapping("/detail")
    public R<BusGroupVO> detail(@RequestParam("id") Long id) {
        BusGroup entity = service.getById(id);
        return R.data(BusGroupWrapper.build().entityVO(entity));
    }
}
