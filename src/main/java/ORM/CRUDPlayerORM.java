package ORM;

import models.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CRUDPlayerORM implements PlayerORM{
    private final Connection connection;

    public CRUDPlayerORM(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Player player) throws SQLException {
        String req = "INSERT INTO players (playerId, nickname) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1, player.getPlayerId());
            statement.setString(2, player.getNickname());

            statement.execute();
        }
    }

    @Override
    public Player read(long id) throws SQLException {
        String req = "SELECT * FROM players WHERE playerId = ?";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery())
            {
                resultSet.next();
                Player player = new Player(resultSet.getLong("playerid"), resultSet.getString("nickname"),
                        null,null,null);
                return player;
            }
        }
    }


    @Override
    public void update(Player player) throws SQLException {
        String req = "UPDATE players SET nickname = ? WHERE playerId = ?";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setString(1, player.getNickname());
            statement.setLong(2, player.getPlayerId());

            statement.execute();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        String req = "DELETE FROM players WHERE playerId = ?";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            statement.setLong(1, id);

            statement.execute();
        }
    }

    @Override
    public ArrayList<Player> readAllPlayers() throws SQLException {
        ArrayList<Player> players = new ArrayList<>();
        String req = "SELECT * FROM players";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                    players.add(new Player(resultSet.getLong("playerId"),
                            resultSet.getString("nickname"),null,null,null));

                return players;
            }
        }
    }
}
