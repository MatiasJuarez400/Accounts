package com.matiasjuarez.money;

import com.matiasjuarez.money.model.Currency;
import com.matiasjuarez.utils.JsonConverter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/currencies")
public class CurrencyResource {
    @Inject
    private CurrencyService currencyService;

    @GET
    public Response hello() {
        try {
            List<Currency> currencies = currencyService.getCurrencies();
            return Response.ok(JsonConverter.convert(currencies), MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
