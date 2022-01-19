package plateforme_educative.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import plateforme_educative.dto.GameDto;
import plateforme_educative.models.Game;
import plateforme_educative.models.Move;
import plateforme_educative.services.GameService;
import plateforme_educative.services.PlayerService;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {
	 @Autowired
	    GameService gameService;

	    @Autowired
	    PlayerService playerService;

	    @Autowired
	    HttpSession httpSession;
	    
	    Logger logger = LoggerFactory.getLogger(GameController.class);
	    
	    @RequestMapping(value = "/create", method = RequestMethod.POST)
	    public Game createNewGame(@RequestBody GameDto gameDTO) {

	        Game game = gameService.createNewGame(playerService.getCurrentUser(), gameDTO);
	        httpSession.setAttribute("gameId", game.getId());

	        logger.info("new game id: " + httpSession.getAttribute("gameId")+ " stored in session" );

	        return game;
	    }
	    
	    @RequestMapping(value = "/list",  method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<Game> getGamesToJoin() {
	        return gameService.getGamesToJoin(playerService.getCurrentUser());
	    }
	    
	    @PostMapping("/join/{id}")
	    public Game joinGame(@RequestBody GameDto gameDTO,@PathVariable(value="id") Long id) {
	        Game game = gameService.joinGame(playerService.getCurrentUser(), id);
	        return game;
	    }
	    
	    @RequestMapping(value = "/player/list", produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<Game> getPlayerGames() {
	        return gameService.getPlayerGames(playerService.getCurrentUser());
	    }
	    
	    @RequestMapping(value = "/{id}")
	    public Game getGameProperties(@PathVariable Long id) {

	        httpSession.setAttribute("gameId", id);

	        return gameService.getGame(id);
	    }
	    
}
