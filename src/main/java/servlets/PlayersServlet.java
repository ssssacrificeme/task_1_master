package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Player;
import org.apache.commons.io.IOUtils;
import services.PlayersService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class PlayersServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    private final PlayersService playersService;

    public PlayersServlet(PlayersService playersService) {
        this.playersService = playersService;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("playerId"));
        try {
            Player player = playersService.readFromDB(id);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(objectWriter.writeValueAsString(player));
            printWriter.close();
        } catch (SQLException e) {
            throw new RuntimeException("Такого Player не существует");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = IOUtils.toString(req.getReader());
        Player player = objectMapper.readValue(json, Player.class);
        Player playerFromDb = null;
        try {
            playerFromDb = playersService.readFromDB(player.getPlayerId());
            playersService.updateDB(player);
            PrintWriter printWriter = resp.getWriter();
            printWriter.println(objectWriter.writeValueAsString(player));
            printWriter.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("playerId"));
        try {
            playersService.deleteFromDB(id);
            PrintWriter printWriter = resp.getWriter();
            printWriter.println(objectWriter.writeValueAsString("Success"));
            printWriter.close();
        } catch (SQLException e) {
            PrintWriter printWriter = resp.getWriter();
            printWriter.println(objectWriter.writeValueAsString("NET PLAYERA"));
            printWriter.close();
            throw new RuntimeException("Такого player не существует");
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = IOUtils.toString(request.getReader());
        Player player = objectMapper.readValue(json, Player.class);
        System.out.println(player);
        try {
            playersService.writeToDB(player);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(objectWriter.writeValueAsString(player));
            printWriter.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
