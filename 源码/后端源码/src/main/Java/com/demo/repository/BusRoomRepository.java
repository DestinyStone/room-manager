package com.demo.repository;

import com.demo.entity.BusGroup;
import com.demo.entity.BusRoom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BusRoomRepository extends JpaRepository<BusRoom, Long> {
}
