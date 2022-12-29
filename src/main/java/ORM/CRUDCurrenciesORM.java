package ORM;

import models.Currencies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CRUDCurrenciesORM implements CurrenciesORM{
    private final Connection connection;

    public CRUDCurrenciesORM(Connection connection){
        this.connection = connection;
    }

    @Override
    public ArrayList<Currencies> readByPlayerId(long playerId) throws SQLException {
        String req = "SELECT * FROM currencies WHERE playerID = ?";
        ArrayList<Currencies> currenciesArrayList = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setLong(1,playerId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    currenciesArrayList.add(new Currencies(resultSet.getLong(1),
                            resultSet.getLong(2),resultSet.getLong(3),
                            resultSet.getString(4),resultSet.getLong(5)));
                }
                return currenciesArrayList;
            }
        }
    }

    @Override
    public void create(Currencies currencies) throws SQLException {
        if (currencies != null) {
            String req = "INSERT INTO currencies (id, playerId, resourceId, name, count) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(req)) {
                statement.setLong(1, currencies.getId());
                statement.setLong(2, currencies.getPlayerId());
                statement.setLong(3, currencies.getResourceId());
                statement.setString(4, currencies.getName());
                statement.setLong(5, currencies.getCount());
                statement.execute();
            }
        }
    }

    @Override
    public Currencies read(long id) throws SQLException {
        String req = "SELECT * FROM currencies WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1,id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return new Currencies(resultSet.getLong("id"),resultSet.getLong("playerId"),
                        resultSet.getLong("resourceId"),resultSet.getString("name"),
                        resultSet.getLong("count"));
            }
        }
    }

    @Override
    public void update(Currencies currencies) throws SQLException {
        String req = "UPDATE currencies SET playerId = ?, resourceID = ?, name = ?, count = ? WHERE id = ?" ;
        try(PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1, currencies.getPlayerId());
            statement.setLong(2, currencies.getResourceId());
            statement.setString(3, currencies.getName());
            statement.setLong(4, currencies.getCount());
            statement.setLong(5, currencies.getId());
            statement.execute();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        String req = "DELETE FROM currencies WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
        }
    }
}
