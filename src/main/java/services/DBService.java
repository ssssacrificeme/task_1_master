package services;

import ORM.CRUDCurrenciesORM;
import ORM.CRUDItemsORM;
import ORM.CRUDPlayerORM;
import ORM.CRUDProgressesORM;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Player;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBService {
    public static final String jsonPath = "src/main/players.json";
    public static final Connection connection = getDBConnection();
    private static final CurrenciesService currenciesService = new CurrenciesService(new CRUDCurrenciesORM(getDBConnection()));
    private static final ItemsService itemsService = new ItemsService(new CRUDItemsORM(getDBConnection()));
    private static final ProgressesService progressesService = new ProgressesService(new CRUDProgressesORM(getDBConnection()));
    private static final PlayersService playersService = new PlayersService(new CRUDPlayerORM(getDBConnection()),currenciesService,
            itemsService,progressesService);
    public static List<Player> jsonStringToPojo(String jsonPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(jsonPath);
        try {
            return objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveToDB(List<Player> players) throws SQLException {
        try {
            PreparedStatement truncate_playersPrepared = connection.prepareStatement(
                    "TRUNCATE players,items,currencies,progresses CASCADE"

            );

            truncate_playersPrepared.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Player p : players) {
            playersService.writeToDB(p);

        }
    }
    public static Connection getDBConnection() {
        Connection dbConnection = null;
        try{
            Class.forName("org.postgresql.Driver");
            dbConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","kikiki","dashka0310");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Opened database successfully");
        return dbConnection;
    }

}
