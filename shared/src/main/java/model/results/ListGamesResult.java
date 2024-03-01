package model.results;

import model.GameData;

import java.util.Collection;

public record ListGamesResult(int statusCode, Collection<GameData> games) {
}
