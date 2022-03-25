package org.ikropachev.gamenavigator;

import org.ikropachev.gamenavigator.model.Genre;

import java.util.List;

import static org.ikropachev.gamenavigator.model.AbstractBaseEntity.START_SEQ;

public class GenreTestData {
    public static final MatcherFactory.Matcher<Genre> GENRE_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator(Genre.class, "games");

    public static final int GENRE_ID = START_SEQ + 4;

    public static final Genre genre1 = new Genre(GENRE_ID, "action");
    public static final Genre genre2 = new Genre(GENRE_ID + 1, "rts");
    public static final Genre genre3 = new Genre(GENRE_ID + 2, "adventure");
    public static final Genre genre4 = new Genre(GENRE_ID + 3, "horror");

    //Genres must be sorted by name
    public static final List<Genre> genres = List.of(genre1, genre3, genre4, genre2);

    public static Genre getNew() {
        return new Genre(null, "New_Genre");
    }

    public static Genre getUpdated() {
        return new Genre(GENRE_ID, "Updated_Genre");
    }
}
