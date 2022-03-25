package org.ikropachev.gamenavigator.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "genre")
public class Genre extends AbstractNamedEntity {

    public Genre() {
    }

    public Genre(Integer id, String name) {
        super(id, name);
    }
}
