package ORM;

import models.Player;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlayerORM extends ORMInterface<Player> {
    ArrayList<Player> readAllPlayers() throws SQLException;
}
