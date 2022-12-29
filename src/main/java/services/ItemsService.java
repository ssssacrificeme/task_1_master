package services;

import ORM.CRUDItemsORM;
import models.Items;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ItemsService {
    private final CRUDItemsORM ormItems;


    public ItemsService(CRUDItemsORM ormItems) { this.ormItems = ormItems; }

    public ArrayList<Items> readByPlId(long plId) throws SQLException {
        return ormItems.readByPlayerId(plId); }


    public void write(List<Items> items) throws SQLException
    {
        for (Items item : items)
            write(item);
    }

    public void write(Items i) throws SQLException { ormItems.create(i);}

    public Items readFromDB(long id) throws SQLException { return ormItems.read(id); }

    public void updateDB(Items i) throws SQLException { ormItems.update(i);}

    public void deleteFromDB(long id) throws SQLException { ormItems.delete(id); }


    public static JSONObject itemsToJson(Items item)
    {
        JSONObject jsonItem = new JSONObject();

        jsonItem.put("id", item.getId());
        jsonItem.put("playerId", item.getPlayerId());
        jsonItem.put("resourceId", item.getResourceId());
        jsonItem.put("count", item.getCount());
        jsonItem.put("level", item.getLevel());

        return jsonItem;
    }


    public static List<Items> jsonToItems(JSONArray jArrItem)
    {
        List<Items> items = new LinkedList<>();

        for (Object o : jArrItem)
        {
            JSONObject jPrgs = (JSONObject) o;
            items.add(new Items((long) jPrgs.get("id"), (long) jPrgs.get("playerId"),
                    (long) jPrgs.get("resourceId"), (long) jPrgs.get("count"), (long) jPrgs.get("level")));
        }

        return items;
    }


    public static JSONArray itemsToJson(List<Items> items)
    {
        JSONArray jsonItemsArr = new JSONArray();
        for (Items item : items)
            jsonItemsArr.add(itemsToJson(item));

        return jsonItemsArr;
    }
}
