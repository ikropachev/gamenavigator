package org.ikropachev.gamenavigator.web.genre;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.ikropachev.gamenavigator.View;
import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.model.Genre;
import org.ikropachev.gamenavigator.service.GameService;
import org.ikropachev.gamenavigator.service.GenreService;
import org.ikropachev.gamenavigator.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.ikropachev.gamenavigator.web.game.AbstractGameController.GAME_ID_STR;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = AdminGenreController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Operations for genres from admin")
public class AdminGenreController {
    private static final Logger log = getLogger(AdminGenreController.class);

    static final String REST_URL = "/rest/admin/genres";
    public static final String GENRE_ID_STR = "100004";

    @Autowired
    private GenreService service;

    @Autowired
    private GameService gameService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a genre")
    public ResponseEntity<Genre> createWithLocation(@Validated(View.Web.class) @RequestBody Genre genre) {
        log.info("create genre {}", genre);
        Genre created = service.create(genre);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping("/games/{gameId}/genres")
    public ResponseEntity<Genre> addGenre(@PathVariable(value = "gameId")
                                          @ApiParam(example = GAME_ID_STR, required = true) Integer gameId,
                                          @RequestBody Genre genreRequest) {
        Integer genreId = genreRequest.getId();
        Game game = gameService.get(gameId);
        Genre genre = service.get(genreId);
        game.addGenre(genre);
        gameService.update(game);
        return new ResponseEntity<>(genre, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "View a genre by id")
    public Genre get(@PathVariable @ApiParam(example = GENRE_ID_STR, required = true) int id) {
        log.info("get genre with id {}", id);
        return service.get(id);
    }

    @GetMapping
    @ApiOperation(value = "View a list of all genres")
    public List<Genre> getAll() {
        log.info("get all genres");
        return service.getAll();
    }

    @GetMapping("/games/{gameId}/genres")
    public ResponseEntity<List<Genre>> getAllGenresByGameId(@PathVariable(value = "gameId")
                                                            @ApiParam(example = GAME_ID_STR, required = true)
                                                            Integer gameId) {
        if (gameService.get(gameId) == null) {
            throw new NotFoundException("Not found Game with id = " + gameId);
        }
        List<Genre> genres = service.getGenresByGameId(gameId);
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a genre by id")
    public void update(@Validated(View.Web.class) @RequestBody Genre genre,
                       @PathVariable @ApiParam(example = GENRE_ID_STR, required = true) int id) {
        log.info("update genre {} with id {}", genre, id);
        genre.setId(id);
        service.update(genre);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a genre by id")
    public void delete(@PathVariable @ApiParam(example = GENRE_ID_STR, required = true) int id) {
        log.info("delete genre with id {}", id);
        service.delete(id);
    }
}
