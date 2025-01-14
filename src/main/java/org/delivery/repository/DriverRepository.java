package org.delivery.repository;


import org.delivery.model.Driver;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class DriverRepository{

    private final Map<String, Driver> storage = new HashMap<>();

    public void save(String key, Driver value){
        storage.put(key,value);
    }

    public Driver findById(String key){
        return storage.get(key);
    }

    public List<Driver> findAll(){
        return storage.values().stream().collect(Collectors.toList());
    }

    public void deleteById(String key){
        storage.remove(key);
    }

    public boolean existsById(String key){
        return storage.containsKey(key);
    }

}
