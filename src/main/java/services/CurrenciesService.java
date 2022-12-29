package services;

import ORM.CRUDCurrenciesORM;
import models.Currencies;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CurrenciesService {
    private final CRUDCurrenciesORM ormCurrencies;

    public CurrenciesService(CRUDCurrenciesORM ormCurrencies) {
        this.ormCurrencies = ormCurrencies;
    }
    public ArrayList<Currencies> readByPlId(long playerId) throws SQLException{
        return ormCurrencies.readByPlayerId(playerId);
    }
    public void write(List<Currencies> currencies) throws SQLException{
        for (Currencies c : currencies)
            write(c);
    }
    public void write(Currencies c) throws SQLException{
        ormCurrencies.create(c);
    }
    public Currencies readDB(long id) throws SQLException {
       return ormCurrencies.read(id);
    }
    public void updateDB(Currencies c) throws SQLException{
        ormCurrencies.update(c);
    }
    public void delete(long id) throws SQLException{
        ormCurrencies.delete(id);
    }
    public static List<Currencies> jsonToCurrencies(JSONArray jArrCurrency)
    {
        List<Currencies> currencies = new LinkedList<>();

        for (Object o : jArrCurrency)
        {
            JSONObject jPrgs = (JSONObject) o;
            currencies.add(new Currencies ((long) jPrgs.get("id"), (long) jPrgs.get("playerId"),
                    (long) jPrgs.get("resourceId"), (String) jPrgs.get("name"), (long) jPrgs.get("count")));
        }

        return currencies;
    }


    public static JSONArray currenciesToJson(List<Currencies> currencies)
    {
        JSONArray jsonCurrenciesArr = new JSONArray();
        for (Currencies currency : currencies)
            jsonCurrenciesArr.add(currenciesToJson(currency));

        return jsonCurrenciesArr;
    }
    public static JSONObject currenciesToJson(Currencies currencies)
    {
        JSONObject jsonCurrency = new JSONObject();

        jsonCurrency.put("id", currencies.getId());
        jsonCurrency.put("playerId", currencies.getPlayerId());
        jsonCurrency.put("resourceId", currencies.getResourceId());
        jsonCurrency.put("name", currencies.getName());
        jsonCurrency.put("count", currencies.getCount());

        return jsonCurrency;
    }
}
