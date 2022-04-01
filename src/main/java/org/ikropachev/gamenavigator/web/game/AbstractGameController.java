package org.ikropachev.gamenavigator.web.game;

import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.service.GameService;
import org.ikropachev.gamenavigator.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AbstractGameController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    public static final String GAME_ID_STR = "100008";

    @Autowired
    protected GameService service;

    @Autowired
    protected GenreService genreService;

    public Game get(int id) {
        log.info("get game with id {}", id);
        return service.get(id);
    }

    public List<Game> getAll() {
        log.info("get all menus");
        return service.getAll();
    }

    public List<Game> getAllByGenreId(Integer genreId) {
        log.info("get all games by genre {}", genreId);
        return service.getAllByGenreId(genreId);
    }
}
