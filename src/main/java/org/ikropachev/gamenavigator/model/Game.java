package org.ikropachev.gamenavigator.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "game")
public class Game extends AbstractNamedEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "developer", nullable = false)
    @ApiModelProperty(example = "developer")
    protected String developer;

    public Game() {
    }

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
}
