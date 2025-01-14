package org.delivery.service;

import org.delivery.exception.OrderStateException;
import org.delivery.model.Customer;

import java.util.Map;

public interface CustomerActions {

    void createOrder(Customer customer, Map<String,String> details);
    void cancelOrder(Customer customer, Customer.Order order) throws OrderStateException;
}
