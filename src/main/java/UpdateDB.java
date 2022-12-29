import ORM.CRUDCurrenciesORM;
import ORM.CRUDItemsORM;
import ORM.CRUDPlayerORM;
import ORM.CRUDProgressesORM;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Currencies;
import models.Items;
import models.Player;
import models.Progresses;
import services.CurrenciesService;
import services.ItemsService;
import services.PlayersService;
import services.ProgressesService;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static services.DBService.*;

public class UpdateDB {

    public static void main(String[] args) throws IOException, SQLException {
        List<Player> playerList = jsonStringToPojo(jsonPath);
        saveToDB(playerList);
    }


}

