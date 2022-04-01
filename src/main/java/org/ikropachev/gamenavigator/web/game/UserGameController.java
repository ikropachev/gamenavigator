package org.ikropachev.gamenavigator.web.game;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.model.Genre;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.ikropachev.gamenavigator.web.game.AdminGameController.GENRE_NAME;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = UserGameController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Operations for games from regular user")
public class UserGameController extends AbstractGameController {
    private static final Logger log = getLogger(UserGameController.class);

    static final String REST_URL = "/rest/user/games";

    @GetMapping
    @ApiOperation(value = "View a list of all games")
    public List<Game> getAll() {
        log.info("get all games");
        return super.getAll();
    }

    @GetMapping(value = "/by-genre-name")
    @ApiOperation(value = "View a list of all games by name of genre")
    public List<Game> getAllByGenreName(@Nullable @RequestParam(value = "genre-name")
                                    @ApiParam(example = GENRE_NAME, required = false) String genreName) {
        log.info("get all games by name of genre {}", genreName);
        Genre genre = genreService.get(genreName);
        return super.getAllByGenreId(genre.getId());
    }
}
