package com.matiasjuarez.api.customer;

import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.customer.Customer;

import java.sql.SQLException;

public interface CustomerService {
    Customer getCustomer(long id) throws SQLException;
    Customer createCustomer(Customer customer) throws SQLException;
    Customer updateCustomer(Customer customer) throws SQLException, EntityNotFoundException;
}
