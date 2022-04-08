package org.ikropachev.gamenavigator.service;

import org.ikropachev.gamenavigator.model.Genre;
import org.ikropachev.gamenavigator.repository.DataJpaGenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.ikropachev.gamenavigator.util.ValidationUtil.checkNew;
import static org.ikropachev.gamenavigator.util.ValidationUtil.checkNotFoundWithId;

@Service
public class GenreService {
    private final DataJpaGenreRepository repository;

    public GenreService(DataJpaGenreRepository repository) {
        this.repository = repository;
    }

    public Genre create(Genre genre) {
        checkNew(genre);
        return repository.save(genre);
    }

    public Genre get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public Genre get(String name) {
        return repository.get(name);
    }

    public List<Genre> getGenresByGameId(Integer gameId) { return repository.getGenresByGameId(gameId);}

    public List<Genre> getAll() {
        return repository.getAll();
    }

    public void update(Genre genre) {
        checkNotFoundWithId(repository.save(genre), genre.getId());
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
