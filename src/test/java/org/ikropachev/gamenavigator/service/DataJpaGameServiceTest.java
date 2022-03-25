package org.ikropachev.gamenavigator.service;

import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import static org.ikropachev.gamenavigator.GameTestData.*;
import static org.ikropachev.gamenavigator.GameTestData.getUpdated;
import static org.ikropachev.gamenavigator.model.AbstractBaseEntity.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataJpaGameServiceTest extends AbstractServiceTest {
    @Autowired
    protected GameService service;

    @Test
    public void create() {
        Game created = service.create(getNew());
        int newId = created.id();
        Game newGame = getNew();
        newGame.setId(newId);
        GAME_MATCHER.assertMatch(created, newGame);
        GAME_MATCHER.assertMatch(service.get(newId), newGame);
    }

    @Test
    void duplicateNameAndDeveloperCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Game(null, "half-life", "valve")));
    }

    @Test
    void delete() {
        service.delete(GAME_ID);
        assertThrows(NotFoundException.class, () -> service.get(GAME_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() {
        Game game = service.get(GAME_ID);
        GAME_MATCHER.assertMatch(game, game1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void update() {
        Game updated = getUpdated();
        service.update(updated);
        GAME_MATCHER.assertMatch(service.get(GAME_ID), getUpdated());
    }

    @Test
    void getAll() {
        GAME_MATCHER.assertMatch(service.getAll(), games);
    }
}
