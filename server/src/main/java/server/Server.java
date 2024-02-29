package server;

import com.google.gson.Gson;
import spark.*;
import dataAccess.*;
import model.*;
import service.*;

import java.util.Map;

public class Server {
    private final ClearApplicationService clearApplicationService;


    public Server() {
        clearApplicationService = new ClearApplicationService(UserDAOManager.getUserDAO(), AuthDAOManager.getAuthDAO(), GameDAOManager.getGameDAO());
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clearApplication);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    private Object clearApplication(Request req, Response res) {
        clearApplicationService.deleteAll();
        res.status(200); // HTTP 200 OK
        return "{}";
    }
}
