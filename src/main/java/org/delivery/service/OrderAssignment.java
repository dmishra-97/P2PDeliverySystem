package org.delivery.service;

import org.delivery.model.Customer;
import org.delivery.model.Driver;

import java.util.List;

public interface OrderAssignment {

    void assignOrdersToAvailableDriver(List<Customer.Order> orders, List<Driver> drivers);
    void assignThisOrderToAvailableDriver(Customer.Order order);
    void assignAvailableOrderToThisDriver(Driver driver);


}
