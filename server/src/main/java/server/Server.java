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
        clearApplicationService = new ClearApplicationService(userDAO, authDAO, gameDAO);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
