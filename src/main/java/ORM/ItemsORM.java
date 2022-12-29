package ORM;

import models.Items;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemsORM extends ORMInterface<Items>{
    ArrayList<Items> readByPlayerId(long playerId) throws SQLException;

}
