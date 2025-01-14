package org.delivery.repository;


import org.delivery.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OrderRepository{

    private final Map<String, Customer.Order> storage = new HashMap<>();

    public void save(String key, Customer.Order value){
        storage.put(key,value);
    }

    public Customer.Order findById(String key){
        return storage.get(key);
    }

    public List<Customer.Order> findAll(){
        return storage.values().stream().collect(Collectors.toList());
    }

    public void deleteById(String key){
        storage.remove(key);
    }

    public boolean existsById(String key){
        return storage.containsKey(key);
    }

}
