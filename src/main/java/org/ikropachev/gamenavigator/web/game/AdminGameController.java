package org.ikropachev.gamenavigator.web.game;

import com.opencsv.CSVReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.ikropachev.gamenavigator.View;
import org.ikropachev.gamenavigator.model.Game;
import org.ikropachev.gamenavigator.model.Genre;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.ikropachev.gamenavigator.web.genre.AdminGenreController.GENRE_ID_STR;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = AdminGameController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Operations for games from admin")
public class AdminGameController extends AbstractGameController {
    private static final Logger log = getLogger(AdminGameController.class);

    static final String REST_URL = "/rest/admin/games";
    public static final String GENRE_NAME = "action";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a game")
    public ResponseEntity<Game> createWithLocation(@Validated(View.Web.class) @RequestBody Game game) {
        log.info("create game {}", game);
        Game created = service.create(game);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "View a game by id")
    public Game get(@PathVariable @ApiParam(example = GAME_ID_STR, required = true) int id) {
        log.info("get game with id {}", id);
        return super.get(id);
    }

    @GetMapping
    @ApiOperation(value = "View a list of all games")
    public List<Game> getAll() {
        log.info("get all games");
        return super.getAll();
    }

    //test controller
    @GetMapping(value = "/by-genre-id")
    @ApiOperation(value = "View a list of all games by genre")
    public List<Game> getAllByGenreId(@Nullable @RequestParam(value = "genreId")
                                      @ApiParam(example = GENRE_ID_STR, required = false) Integer genreId) {
        log.info("get all games by genre {}", genreId);
        return super.getAllByGenreId(genreId);
    }

    @GetMapping(value = "/by-genre-name")
    @ApiOperation(value = "View a list of all games by name of genre")
    public List<Game> getAllByGenreName(@Nullable @RequestParam(value = "genre-name")
                                        @ApiParam(example = GENRE_NAME, required = false) String genreName) {
        log.info("get all games by name of genre {}", genreName);
        Genre genre = genreService.get(genreName);
        return super.getAllByGenreId(genre.getId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a game by id")
    public void update(@Validated(View.Web.class) @RequestBody Game game,
                       @PathVariable @ApiParam(example = GAME_ID_STR, required = true) int id) {
        log.info("update game {} with id {}", game, id);
        game.setId(id);
        service.update(game);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a game by id")
    public void delete(@PathVariable @ApiParam(example = GAME_ID_STR, required = true) int id) {
        log.info("delete game with id {}", id);
        service.delete(id);
    }

    @PostMapping("/upload-csv-file")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            List<List<String>> records = new ArrayList<List<String>>();
            try (CSVReader csvReader = new CSVReader(new FileReader(convFile));) {
                String[] values = null;
                while ((values = csvReader.readNext()) != null) {
                    records.add(Arrays.asList(values));
                }
                int a;
                for (a = 0; a < records.size(); ++a) {
                    Game game = new Game();
                    game.setName(records.get(a).get(0));
                    game.setDeveloper(records.get(a).get(1));
                    service.create(game);
                }

            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }

        return "file-upload-status";
    }
}
