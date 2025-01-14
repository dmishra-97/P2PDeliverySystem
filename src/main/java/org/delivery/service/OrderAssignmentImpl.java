package org.delivery.service;

import org.delivery.model.Customer;
import org.delivery.model.Driver;
import org.delivery.model.DriverStatus;
import org.delivery.model.OrderStatus;
import org.delivery.utils.OrderAssignmentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class OrderAssignmentImpl implements OrderAssignment{

    @Autowired
    OrderAssignmentUtility orderAssignmentUtility;

    @Override
    @Scheduled(cron= "*/1 * * * *")
    public void assignOrdersToAvailableDriver(List<Customer.Order> orders, List<Driver> drivers) {
        List<Customer.Order> unassignedOrders= orderAssignmentUtility.findPendingUnassignedOrders(orders);
        List<Driver> freeDrivers= orderAssignmentUtility.findFreeDrivers(drivers);

        int minimumResource = Math.min(unassignedOrders.size(),freeDrivers.size());

        for(int i=0;i<minimumResource;i++){
            Customer.Order order = unassignedOrders.get(i);
            Driver driver = freeDrivers.get(i);
            order.setOrderStatus(OrderStatus.ASSIGNED);
            driver.setDriverStatus(DriverStatus.BUSY);
            order.setAssignedDriverId(driver.getDriverId());
            driver.setAssignedOrderId(order.getOrderId());
        }
    }

}
