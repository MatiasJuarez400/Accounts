package com.matiasjuarez.api.customeraccount;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.api.BaseService;
import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.country.CountryService;
import com.matiasjuarez.api.customer.CustomerService;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.api.errorhandling.exceptions.UpdateNotPerformedException;
import com.matiasjuarez.model.customer.CustomerAccount;

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

    public CustomerAccountServiceImpl() {}

    @Override
    public CustomerAccount getCustomerAccount(Long id) throws SQLException {
        return getDao().queryForId(id);
    }

    @Override
    public List<CustomerAccount> getCustomerAccounts() throws SQLException {
        return getDao().queryForAll();
    }

    @Override
    public CustomerAccount createCustomerAccount(CustomerAccount customerAccount) throws SQLException {
        validateForeignData(customerAccount);

        CustomerAccount newCustomerAccount = getDao().createIfNotExists(customerAccount);

        return getCustomerAccount(newCustomerAccount.getId());
    }

    private Dao<CustomerAccount, Long> getDao() {
        return inMemoryDBManager.getDaoForClass(CustomerAccount.class);
    }

    private void validateForeignData(CustomerAccount newCustomerAcountData) throws SQLException {
        if (countryService.getCountry(newCustomerAcountData.getBaseCountry().getCode()) == null) {
            throw new EntityNotFoundException(EntityNames.COUNTRY, newCustomerAcountData.getBaseCountry().getCode());
        }

        if (customerService.getCustomer(newCustomerAcountData.getCustomer().getId()) == null) {
            throw new EntityNotFoundException(EntityNames.CUSTOMER, newCustomerAcountData.getCustomer().getId());
        }
    }
}
