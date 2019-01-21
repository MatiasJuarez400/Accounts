package com.matiasjuarez.api.customer;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.customer.Customer;
import com.matiasjuarez.data.InMemoryDBManager;

import java.sql.SQLException;

public class CustomerServiceImpl implements CustomerService {
    private InMemoryDBManager inMemoryDBManager;

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
    public Customer updateCustomer(Customer customer) throws SQLException {
        int updatedCustomerId = getCustomerDao().update(customer);
        return customer;
    }

    @Override
    public boolean deleteCustomer(long id) throws SQLException {
        int deleteId = getCustomerDao().deleteById(id);
        return true;
    }

    private Dao<Customer, Long> getCustomerDao() {
        return inMemoryDBManager.getDaoForClass(Customer.class);
    }
}
