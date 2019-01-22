package com.matiasjuarez.api.customeraccount;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.api.BaseService;
import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.country.CountryService;
import com.matiasjuarez.api.customer.CustomerService;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.api.errorhandling.exceptions.InconsistentDataException;
import com.matiasjuarez.api.errorhandling.exceptions.UpdateNotPerformedException;
import com.matiasjuarez.customer.CustomerAccount;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class CustomerAccountServiceImpl extends BaseService implements CustomerAccountService {
    @Inject
    private CustomerService customerService;
    @Inject
    private CountryService countryService;

    public CustomerAccountServiceImpl(CustomerService customerService, CountryService countryService) {
        this.customerService = customerService;
        this.countryService = countryService;
    }

    @Override
    public CustomerAccount getCustomerAccount(Long id) throws SQLException {
        return getDao().queryForId(id);
    }

    @Override
    public List<CustomerAccount> getCustomerAccounts() throws SQLException {
        return getDao().queryForAll();
    }

    @Override
    public CustomerAccount updateCustomerAccount(CustomerAccount newCustomerAcountData) throws SQLException {
        validateForeignData(newCustomerAcountData);

        CustomerAccount storedCustomerAccount = getDao().queryForId(newCustomerAcountData.getId());
        if (storedCustomerAccount == null) {
            throw new EntityNotFoundException(EntityNames.CUSTOMER_ACCOUNT, newCustomerAcountData.getId());
        }

        storedCustomerAccount.setBaseCountry(newCustomerAcountData.getBaseCountry());
        storedCustomerAccount.setCustomer(newCustomerAcountData.getCustomer());

        int updateResult = getDao().update(storedCustomerAccount);

        if (updateResult != 1) {
            throw new UpdateNotPerformedException(EntityNames.CUSTOMER_ACCOUNT, newCustomerAcountData.getId());
        }

        return storedCustomerAccount;
    }

    @Override
    public CustomerAccount createCustomerAccount(CustomerAccount customerAccount) throws SQLException {
        validateForeignData(customerAccount);

        CustomerAccount newCustomerAccount = getDao().createIfNotExists(customerAccount);

        return newCustomerAccount;
    }

    private Dao<CustomerAccount, Long> getDao() {
        return inMemoryDBManager.getDaoForClass(CustomerAccount.class);
    }

    private void validateForeignData(CustomerAccount newCustomerAcountData) throws SQLException {
        if (newCustomerAcountData.getCustomer() == null || newCustomerAcountData.getCustomer().getId() == null) {
            throw new InconsistentDataException("Customer data must be present and have an id");
        }

        if (newCustomerAcountData.getBaseCountry() == null || newCustomerAcountData.getBaseCountry().getCode() == null) {
            throw new InconsistentDataException("Country data must be present and have a country code");
        }

        if (countryService.getCountry(newCustomerAcountData.getBaseCountry().getCode()) == null) {
            throw new EntityNotFoundException(EntityNames.COUNTRY, newCustomerAcountData.getBaseCountry().getId());
        }

        if (customerService.getCustomer(newCustomerAcountData.getCustomer().getId()) == null) {
            throw new EntityNotFoundException(EntityNames.COUNTRY, newCustomerAcountData.getCustomer().getId());
        }
    }
}
