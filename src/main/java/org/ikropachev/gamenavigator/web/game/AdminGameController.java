package org.ikropachev.gamenavigator.web.game;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.ikropachev.gamenavigator.View;
import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.model.Genre;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.ikropachev.gamenavigator.web.genre.AdminGenreController.GENRE_ID_STR;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = AdminGameController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Operations for games from admin")
public class AdminGameController extends AbstractGameController {
    private static final Logger log = getLogger(AdminGameController.class);

    static final String REST_URL = "/rest/admin/games";
    public static final String GENRE_NAME = "action";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a game")
    public ResponseEntity<Game> createWithLocation(@Validated(View.Web.class) @RequestBody Game game) {
        log.info("create game {}", game);
        Game created = service.create(game);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

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

    //test controller
    @GetMapping(value = "/by-genre-id")
    @ApiOperation(value = "View a list of all games by genre")
    public List<Game> getAllByGenreId(@Nullable @RequestParam(value = "genreId")
                                   @ApiParam(example = GENRE_ID_STR, required = false) Integer genreId) {
        log.info("get all games by genre {}", genreId);
        return super.getAllByGenreId(genreId);
    }

    @GetMapping(value = "/by-genre")
    @ApiOperation(value = "View a list of all games by genre")
    public List<Game> getAllByGenre(@Nullable @RequestParam(value = "genre")
                                      @ApiParam(example = GENRE_NAME, required = false) String genreName) {
        log.info("get all games by genre {}", genreName);
        Genre genre = genreService.get(genreName);
        return super.getAllByGenreId(genre.getId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a game by id")
    public void update(@Validated(View.Web.class) @RequestBody Game game,
                       @PathVariable @ApiParam(example = GAME_ID_STR, required = true) int id) {
        log.info("update game {} with id {}", game, id);
        game.setId(id);
        service.update(game);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a game by id")
    public void delete(@PathVariable @ApiParam(example = GAME_ID_STR, required = true) int id) {
        log.info("delete game with id {}", id);
        service.delete(id);
    }
}
