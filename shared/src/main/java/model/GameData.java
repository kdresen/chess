package model;

import chess.ChessGame;

import java.util.Objects;

public record GameData(String whiteUsername, String blackUsername, String gameName, int gameID, ChessGame game) {

    // Methods for changes in the GameData object
    public GameData changeWhiteUsername(String newName) {
        return new GameData(newName, blackUsername, gameName, gameID, game);
    }
    public GameData changeBlackUsername(String newName) {
        return new GameData(whiteUsername, newName, gameName, gameID, game);
    }
    public GameData changeGameName(String newName) {
        return new GameData(whiteUsername, blackUsername, newName, gameID, game);
    }
    public GameData changeGameID(int newID) {
        return new GameData(whiteUsername, blackUsername, gameName, newID, game);
    }
    public GameData newMove(ChessGame newMove) {
        return new GameData(whiteUsername, blackUsername, gameName, gameID, newMove);
    }

    public boolean isWhiteUsernameNull() {
        return Objects.equals(whiteUsername, null);
    }
    public boolean isBlackUsernameNull() {
        return Objects.equals(blackUsername, null);
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
