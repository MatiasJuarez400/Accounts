package com.matiasjuarez.api.customer;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.api.BaseService;
import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.api.errorhandling.exceptions.UpdateNotPerformedException;
import com.matiasjuarez.model.customer.Customer;

import java.sql.SQLException;

public class CustomerServiceImpl extends BaseService implements CustomerService {
    @Override
    public Customer getCustomer(Long id) throws SQLException {
        return getCustomerDao().queryForId(id);
    }

    @Override
    public Customer createCustomer(Customer customer) throws SQLException {
        Customer newCustomer = getCustomerDao().createIfNotExists(customer);
        return newCustomer;
    }

    @Override
    public Customer updateCustomer(Customer customer) throws SQLException {
        Customer storedCustomer = getCustomer(customer.getId());

        if (storedCustomer == null) {
            throw new EntityNotFoundException(EntityNames.CUSTOMER, customer.getId());
        }

        int updateResult = getCustomerDao().update(customer);

        if (updateResult != 1) {
            throw new UpdateNotPerformedException(EntityNames.CUSTOMER, customer.getId());
        }

        return customer;
    }

    private Dao<Customer, Long> getCustomerDao() {
        return inMemoryDBManager.getDaoForClass(Customer.class);
    }
}
