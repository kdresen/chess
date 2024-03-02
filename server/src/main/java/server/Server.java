package server;

import com.google.gson.Gson;
import model.requests.CreateGameRequest;
import model.requests.JoinGameRequest;
import model.results.*;
import spark.*;
import dataAccess.*;
import model.*;
import service.*;

import java.util.Map;

public class Server {
    private final ClearApplicationService clearApplicationService;
    private final UserService userService;
    private final GameService gameService;

    public Server() {
        UserDAO userDAO = UserDAOManager.getUserDAO();
        GameDAO gameDAO = GameDAOManager.getGameDAO();
        AuthDAO authDAO = AuthDAOManager.getAuthDAO();
        clearApplicationService = new ClearApplicationService(userDAO, authDAO, gameDAO);
        userService = new UserService(authDAO, userDAO);
        gameService = new GameService(authDAO, gameDAO);
    }
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clearApplication);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.put("/game", this::joinGame);
        Spark.post("/game", this::createGame);
        Spark.exception(DataAccessException.class, this::exceptionHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    private Object exceptionHandler(DataAccessException ex, Request req, Response res) {
       switch (ex.getMessage()) {
           case "Error: bad request":
               res.status(400);
               break;
           case "Error: unauthorized":
               res.status(401);
               break;
           case "Error: already taken":
               res.status(403);
               break;
           default:
               res.status(500);
       }
       res.body(new Gson().toJson(Map.of("message", ex.getMessage())));
       return res.body();
    }
    private Object clearApplication(Request req, Response res) throws DataAccessException {
        clearApplicationService.deleteAll();
        res.status(200); // HTTP 200 OK
        return "{}";
    }
    private Object register(Request req, Response res) throws DataAccessException {
        UserData userData = new Gson().fromJson(req.body(), UserData.class);
        AuthData resultAuthData = userService.registerUser(userData);
        res.status(200);
        return new Gson().toJson(resultAuthData);
    }
    private Object login(Request req, Response res) throws DataAccessException {
        UserData userData = new Gson().fromJson(req.body(), UserData.class);
        AuthData resultAuthData = userService.loginUser(userData);
        res.status(200);
        return new Gson().toJson(resultAuthData);
    }
    private Object logout(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        userService.logoutUser(authToken);
        res.status(200);
        return "{}";
    }
    private Object listGames(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        ListGamesResult result = gameService.listGames(authToken);
        res.status(200);
        return new Gson().toJson(result);
    }
    private Object createGame(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        CreateGameRequest temp = new Gson().fromJson(req.body(), CreateGameRequest.class);
        String gameName = temp.gameName();
        CreateGameRequest gameRequest = new CreateGameRequest(authToken, gameName);
        CreateGameResult gameResult = gameService.createGame(gameRequest);
        res.status(200);
        return new Gson().toJson(gameResult);
    }
    private Object joinGame(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        JoinGameRequest temp = new Gson().fromJson(req.body(), JoinGameRequest.class);
        String playerColor = temp.playerColor();
        int gameID = temp.gameID();
        JoinGameRequest gameRequest = new JoinGameRequest(authToken, playerColor, gameID);
        gameService.joinGame(gameRequest);
        res.status(200);
        return "{}";
    }

 }
