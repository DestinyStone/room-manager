package com.demo.common.core;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author zhou xiao fen
 * @Date 2023/10/31 0:15
 **/
public abstract class BaseEntityWrapper<E, V> {
    public BaseEntityWrapper() {
    }

    public abstract V entityVO(E entity);

    public List<V> listVO(List<E> list) {
        return (List)list.stream().map(this::entityVO).collect(Collectors.toList());
    }
}
