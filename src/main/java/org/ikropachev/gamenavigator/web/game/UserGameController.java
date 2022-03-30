package org.ikropachev.gamenavigator.web.game;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.service.GameService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.ikropachev.gamenavigator.web.game.AbstractGameController.GAME_ID_STR;
import static org.ikropachev.gamenavigator.web.genre.AdminGenreController.GENRE_ID_STR;
import static org.slf4j.LoggerFactory.getLogger;

public class UserGameController extends AbstractGameController {
    private static final Logger log = getLogger(UserGameController.class);

    static final String REST_URL = "/rest/user/games";

    @Autowired
    private GameService service;

    @GetMapping("/{id}")
    @ApiOperation(value = "View a game by id")
    public Game get(@PathVariable @ApiParam(example = GAME_ID_STR, required = true) int id) {
        log.info("get game with id {}", id);
        return super.get(id);
    }

    @GetMapping
    @ApiOperation(value = "View a list of all games")
    public List<Game> getAll() {
        log.info("get all games");
        return super.getAll();
    }

    @GetMapping(value = "/by-genre-id")
    @ApiOperation(value = "View a list of all games by genre")
    public List<Game> getAllByGenreId(@Nullable @RequestParam(value = "genreId")
                                      @ApiParam(example = GENRE_ID_STR, required = false) Integer genreId) {
        log.info("get all games by genre {}", genreId);
        return super.getAllByGenreId(genreId);
    }
}
