package com.example.com.api;

import com.example.com.bean.MoneyConverterBeanI;
import com.example.com.model.Denomination;
import com.google.gson.Gson;

import javax.ejb.EJB;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Path("/mc")
public class MoneyConverter {
    @EJB
    MoneyConverterBeanI moneyConverterBeanI;
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/get-denominations/{amount}")
    public Response getDenominationsForMoney(@PathParam("amount")BigDecimal amount) throws SQLException {
        Map m = moneyConverterBeanI.getDenominationsForMoney(amount);
           //return Response.status(500).build();
           //return Response.status(401).entity("Illegal Access").build();
        return Response.ok().entity(m).build();
    }
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/get-money-value")
    public Response getMoneyValue(String jsonMap){
        Gson gson = new Gson();
        Map <String, Integer>m = gson.fromJson(jsonMap.toString(),Map.class);
        Map<Denomination, Integer> map = new HashMap<>();
        for (Map.Entry m_: m.entrySet()){
            double value = (double) m_.getValue();
            map.put(Denomination.valueOf(m_.getKey().toString()),(int)value);
        }
        BigDecimal amount = moneyConverterBeanI.getMoneyValueFromDenominations(map);
        return Response.ok().entity(amount).build();
    }
}
