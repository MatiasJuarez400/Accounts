package com.matiasjuarez.api.currency;

import com.matiasjuarez.api.ApiUtils;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.model.money.Currency;
import com.matiasjuarez.utils.JsonConverter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/currencies")
@Produces(MediaType.APPLICATION_JSON)
public class CurrencyResource {
    @Inject
    private CurrencyService currencyService;

    public CurrencyResource(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public CurrencyResource() {}

    @GET
    public Response getCurrencies() throws SQLException {
        List<Currency> currencies = currencyService.getCurrencies();
        return ApiUtils.buildOkResponse(currencies);
    }

    @GET
    @Path("/{ticker}")
    public Response getCurrency(@PathParam("ticker") String ticker) throws SQLException, EntityNotFoundException {
        Currency currency = currencyService.getCurrency(ticker);

        if (currency == null) {
            throw new EntityNotFoundException(ticker);
        }

        return ApiUtils.buildOkResponse(currency);
    }
}
