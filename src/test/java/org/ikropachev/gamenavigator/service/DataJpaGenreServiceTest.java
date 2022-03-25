package org.ikropachev.gamenavigator.service;

import org.ikropachev.gamenavigator.model.Genre;
import org.ikropachev.gamenavigator.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import static org.ikropachev.gamenavigator.GenreTestData.*;
import static org.ikropachev.gamenavigator.model.AbstractBaseEntity.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataJpaGenreServiceTest extends AbstractServiceTest {

    @Autowired
    protected GenreService service;

    @Test
    public void create() {
        Genre created = service.create(getNew());
        int newId = created.id();
        Genre newGenre = getNew();
        newGenre.setId(newId);
        GENRE_MATCHER.assertMatch(created, newGenre);
        GENRE_MATCHER.assertMatch(service.get(newId), newGenre);
    }

    @Test
    void duplicateNameCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Genre(null, "action")));
    }

    @Test
    void delete() {
        service.delete(GENRE_ID);
        assertThrows(NotFoundException.class, () -> service.get(GENRE_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() {
        Genre genre = service.get(GENRE_ID);
        GENRE_MATCHER.assertMatch(genre, genre1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void update() {
        Genre updated = getUpdated();
        service.update(updated);
        GENRE_MATCHER.assertMatch(service.get(GENRE_ID), getUpdated());
    }

    @Test
    void getAll() {
        GENRE_MATCHER.assertMatch(service.getAll(), genres);
    }
}
