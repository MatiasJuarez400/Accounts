package com.matiasjuarez.api.customer;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.api.errorhandling.exceptions.UpdateNotPerformedException;
import com.matiasjuarez.customer.Customer;
import com.matiasjuarez.data.InMemoryDBManager;

import java.sql.SQLException;

public class CustomerServiceImpl implements CustomerService {
    private InMemoryDBManager inMemoryDBManager;

    private static final String ENTITY_NAME = "customer";

    public CustomerServiceImpl() {
        this.inMemoryDBManager = InMemoryDBManager.getInstance();
    }

    @Override
    public Customer getCustomer(long id) throws SQLException {
        return getCustomerDao().queryForId(id);
    }

    @Override
    public Customer createCustomer(Customer customer) throws SQLException {
        Customer newCustomer = getCustomerDao().createIfNotExists(customer);
        return newCustomer;
    }

    @Override
    public Customer updateCustomer(Customer customer) throws SQLException, EntityNotFoundException {
        Customer storedCustomer = getCustomer(customer.getId());

        if (storedCustomer == null) {
            throw new EntityNotFoundException(ENTITY_NAME, customer.getId());
        }

        int updateResult = getCustomerDao().update(customer);

        if (updateResult != 1) {
            throw new UpdateNotPerformedException(ENTITY_NAME, customer.getId());
        }

        return customer;
    }

    private Dao<Customer, Long> getCustomerDao() {
        return inMemoryDBManager.getDaoForClass(Customer.class);
    }
}
