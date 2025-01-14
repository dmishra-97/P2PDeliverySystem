package org.delivery.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Driver {

    String driverId;
    String driverName;
    String contactNo;
    DriverStatus driverStatus;
    String assignedOrderId;

}
