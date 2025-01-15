package org.delivery.service;

import lombok.extern.slf4j.Slf4j;
import org.delivery.model.Customer;
import org.delivery.model.Driver;
import org.delivery.model.DriverStatus;
import org.delivery.model.OrderStatus;
import org.delivery.repository.DriverRepository;
import org.delivery.repository.OrderRepository;
import org.delivery.utils.OrderAssignmentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderAssignmentImpl implements OrderAssignment{

    @Autowired
    OrderAssignmentUtility orderAssignmentUtility;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DriverRepository driverRepository;

    @Override
    @Scheduled(cron= "*/2 * * * *")
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

    public void assignThisOrderToAvailableDriver(Customer.Order order){
        List<Driver> drivers = driverRepository.findAll();
        Optional<Driver> driverOptional = drivers.stream().filter(driver -> driver.getDriverStatus()==DriverStatus.READY).findAny();

        if(driverOptional.isPresent()){
            order.setAssignedDriverId(driverOptional.get().getDriverId());
            driverOptional.get().setAssignedOrderId(order.getOrderId());
            orderRepository.save(order.getOrderId(),order);
            driverRepository.save(driverOptional.get().getDriverId(), driverOptional.get());
        }

    }

    public void assignAvailableOrderToThisDriver(Driver driver){

        List<Customer.Order> orders = orderRepository.findAll();
        Optional<Customer.Order> orderOptional = orders.stream().filter(order -> order.getOrderStatus()==OrderStatus.ORDERED).findAny();

        if(orderOptional.isPresent()){
            driver.setAssignedOrderId(orderOptional.get().getOrderId());
            orderOptional.get().setAssignedDriverId(driver.getDriverId());
            orderRepository.save(orderOptional.get().getOrderId(),orderOptional.get());
            driverRepository.save(driver.getDriverId(), driver);
        }

    }

}
