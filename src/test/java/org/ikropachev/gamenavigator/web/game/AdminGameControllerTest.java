package org.ikropachev.gamenavigator.web.game;

import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.service.GameService;
import org.ikropachev.gamenavigator.util.exception.NotFoundException;
import org.ikropachev.gamenavigator.web.AbstractControllerTest;
import org.ikropachev.gamenavigator.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.ikropachev.gamenavigator.GameTestData.*;
import static org.ikropachev.gamenavigator.TestUtil.userHttpBasic;
import static org.ikropachev.gamenavigator.UserTestData.admin;
import static org.ikropachev.gamenavigator.model.AbstractBaseEntity.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminGameControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminGameController.REST_URL;

    @Autowired
    private GameService service;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/games")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(GAME_MATCHER.contentJson(game1, game2, game4, game3));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/games/" + GAME_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(GAME_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/games/" + NOT_FOUND)
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithLocation() throws Exception {
        Game newGame = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/games")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(newGame)));

        Game created = GAME_MATCHER.readFromJson(action);
        int newId = created.id();
        newGame.setId(newId);
        GAME_MATCHER.assertMatch(created, newGame);
        GAME_MATCHER.assertMatch(service.get(newId), newGame);
    }

    @Test
    void update() throws Exception {
        Game updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/games/" + GAME_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        GAME_MATCHER.assertMatch(service.get(GAME_ID), updated);
    }
}
