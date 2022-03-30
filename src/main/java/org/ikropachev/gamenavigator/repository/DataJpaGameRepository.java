package org.ikropachev.gamenavigator.repository;

import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.model.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaGameRepository {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final CrudGameRepository crudGameRepository;

    public DataJpaGameRepository(CrudGameRepository crudGameRepository) {
        this.crudGameRepository = crudGameRepository;
    }

    public Game save(Game game) {
        return crudGameRepository.save(game);
    }

    public Game get(int id) {
        return crudGameRepository.findById(id).orElse(null);
    }

    public List<Game> getAll() {
        return crudGameRepository.findAll();
    }

    public List<Game> getAllByGenreId(Integer genreId) {
        return crudGameRepository.findAllByGenreId(genreId);
    }

    public boolean delete(int id) {
        return crudGameRepository.delete(id) != 0;
    }

    /*
    public List<Game> getAllByGenre(Genre genre) {
        return crudGameRepository.findAllByGenre(genre);
    }
     */
}
