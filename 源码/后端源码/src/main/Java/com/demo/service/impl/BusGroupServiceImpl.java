package com.demo.service.impl;

import com.demo.entity.BusGroup;
import com.demo.repository.BusGroupRepository;
import com.demo.service.BusGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusGroupServiceImpl implements BusGroupService {

    @Autowired
    private BusGroupRepository repository;

    @Override
    public boolean save(BusGroup entity) {
        repository.save(entity);
        return true;
    }

    @Override
    public boolean removeByIds(List<Long> ids) {
        for (Long id : ids) {
            repository.deleteById(id);
        }
        return true;
    }

    @Override
    public List<BusGroup> list() {
        return repository.findAll();
    }

    @Override
    public boolean updateById(BusGroup entity) {
        repository.save(entity);
        return true;
    }

    @Override
    public BusGroup getById(Long id) {
        return repository.getOne(id);
    }
}
