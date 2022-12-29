package ORM;

import models.Currencies;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CurrenciesORM extends ORMInterface<Currencies> {
    ArrayList<Currencies> readByPlayerId(long playerId) throws SQLException;

}
