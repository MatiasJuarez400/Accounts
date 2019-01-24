package com.matiasjuarez.api.customeraccount;

import com.matiasjuarez.model.customer.CustomerAccount;

import java.sql.SQLException;
import java.util.List;

public interface CustomerAccountService {
    CustomerAccount getCustomerAccount(Long id) throws SQLException;
    List<CustomerAccount> getCustomerAccounts() throws SQLException;
    CustomerAccount createCustomerAccount(CustomerAccount customerAccount) throws SQLException;
}
