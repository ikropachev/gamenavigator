package org.ikropachev.gamenavigator.web.game;

import org.ikropachev.gamenavigator.service.GameService;
import org.ikropachev.gamenavigator.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.ikropachev.gamenavigator.GameTestData.*;
import static org.ikropachev.gamenavigator.TestUtil.userHttpBasic;
import static org.ikropachev.gamenavigator.UserTestData.admin;
import static org.ikropachev.gamenavigator.UserTestData.user1;
import static org.ikropachev.gamenavigator.web.game.AdminGameController.GENRE_NAME;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserGameControllerTest extends AbstractControllerTest {
    private static final String REST_URL = UserGameController.REST_URL + "/";

    //@Autowired
    //private GameService service;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(GAME_MATCHER.contentJson(game1, game2, game4, game3));
    }

    @Test
    void getAllByGenre() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by-genre?genre=" + GENRE_NAME)
                .with(userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(GAME_MATCHER.contentJson(game1, game2));
    }
}
