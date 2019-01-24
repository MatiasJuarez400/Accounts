package com.matiasjuarez.api.monetaryaccount;

import com.matiasjuarez.api.ApiUtils;
import com.matiasjuarez.api.customeraccount.CustomerAccountResource;
import com.matiasjuarez.model.customer.CustomerAccount;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccountStatus;
import com.matiasjuarez.model.money.Currency;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.matiasjuarez.api.monetaryaccount.MonetaryAccountResource.MONETARY_ACCOUNTS_PATH;

@Path(MONETARY_ACCOUNTS_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class MonetaryAccountResource {
    public static final String MONETARY_ACCOUNTS_PATH = CustomerAccountResource.CUSTOMER_ACCOUNTS_PATH +
            "/{customerAccountId}/monetaryaccounts";

    private static final String ACCOUNT_CURRENCY_TICKER = "currencyTicker";
    private static final String STATUS_ACTIVE = "statusActive";

    @Inject
    private MonetaryAccountService monetaryAccountService;

    public MonetaryAccountResource(MonetaryAccountService monetaryAccountService) {
        this.monetaryAccountService = monetaryAccountService;
    }

    public MonetaryAccountResource() {}

    @POST
    public Response createMonetaryAccount(@PathParam("customerAccountId") Long customerAccountId,
                                          Map<String, Object> requestBody) throws SQLException {
        ApiUtils.validateIfValuesArePresent(requestBody, ACCOUNT_CURRENCY_TICKER, STATUS_ACTIVE);

        String accountCurrency = (String) requestBody.get(ACCOUNT_CURRENCY_TICKER);
        Currency currency = new Currency(accountCurrency);

        Boolean statusActive =
                ApiUtils.convertRequestValueToBoolean(STATUS_ACTIVE, requestBody);
        MonetaryAccountStatus monetaryAccountStatus = statusActive ?
                MonetaryAccountStatus.OPERATIVE : MonetaryAccountStatus.INACTIVE;

        CustomerAccount customerAccount = new CustomerAccount(customerAccountId);


        MonetaryAccount monetaryAccountToCreate = new MonetaryAccount(new BigDecimal(0), currency, monetaryAccountStatus, customerAccount);

        MonetaryAccount createdMonetaryAccount = monetaryAccountService.createMonetaryAccount(monetaryAccountToCreate);

        return ApiUtils.buildCreatedResponse(createdMonetaryAccount);
    }

    @GET
    public Response getMonetaryAccountsFromCustomerAccount(
            @PathParam("customerAccountId") Long customerAccountId) throws SQLException {

        List<MonetaryAccount> monetaryAccounts = monetaryAccountService.getMonetaryAccountsFromCustomerAccount(customerAccountId);

        return ApiUtils.buildOkResponse(monetaryAccounts);
    }

    @PUT
    @Path("/{monetaryAccountId}")
    public Response updateMonetaryAccount(@PathParam("monetaryAccountId") Long monetaryAccountId,
                                          @PathParam("customerAccountId") Long customerAccountId,
                                          Map<String, Object> requestBody) throws SQLException {

        ApiUtils.validateIfValuesArePresent(requestBody, STATUS_ACTIVE);

        Boolean statusActive =
                ApiUtils.convertRequestValueToBoolean(STATUS_ACTIVE, requestBody);

        MonetaryAccount monetaryAccountUpdateData = new MonetaryAccount();
        monetaryAccountUpdateData.setId(monetaryAccountId);
        monetaryAccountUpdateData.setCustomerAccount(new CustomerAccount(customerAccountId));
        monetaryAccountUpdateData.changeStatus(statusActive);

        MonetaryAccount updatedMonetaryAccount = monetaryAccountService.updateMonetaryAccount(monetaryAccountUpdateData);

        return ApiUtils.buildOkResponse(updatedMonetaryAccount);
    }
}
