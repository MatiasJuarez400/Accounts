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

    @PUT
    @Path("/{accountId}")
    public Response updateCustomerAccount(@PathParam("accountId") Long accountId, Map<String, Object> requestBody) throws SQLException {
        CustomerAccount rawData = validateDataAndCreateRawCustomerAccount(requestBody);
        rawData.setId(accountId);

        CustomerAccount updatedCustomerAccount = customerAccountService.updateCustomerAccount(rawData);

        return ApiUtils.buildOkResponse(updatedCustomerAccount);
    }

    @POST
    public Response createCustomerAccount(Map<String, Object> requestBody) throws SQLException {
        CustomerAccount rawData = validateDataAndCreateRawCustomerAccount(requestBody);

        CustomerAccount created = customerAccountService.createCustomerAccount(rawData);

        return ApiUtils.buildCreatedResponse(created);
    }

    private CustomerAccount validateDataAndCreateRawCustomerAccount(Map<String, Object> requestBody) {
        if (requestBody == null) {
            throw getBadRequestException();
        }

        String countryCode = (String) requestBody.get(COUNTRY_CODE);
        Object rawCustomerId = requestBody.get(CUSTOMER_ID);
        Long customerId;

        if (StringUtils.isEmpty((String) requestBody.get(COUNTRY_CODE)) || requestBody.get(CUSTOMER_ID) == null) {
            throw getBadRequestException();
        }

        try {
            customerId = Long.valueOf((Integer)rawCustomerId);
        } catch (Exception e) {
            throw new BadRequestException(
                    String.format("Value for %s must be a number. Received [%s]",
                            CUSTOMER_ID, rawCustomerId)
            );
        }


        Country country = new Country();
        country.setCode(countryCode);

        Customer customer = new Customer();
        customer.setId(customerId);

        CustomerAccount rawCustomerAccount = new CustomerAccount();
        rawCustomerAccount.setCustomer(customer);
        rawCustomerAccount.setBaseCountry(country);

        return rawCustomerAccount;
    }

    private BadRequestException getBadRequestException() {
        return new BadRequestException(
                String.format("Values for %s and %s must be present in request body", COUNTRY_CODE, CUSTOMER_ID));
    }
}
