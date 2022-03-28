package org.ikropachev.gamenavigator.repository;

import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudGenreRepository extends JpaRepository<Genre, Integer> {
    @Override
    @Transactional
    Genre save(Genre genre);

    @Query("SELECT g FROM Genre g ORDER BY g.name")
    List<Genre> findAll();

    //List<Game> findGenresByGameId(Integer gameId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Genre g WHERE g.id=:id")
    int delete(@Param("id") int id);
}
