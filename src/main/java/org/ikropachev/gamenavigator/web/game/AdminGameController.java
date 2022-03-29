package org.ikropachev.gamenavigator.web.game;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.model.Genre;
import org.ikropachev.gamenavigator.service.GameService;
import org.ikropachev.gamenavigator.service.GenreService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

import static org.ikropachev.gamenavigator.web.genre.AdminGenreController.GENRE_ID_STR;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = AdminGameController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Operations for games from admin")
public class AdminGameController extends AbstractGameController {
    private static final Logger log = getLogger(AdminGameController.class);

    static final String REST_URL = "/rest/admin/games";
    public static final String GAME_ID_STR = "100008";

    @Autowired
    private GameService service;

    @Autowired
    private GenreService genreService;

    @GetMapping("/{id}")
    @ApiOperation(value = "View a game by id")
    public Game get(@PathVariable @ApiParam(example = GAME_ID_STR, required = true) int id) {
        log.info("get game with id {}", id);
        return service.get(id);
    }

    @Override
    @GetMapping(value = "/by-genre-id")
    @ApiOperation(value = "View a list of all games by genre")
    public List<Game> getAllByGenreId(@Nullable @RequestParam(value = "genreId")
                                   @ApiParam(example = GENRE_ID_STR, required = false) Integer genreId) {
        log.info("get all games by genre {}", genreId);
        return super.getAllByGenreId(genreId);
    }

    @GetMapping
    @ApiOperation(value = "View a list of all games")
    public List<Game> getAll() {
        log.info("get all games");
        return service.getAll();
    }
}
