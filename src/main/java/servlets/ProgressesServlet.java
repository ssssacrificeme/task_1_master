package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Progresses;
import org.apache.commons.io.IOUtils;
import services.DBService;
import services.ProgressesService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class ProgressesServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    private final ProgressesService progressesService;

    public ProgressesServlet(ProgressesService progressesService) {
        this.progressesService = progressesService;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Progresses progresses = progressesService.readFromDB(id);
            if (progresses == null){
                response.sendError(404,"Такого progresses нет");
            }
            else {
                PrintWriter printWriter = response.getWriter();
                printWriter.println(progresses.toString());
                printWriter.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = IOUtils.toString(req.getReader());
        Progresses progress = objectMapper.readValue(json,Progresses.class);
        System.out.println(progress);
        try {
            progressesService.updateDB(progress);
            PrintWriter printWriter = resp.getWriter();
            printWriter.println(objectWriter.writeValueAsString(progress));
            printWriter.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            progressesService.deleteFromDB(id);
            PrintWriter printWriter = resp.getWriter();
            printWriter.println("Success");
            printWriter.close();
        } catch (SQLException e) {
            PrintWriter printWriter = resp.getWriter();
            printWriter.println("Удалено неуспешно");
            printWriter.close();
            throw new RuntimeException("Не существует такого Progresses " + id);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = IOUtils.toString(request.getReader());
        System.out.println(json);
        Progresses progress = objectMapper.readValue(json, Progresses.class);
        try {
            progressesService.write(progress);
        } catch (SQLException e) {
            throw new RuntimeException("This progresses exist");
        }
        PrintWriter printWriter = response.getWriter();
        printWriter.println(objectWriter.writeValueAsString("Success"));
        printWriter.close();
    }

}
