package ORM;

import models.Progresses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CRUDProgressesORM implements ProgressesORM{
    private final Connection connection;

    public CRUDProgressesORM(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Progresses val) throws SQLException {
            String req = "INSERT INTO progresses (id, playerId, resourceID, score, maxScore) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(req)) {
                statement.setLong(1, val.getId());
                statement.setLong(2, val.getPlayerId());
                statement.setLong(3, val.getResourceId());
                statement.setLong(4, val.getScore());
                statement.setLong(5, val.getMaxScore());

                statement.execute();
            }
    }

    @Override
    public Progresses read(long id) throws SQLException {
        String req = "SELECT * FROM progresses WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery())
            {
                resultSet.next();
                return new Progresses(resultSet.getLong("id"), resultSet.getLong("playerId"),
                        resultSet.getLong("resourceId"), resultSet.getLong("score"), resultSet.getLong("maxScore"));
            }
        }
    }

    @Override
    public void update(Progresses val) throws SQLException {
        String req = "UPDATE progresses SET playerId = ?, resourceID = ?, score = ?, maxScore = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1, val.getPlayerId());
            statement.setLong(2, val.getResourceId());
            statement.setLong(3, val.getScore());
            statement.setLong(4, val.getMaxScore());
            statement.setLong(5, val.getId());

            statement.execute();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        String req = "DELETE FROM progresses WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1, id);

            statement.execute();
        }
    }

    @Override
    public ArrayList<Progresses> readByPlayerId(long playerId) throws SQLException {
        ArrayList<Progresses> progressesArrayList = new ArrayList<>();
        String req = "SELECT * FROM progresses WHERE playerID = ?";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1, playerId);

            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                    progressesArrayList.add(new Progresses(resultSet.getLong("id"), resultSet.getLong("playerId"),
                            resultSet.getLong("resourceId"), resultSet.getLong("score"), resultSet.getLong("maxScore")));

                return progressesArrayList;
            }
        }
    }
}
