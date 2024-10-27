package com.demo.service;


import com.demo.entity.BusRoom;

import java.util.List;

public interface BusRoomService {
    List<Long> getAccessIds();

    boolean save(BusRoom convert);

    List<BusRoom> list();

    boolean removeByIds(List<Long> toLongList);

    boolean updateById(BusRoom entity);

    BusRoom getById(Long id);
}
