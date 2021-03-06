package com.matiasjuarez.api.customer;

import com.matiasjuarez.api.ApiUtils;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.model.customer.Customer;
import com.matiasjuarez.utils.JsonConverter;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Map;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    private CustomerService customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerResource() {}

    @GET
    @Path("/{customerId}{format: (\\d+)?}")
    public Response getCustomer(@PathParam("customerId") Long customerId) throws SQLException {
        Customer customer = customerService.getCustomer(customerId);

        if (customer == null) {
            throw new EntityNotFoundException(customerId);
        }

        return ApiUtils.buildOkResponse(customer);
    }

    @POST
    public Response createCustomer(Map<String, Object> request) throws SQLException {
        Customer newCustomer = validateRequestBodyData(request);
        newCustomer = customerService.createCustomer(newCustomer);

        return ApiUtils.buildCreatedResponse(newCustomer);
    }

    @PUT
    @Path("/{customerId}{format: (\\d+)?}")
    public Response updateCustomer(@PathParam("customerId") Long customerId, Map<String, Object> requestBody) throws SQLException {
        Customer customerToUpdate = validateRequestBodyData(requestBody);
        customerToUpdate.setId(customerId);

        customerService.updateCustomer(customerToUpdate);

        return ApiUtils.buildOkResponse(customerToUpdate);
    }

    private Customer validateRequestBodyData(Map<String, Object> requestBody) {
        String customerName = (String) requestBody.get("name");
        String customerLastname = (String) requestBody.get("lastname");

        if (StringUtils.isEmpty(customerName) || StringUtils.isEmpty(customerLastname)) {
            throw new BadRequestException("Can not create new customer without name or lastname");
        }

        return new Customer(customerName, customerLastname);
    }
}
