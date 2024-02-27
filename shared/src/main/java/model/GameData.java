package model;

import chess.ChessGame;

import java.util.Objects;

class GameData {
    private final String whiteUsername;
    private final String blackUsername;
    private final String gameName;
    private final int gameID;
    private final ChessGame game;

    GameData(String whiteUsername, String blackUsername, String gameName, int gameID, ChessGame game) {
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.gameID = gameID;
        this.game = game;
    }

    public int getGameID() {
        return gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public ChessGame getGame() {
        return game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameData gameData = (GameData) o;
        return gameID == gameData.gameID && Objects.equals(whiteUsername, gameData.whiteUsername) && Objects.equals(blackUsername, gameData.blackUsername) && Objects.equals(gameName, gameData.gameName) && Objects.equals(game, gameData.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(whiteUsername, blackUsername, gameName, gameID, game);
    }

    @Override
    public String toString() {
        return "GameData{" +
                "whiteUsername=" + whiteUsername +
                ", blackUsername=" + blackUsername +
                ", gameName=" + gameName +
                ", gameID=" + gameID +
                '}';
    }
}
