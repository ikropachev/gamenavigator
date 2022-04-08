package org.ikropachev.gamenavigator.repository;

import org.ikropachev.gamenavigator.model.Game;
import org.springframework.data.jpa.repository.EntityGraph;
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
    //@EntityGraph(attributePaths = {"genres"}, type = EntityGraph.EntityGraphType.LOAD)
    Game save(Game game);

    @EntityGraph(attributePaths = {"genres"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT g FROM Game g ORDER BY g.name")
    List<Game> findAll();

    //https://www.codejava.net/frameworks/spring/jpa-join-query-for-like-search-examples
    @Query("SELECT g FROM Game g JOIN g.genres ge WHERE ge.id =:genreId")
    List<Game> findAllByGenreId(@Param("genreId") Integer genreId);

    //@Query("select g from Game g where g.genres in ?1")
    //List<Game> findAllByGenre(Genre genre);

    @Modifying
    @Transactional
    @Query("DELETE FROM Game g WHERE g.id=:id")
    int delete(@Param("id") int id);
}
