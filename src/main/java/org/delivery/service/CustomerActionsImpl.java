package org.delivery.service;

import lombok.extern.slf4j.Slf4j;
import org.delivery.exception.OrderStateException;
import org.delivery.model.*;
import org.delivery.repository.DriverRepository;
import org.delivery.repository.OrderRepository;
import org.delivery.utils.OrderAssignmentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CustomerActionsImpl implements CustomerActions {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    OrderAssignment orderAssignment;

    @Override
    public void createOrder(Customer customer, Map<String,String> details) {
        Customer.Order order = Customer.Order.builder()
                .orderId("Order-"+ Instant.now())
                .orderStatus(OrderStatus.ORDERED)
                .senderName(customer.getName())
                .senderContactNo(customer.getContactNo())
                .senderAddress(details.get(P2PConstants.SENDER_ADDR))
                .receiverName(details.get(P2PConstants.RECEIVER_NAME))
                .receiverContactNo(details.get(P2PConstants.RECEIVER_MOB))
                .receiverAddress(details.get(P2PConstants.RECEIVER_ADDR))
                .build();

        customer.getOrders().add(order);

        orderRepository.save(order.getOrderId(),order);
        orderAssignment.assignThisOrderToAvailableDriver(order);

    }

    @Override
    public void cancelOrder(Customer customer, Customer.Order order) throws OrderStateException{

        Optional<Customer.Order> filteredOrder= customer.getOrders().stream().filter(order1 -> order1.getOrderId()==order.getOrderId()).findFirst();
        if(filteredOrder.isPresent() && filteredOrder.get().getOrderStatus() != OrderStatus.PICKED){
            filteredOrder.get().setOrderStatus(OrderStatus.CANCELED);

            String driverId = filteredOrder.get().getAssignedDriverId();
            Driver assignedDriver = driverRepository.findById(driverId);

            if(assignedDriver!=null)
                assignedDriver.setDriverStatus(DriverStatus.READY);
            filteredOrder.get().setAssignedDriverId(null);

            orderRepository.save(filteredOrder.get().getOrderId(),filteredOrder.get());
            driverRepository.save(assignedDriver.getDriverId(),assignedDriver);
        }else{
            log.error("Cannot cancel Order:"+order+" as its already picked");
            throw new OrderStateException("Cannot cancel Order:"+order+" as its already picked");
        }
    }
}
