package org.ikropachev.gamenavigator.repository;

import org.ikropachev.gamenavigator.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudGameRepository extends JpaRepository<Game, Integer> {
    @Override
    @Transactional
    Game save(Game game);

    @Query("SELECT g FROM Game g ORDER BY g.name")
    List<Game> findAll();

    List<Game> findGamesByGenreId(Integer genreId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Game g WHERE g.id=:id")
    int delete(@Param("id") int id);
}
