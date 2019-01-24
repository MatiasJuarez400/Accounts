package com.matiasjuarez.api.customeraccount;

import com.matiasjuarez.api.ApiUtils;
import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.model.customer.Country;
import com.matiasjuarez.model.customer.Customer;
import com.matiasjuarez.model.customer.CustomerAccount;
import com.matiasjuarez.utils.JsonConverter;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.matiasjuarez.api.customeraccount.CustomerAccountResource.CUSTOMER_ACCOUNTS_PATH;

@Path(CUSTOMER_ACCOUNTS_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerAccountResource {

    @Inject
    private CustomerAccountService customerAccountService;

    private static final String COUNTRY_CODE = "countryCode";
    private static final String CUSTOMER_ID = "customerId";

    public static final String CUSTOMER_ACCOUNTS_PATH = "/customeraccounts";

    public CustomerAccountResource(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }

    public CustomerAccountResource() {}

    @GET
    @Path("/{accountId}")
    public Response getCustomerAccount(@PathParam("accountId") Long accountId) throws SQLException {
        CustomerAccount customerAccount = customerAccountService.getCustomerAccount(accountId);

        if (customerAccount == null) {
            throw new EntityNotFoundException(EntityNames.CUSTOMER_ACCOUNT, accountId);
        }

        return ApiUtils.buildOkResponse(customerAccount);
    }

    @GET
    public Response getCustomerAccounts() throws SQLException {
        List<CustomerAccount> customerAccounts = customerAccountService.getCustomerAccounts();

        return ApiUtils.buildOkResponse(customerAccounts);
    }

    @POST
    public Response createCustomerAccount(Map<String, Object> requestBody) throws SQLException {
        CustomerAccount rawData = validateDataAndCreateRawCustomerAccount(requestBody);

        CustomerAccount created = customerAccountService.createCustomerAccount(rawData);

        return ApiUtils.buildCreatedResponse(created);
    }

    private CustomerAccount validateDataAndCreateRawCustomerAccount(Map<String, Object> requestBody) {
        ApiUtils.validateIfValuesArePresent(requestBody, COUNTRY_CODE, CUSTOMER_ID);

        String countryCode = (String) requestBody.get(COUNTRY_CODE);
        Long customerId = ApiUtils.convertRequestValueToLong(CUSTOMER_ID, requestBody);

        Country country = new Country();
        country.setCode(countryCode);

        Customer customer = new Customer();
        customer.setId(customerId);

        CustomerAccount rawCustomerAccount = new CustomerAccount();
        rawCustomerAccount.setCustomer(customer);
        rawCustomerAccount.setBaseCountry(country);

        return rawCustomerAccount;
    }
}
