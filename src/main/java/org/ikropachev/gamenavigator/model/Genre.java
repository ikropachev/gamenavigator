package org.ikropachev.gamenavigator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genre")
public class Genre extends AbstractNamedEntity {

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "genre")
    @JsonIgnore
    private List<Game> games;

    public Genre() {
    }

    public Genre(Integer id, String name, List<Game> games) {
        super(id, name);
        this.games = games;
    }

    //Constructor for tests with ignoring fields
    public Genre(Integer id, String name) {
        super(id, name);
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
