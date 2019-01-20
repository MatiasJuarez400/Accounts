package com.matiasjuarez.money.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/currency")
public class CurrencyResource {

    @GET
    public String hello() {
        return "Hola desde currency";
    }
}
