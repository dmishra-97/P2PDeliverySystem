package org.delivery.service;

import org.delivery.model.Customer;
import org.delivery.model.Driver;
import org.delivery.model.DriverStatus;
import org.delivery.model.OrderStatus;
import org.delivery.repository.DriverRepository;
import org.delivery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DriverActionsImpl implements DriverActions{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DriverRepository driverRepository;

    @Override
    public void pickOrder(Driver driver) {
        if(driver.getAssignedOrderId()!=null ) {
            Customer.Order order = orderRepository.findById(driver.getAssignedOrderId());
            if(order.getOrderStatus()!=OrderStatus.CANCELED) {
                order.setOrderStatus(OrderStatus.PICKED);
                driverRepository.save(driver.getDriverId(), driver);
                orderRepository.save(order.getOrderId(), order);
            }
        }

    }

    @Override
    public void deliverOrder(Driver driver) {

        Customer.Order order = orderRepository.findById(driver.getAssignedOrderId());
        order.setOrderStatus(OrderStatus.DELIVERED);
        driver.setDriverStatus(DriverStatus.READY);
        driverRepository.save(driver.getDriverId(),driver);
        orderRepository.save(order.getOrderId(), order);

    }

    @Override
    public void markDutyEnd(Driver driver) {
        driver.setDriverStatus(DriverStatus.OFFLINE);
        driverRepository.save(driver.getDriverId(),driver);
    }

    @Override
    public void markDutyStart(Driver driver) {
        driver.setDriverStatus(DriverStatus.READY);
        driverRepository.save(driver.getDriverId(),driver);
    }
}
