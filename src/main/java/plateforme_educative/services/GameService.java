package plateforme_educative.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plateforme_educative.dto.GameDto;
import plateforme_educative.models.Game;
import plateforme_educative.models.GameStatus;
import plateforme_educative.models.GameType;
import plateforme_educative.repositorys.GameRepository;
import plateforme_educative.models.User;



@Service
@Transactional
public class GameService {
    private final GameRepository gameRepository;
    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    public Game getGame(Long id) {
        return gameRepository.findById(id).get();
    }
    public Game createNewGame(User player, GameDto gameDTO) {
        Game game = new Game();
        game.setFirstPlayer(player);
        game.setGameType(gameDTO.getGameType());
        game.setFirstPlayerPieceCode(gameDTO.getFirstPlayerPieceCode());
        System.out.println(gameDTO.getFirstPlayerPieceCode());
        game.setGameStatus(gameDTO.getGameType() == GameType.COMPUTER ? GameStatus.IN_PROGRESS :
                GameStatus.WAITS_FOR_PLAYER);

        game.setCreated(new Date());
        gameRepository.save(game);

        return game;
    }
    
    public Game updateGameStatus(Game game, GameStatus gameStatus) {
        Game g = getGame(game.getId());
        g.setGameStatus(gameStatus);

        return g;
    }
    
    public List<Game> getGamesToJoin(User player) {
        return gameRepository.findByGameTypeAndGameStatus(GameType.COMPETITION,
                GameStatus.WAITS_FOR_PLAYER).stream().filter(game -> game.getFirstPlayer() != player).collect(Collectors.toList());

    }
    
    public Game joinGame(User player, Long id) {
    	Game game = gameRepository.findById(id).get();
      //  Game game =  getGame((long) gameDTO.getId());
        game.setSecondPlayer(player);
        gameRepository.save(game);

        updateGameStatus(game, GameStatus.IN_PROGRESS);

        return game;

    }
    
    public List<Game> getPlayerGames(User player) {
        return gameRepository.findByGameStatus(
                GameStatus.IN_PROGRESS).stream().filter(game -> game.getFirstPlayer() == player).collect(Collectors.toList());
    }
}
