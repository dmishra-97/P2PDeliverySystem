package org.delivery.service;

import org.delivery.model.Customer;
import org.delivery.model.Driver;

public interface DriverActions {
    void pickOrder(Driver driver);
    void deliverOrder(Driver driver);
    void markDutyEnd(Driver driver);
    void markDutyStart(Driver driver);
}
