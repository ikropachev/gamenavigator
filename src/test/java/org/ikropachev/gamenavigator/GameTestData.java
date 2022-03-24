package org.ikropachev.gamenavigator;

import org.ikropachev.gamenavigator.model.Game;

import java.util.List;

import static org.ikropachev.gamenavigator.model.AbstractBaseEntity.START_SEQ;

public class GameTestData {
    public static final MatcherFactory.Matcher<Game> GAME_MATCHER = MatcherFactory.usingEqualsComparator(Game.class);

    public static final int GAME_ID = START_SEQ + 8;

    public static final Game game1 = new Game(GAME_ID, "half-life");
    public static final Game game2 = new Game(GAME_ID + 1, "silent hill");
    public static final Game game3 = new Game(GAME_ID + 2, "warcraft");
    public static final Game game4 = new Game(GAME_ID + 3, "starcraft");

    //Games must be sorted by name
    public static final List<Game> games = List.of(game1, game2, game4, game3);

    public static Game getNew() {
        return new Game(null, "New_Game");
    }

    public static Game getUpdated() {
        return new Game(GAME_ID, "Updated_Game");
    }
}
