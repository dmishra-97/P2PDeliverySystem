package org.delivery.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    String customerId;
    String name;
    String contactNo;
    List<Order> orders= new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    @EqualsAndHashCode
    public class Order{

        String orderId;
        OrderStatus orderStatus;
        String receiverName;
        String receiverAddress;
        String receiverContactNo;
        String senderName;
        String senderAddress;
        String senderContactNo;
        String assignedDriverId;
    }
}
