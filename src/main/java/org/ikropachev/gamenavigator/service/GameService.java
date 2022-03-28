package org.ikropachev.gamenavigator.service;

import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.repository.DataJpaGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.ikropachev.gamenavigator.util.ValidationUtil.checkNew;
import static org.ikropachev.gamenavigator.util.ValidationUtil.checkNotFoundWithId;

@Service
public class GameService {
    private final DataJpaGameRepository repository;

    public GameService(DataJpaGameRepository repository) {
        this.repository = repository;
    }

    public Game create(Game game) {
        checkNew(game);
        return repository.save(game);
    }

    public void update(Game game) {
        checkNotFoundWithId(repository.save(game), game.getId());
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Game get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Game> getAll() {
        return repository.getAll();
    }
}
