package com.demo.service;

import com.demo.entity.BusGroup;

import java.util.List;

public interface BusGroupService{
    boolean save(BusGroup entity);

    boolean removeByIds(List<Long> toLongList);

    List<BusGroup> list();

    boolean updateById(BusGroup entity);

    BusGroup getById(Long id);
}
