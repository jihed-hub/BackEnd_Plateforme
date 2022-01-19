package plateforme_educative.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import plateforme_educative.dto.CreateMoveDto;
import plateforme_educative.dto.MoveDto;
import plateforme_educative.models.Game;
import plateforme_educative.models.Move;
import plateforme_educative.models.Position;
import plateforme_educative.services.GameService;
import plateforme_educative.services.MoveService;
import plateforme_educative.services.PlayerService;

@RestController
@RequestMapping("/api/move")
public class MoveController {

    @Autowired
    private MoveService moveService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @Autowired
    private HttpSession httpSession;
    
    Logger logger = LoggerFactory.getLogger(MoveController.class);


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Game createMove(@RequestBody CreateMoveDto createMoveDTO) {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        logger.info("move to insert:" + createMoveDTO.getBoardColumn() + createMoveDTO.getBoardRow());

        Game game = gameService.getGame(gameId);
        Move move = moveService.createMove(gameService.getGame(gameId), playerService.getCurrentUser(), createMoveDTO);
        gameService.updateGameStatus(gameService.getGame(gameId), moveService.checkCurrentGameStatus(game));

        return game;
    }
    
    @RequestMapping(value = "/autocreate", method = RequestMethod.GET)
    public Move autoCreateMove() {
        Long gameId = (Long) httpSession.getAttribute("gameId");

        logger.info("AUTO move to insert:" );

        Move move = moveService.autoCreateMove(gameService.getGame(gameId));

        Game game = gameService.getGame(gameId);
        gameService.updateGameStatus(gameService.getGame(gameId), moveService.checkCurrentGameStatus(game));

        return move;
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<MoveDto> getMovesInGame() {

        Long gameId = (Long) httpSession.getAttribute("gameId");

      return moveService.getMovesInGame(gameService.getGame(gameId));
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public List<Position> validateMoves() {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        return moveService.getPlayerMovePositionsInGame(gameService.getGame(gameId), playerService.getCurrentUser());
    }

    @RequestMapping(value = "/turn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isPlayerTurn() {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        return moveService.isPlayerTurn(gameService.getGame(gameId), gameService.getGame(gameId).getFirstPlayer(),
                gameService.getGame(gameId).getSecondPlayer());
    }
    
    @RequestMapping(value = "/aaa", method = RequestMethod.GET)
    public List<Position> get(){
        Long gameId = (Long) httpSession.getAttribute("gameId");
        return moveService.getTakenMovePositionsInGame(gameService.getGame(gameId));
    }
    @RequestMapping(value = "/bbb", method = RequestMethod.GET)
    public List<MoveDto> gett(){
        Long gameId = (Long) httpSession.getAttribute("gameId");
        return moveService.getMovesInGame(gameService.getGame(gameId));
    }

   
}
