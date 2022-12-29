package services;

import ORM.CRUDPlayerORM;
import models.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.List;

import static services.CurrenciesService.currenciesToJson;
import static services.ItemsService.itemsToJson;
import static services.ProgressesService.progressesToJson;

public class PlayersService {

    private final CRUDPlayerORM playerORM;
    private final CurrenciesService currenciesService;
    private final ItemsService itemsService;
    private final ProgressesService progressesService;

    public PlayersService(CRUDPlayerORM playerORM, CurrenciesService currenciesService, ItemsService itemsService, ProgressesService progressesService) {
        this.playerORM = playerORM;
        this.currenciesService = currenciesService;
        this.itemsService = itemsService;
        this.progressesService = progressesService;
    }


    public Player readFromDB(long id) throws SQLException {

        Player pl = playerORM.read(id);

        pl.setCurrencies(currenciesService.readByPlId(pl.getPlayerId()));
        pl.setItems(itemsService.readByPlId(pl.getPlayerId()));
        pl.setProgresses(progressesService.readAllWithPlId(pl.getPlayerId()));

        return pl;
    }


    public void writeToDB(Player pl) throws SQLException {
        playerORM.create(pl);
    }


    public void updateDB(Player pl) throws SQLException { playerORM.update(pl);}

    public void deleteFromDB(long id) throws SQLException { playerORM.delete(id); }


    public void writeToDB(List<Player> players) throws SQLException
    {
        for (Player player : players)
            writeToDB(player);
    }


    public List<Player> readAllPlayers() throws SQLException
    {
        List<Player> players = playerORM.readAllPlayers();

        for (Player player : players)
        {
            player.setCurrencies(currenciesService.readByPlId(player.getPlayerId()));
            player.setItems(itemsService.readByPlId(player.getPlayerId()));
            player.setProgresses(progressesService.readAllWithPlId(player.getPlayerId()));
        }

        return players;
    }


    // метод реализован через примитивную json-simple. Существуют гораздо менее многословные библиотеки
    // для парсинга json в Java. Однако json-simple использовалась для простоты восприятия т.к. это первая работа с json
    /*public static List<Player> readFromJson(String fileName) throws IOException, ParseException
    {
        // считываем json файл
        JSONArray jsonArr = (JSONArray) new JSONParser().parse(new FileReader(fileName));

        List<Player> players = new LinkedList<>();

        // идём по всем игрокам
        for (Object obj : jsonArr)
        {
            JSONObject playerJson = (JSONObject) obj;
            Player readPlayer = new Player((long) playerJson.get("playerId"), (String) playerJson.get("nickname"));

            // записываем currencies для игрока
            readPlayer.addCurrency(jsonToCurrencies((JSONArray) playerJson.get("currencies")));

            // записываем items для игрока
            readPlayer.addItem(jsonToItems((JSONArray) playerJson.get("items")));

            // записываем progresses для игрока
            readPlayer.addProgress(jsonToProgresses((JSONArray) playerJson.get("progresses")));

            players.add(readPlayer);
        }

        return players;
    }*/


    public static JSONObject playerToJson(Player player)
    {
        JSONObject jsonPlayer = new JSONObject();

        jsonPlayer.put("playerId", player.getPlayerId());
        jsonPlayer.put("nickname", player.getNickname());
        jsonPlayer.put("progresses", progressesToJson(player.getProgresses()));
        jsonPlayer.put("currencies", CurrenciesService.currenciesToJson(player.getCurrencies()));
        jsonPlayer.put("items", ItemsService.itemsToJson(player.getItems()));

        return jsonPlayer;
    }


    private static JSONArray playerToJson(List<Player> players)
    {
        JSONArray playersJsonView = new JSONArray();

        for (Player player : players)
            playersJsonView.add(playerToJson(player));

        return playersJsonView;
    }
}
