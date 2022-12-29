package ORM;

import models.Items;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CRUDItemsORM implements ItemsORM{
    private final Connection connection;

    public CRUDItemsORM(Connection connection) {
        this.connection = connection;
    }


    @Override
    public ArrayList<Items> readByPlayerId(long playerId) throws SQLException {
        ArrayList<Items> itemsList = new ArrayList<>();
        String req = "SELECT * FROM items WHERE playerid = ?";
        try(PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1,playerId);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()){
                    itemsList.add(new Items(resultSet.getLong("id"),resultSet.getLong("playerid"),
                            resultSet.getLong("resourceid"),resultSet.getLong("count"),
                            resultSet.getLong("level")));
                }
                return itemsList;
            }
        }
    }

    @Override
    public void create(Items items) throws SQLException {
        if (items != null) {
            String req = "INSERT INTO items (id, playerId, resourceID, count, level) VALUES (?, ?, ?, ?, ?) ";
            try (PreparedStatement statement = connection.prepareStatement(req)) {
                statement.setLong(1, items.getId());
                statement.setLong(2, items.getPlayerId());
                statement.setLong(3, items.getResourceId());
                statement.setLong(4, items.getCount());
                statement.setLong(5, items.getLevel());

                statement.execute();
            }
        }
    }

    @Override
    public Items read(long id) throws SQLException {
        String req = "SELECT * FROM items WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery())
            {
                resultSet.next();
                return new Items(resultSet.getLong("id"), resultSet.getLong("playerId"),
                        resultSet.getLong("resourceId"), resultSet.getLong("count"), resultSet.getLong("level"));
            }
        }
    }

    @Override
    public void update(Items items) throws SQLException {
        String req = "UPDATE items SET playerId = ?, resourceID = ?, count = ?, level = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1, items.getPlayerId());
            statement.setLong(2, items.getResourceId());
            statement.setLong(3, items.getCount());
            statement.setLong(4, items.getLevel());
            statement.setLong(5, items.getId());

            statement.execute();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        String req = "DELETE FROM items WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1, id);

            statement.execute();
        }
    }
}
