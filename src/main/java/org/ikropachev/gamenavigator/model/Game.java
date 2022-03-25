package org.ikropachev.gamenavigator.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "game")
public class Game extends AbstractNamedEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "developer", nullable = false)
    @ApiModelProperty(example = "developer")
    protected String developer;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "game_x_genre",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    public Game() {
    }

    public Game(Integer id, String name, String developer, List<Genre> genres) {
        super(id, name);
        this.developer = developer;
        this.genres = genres;
    }
    //Constructor for tests with ignoring fields
    public Game(Integer id, String name, String developer) {
        super(id, name);
        this.developer = developer;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
