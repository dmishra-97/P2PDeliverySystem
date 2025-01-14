package org.delivery.utils;

import lombok.extern.slf4j.Slf4j;
import org.delivery.model.Customer;
import org.delivery.model.Driver;
import org.delivery.model.DriverStatus;
import org.delivery.model.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OrderAssignmentUtility {

    public List<Customer.Order> findPendingUnassignedOrders(List<Customer.Order> orders) {
        List<Customer.Order> filteredList = null;
        if(orders!=null)
            filteredList = orders.stream().filter(order -> order.getOrderStatus()== OrderStatus.ORDERED).collect(Collectors.toList());

        return filteredList;
    }

    public List<Driver> findFreeDrivers(List<Driver> drivers) {
        List<Driver> filteredList = null;
        if(drivers!=null)
            filteredList = drivers.stream().filter(driver -> driver.getDriverStatus()== DriverStatus.READY).collect(Collectors.toList());

        return filteredList;
    }
}

