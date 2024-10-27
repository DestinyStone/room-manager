package com.demo.service.impl;

import com.demo.common.utils.AuthUtil;
import com.demo.entity.BusRoom;
import com.demo.repository.BusRoomRepository;
import com.demo.service.BusRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusRoomServiceImpl implements BusRoomService {

    @Autowired
    private BusRoomRepository repository;

    @Override
    public List<Long> getAccessIds() {
        return repository.findAll().stream().map(BusRoom::getId).collect(Collectors.toList());
    }

    @Override
    public boolean save(BusRoom convert) {
        repository.save(convert);
        return true;
    }

    @Override
    public List<BusRoom> list() {
        return repository.findAll();
    }

    @Override
    public boolean removeByIds(List<Long> ids) {
        for (Long id : ids) {
            repository.deleteById(id);
        }
        return true;
    }

    @Override
    public boolean updateById(BusRoom entity) {
        repository.save(entity);
        return true;
    }

    @Override
    public BusRoom getById(Long id) {
        return repository.getOne(id);
    }
}
