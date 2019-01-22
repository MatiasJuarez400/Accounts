package com.matiasjuarez.api.customeraccount;

import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.customer.CustomerAccount;
import com.matiasjuarez.utils.JsonConverter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/customeraccount")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerAccountResource {

    @Inject
    private CustomerAccountService customerAccountService;

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

        return Response.ok(JsonConverter.convert(customerAccount)).build();
    }

    @GET
    public Response getCustomerAccounts() throws SQLException {
        List<CustomerAccount> customerAccounts = customerAccountService.getCustomerAccounts();

        return Response.ok(JsonConverter.convert(customerAccounts)).build();
    }

    @POST
    @Path("/{accountId}")
    public Response updateCustomerAccount() {

    }
}
