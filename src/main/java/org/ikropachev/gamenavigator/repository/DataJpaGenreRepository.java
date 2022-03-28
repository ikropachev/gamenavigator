package org.ikropachev.gamenavigator.repository;

import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.model.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaGenreRepository {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final CrudGenreRepository crudGenreRepository;

    public DataJpaGenreRepository(CrudGenreRepository crudGenreRepository) {
        this.crudGenreRepository = crudGenreRepository;
    }

    public Genre save(Genre genre) {
        return crudGenreRepository.save(genre);
    }

    public boolean delete(int id) {
        return crudGenreRepository.delete(id) != 0;
    }

    public Genre get(int id) {
        return crudGenreRepository.findById(id).orElse(null);
    }

    public List<Genre> getAll() {
        return crudGenreRepository.findAll();
    }
}
