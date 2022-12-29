package services;

import ORM.ProgressesORM;
import models.Progresses;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProgressesService {


    private final ProgressesORM ormProgresses;


    public ProgressesService(ProgressesORM ormProgresses) {
        this.ormProgresses = ormProgresses;
    }

    public List<Progresses> readAllWithPlId(long plId) throws SQLException {
        return ormProgresses.readByPlayerId(plId);
    }


    public void write(List<Progresses> progresses) throws SQLException {
        for(Progresses progress : progresses)
            write(progress);
    }

    public void write(Progresses p) throws SQLException {
        ormProgresses.create(p);
    }

    public Progresses readFromDB(long id) throws SQLException {
        return ormProgresses.read(id);
    }

    public void updateDB(Progresses p) throws SQLException {
        ormProgresses.update(p);
    }

    public void deleteFromDB(long id) throws SQLException {
        ormProgresses.delete(id);
    }


    public static JSONObject progressToJson(Progresses progress)
    {
        JSONObject jsonProgress = new JSONObject();

        jsonProgress.put("id", progress.getId());
        jsonProgress.put("playerId", progress.getPlayerId());
        jsonProgress.put("resourceId", progress.getResourceId());
        jsonProgress.put("score", progress.getScore());
        jsonProgress.put("maxScore", progress.getMaxScore());

        return jsonProgress;
    }


    public static JSONArray progressesToJson(List<Progresses> progresses)
    {
        JSONArray jsonProgressArr = new JSONArray();
        for (Progresses progress : progresses)
            jsonProgressArr.add(progressToJson(progress));

        return jsonProgressArr;
    }

}
