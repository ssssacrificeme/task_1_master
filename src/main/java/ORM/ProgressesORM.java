package ORM;

import models.Progresses;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProgressesORM extends ORMInterface<Progresses>{
    ArrayList<Progresses> readByPlayerId(long playerId) throws SQLException;
}
