import ORM.CRUDCurrenciesORM;
import ORM.CRUDItemsORM;
import ORM.CRUDPlayerORM;
import ORM.CRUDProgressesORM;
import models.Player;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.*;
import servlets.ItemsServlet;
import servlets.PlayersServlet;
import servlets.ProgressesServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static services.DBService.*;

public class Main {
    public static void main(String[] args) throws Exception {
        runServer(false);
    }

    public static void runServer(boolean readFromJson) throws Exception {
        if (readFromJson){
            List<Player> players = DBService.jsonStringToPojo(DBService.jsonPath);
            DBService.saveToDB(players);
        }
        final ProgressesService progressesService = new ProgressesService(new CRUDProgressesORM(DBService.getDBConnection()));
        final ItemsService itemsService = new ItemsService(new CRUDItemsORM(DBService.getDBConnection()));
        final CurrenciesService currenciesService = new CurrenciesService(new CRUDCurrenciesORM(DBService.getDBConnection()));
        final PlayersService playersService = new PlayersService(new CRUDPlayerORM(DBService.getDBConnection()),
                currenciesService,itemsService,progressesService);

        final Server server = new Server();
        final int port = 8080;
        final HttpConfiguration httpConfig = new HttpConfiguration();
        final HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);
        final ServerConnector serverConnector = new ServerConnector(server, httpConnectionFactory);
        serverConnector.setHost("localhost");
        serverConnector.setPort(port);
        server.setConnectors(new Connector[]{serverConnector});
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");

        context.addServlet(new ServletHolder(new PlayersServlet(playersService)), "/players/*");
     //   context.addServlet(new ServletHolder(new ItemsServlet(itemsService)), "/items/*");
        context.addServlet(new ServletHolder(new ProgressesServlet(progressesService)), "/progresses/*");
     //   context.addServlet(new ServletHolder( new CurrencyServlet(dbService)), "/currencies/*");

        server.setHandler(context);
        server.start();
    }
}
