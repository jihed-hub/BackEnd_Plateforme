package plateforme_educative.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plateforme_educative.dto.CreateMoveDto;
import plateforme_educative.dto.MoveDto;
import plateforme_educative.models.Game;
import plateforme_educative.models.GameStatus;
import plateforme_educative.models.GameType;
import plateforme_educative.models.Move;
import plateforme_educative.models.Piece;
import plateforme_educative.models.Position;
import plateforme_educative.models.User;
import plateforme_educative.repositorys.MoveRepository;



@Service
@Transactional
public class MoveService {
	  private final MoveRepository moveRepository;


	    @Autowired
	    public MoveService(MoveRepository moveRepository) {
	        this.moveRepository = moveRepository;
	    }
	    
	    public Move createMove(Game game, User player, CreateMoveDto createMoveDTO) {
	        Move move = new Move();
	        move.setBoardColumn(createMoveDTO.getBoardColumn());
	        move.setBoardRow(createMoveDTO.getBoardRow());
	        move.setCreated(new Date());
	        move.setPlayer(player);
	        move.setGame(game);

	        moveRepository.save(move);

	        return move;
	    }

	    public Move autoCreateMove(Game game) {
	        Move move = new Move();
	        move.setBoardColumn(GameLogic.nextAutoMove(getTakenMovePositionsInGame(game)).getBoardColumn());
	        move.setBoardRow(GameLogic.nextAutoMove(getTakenMovePositionsInGame(game)).getBoardRow());
	        move.setCreated(new Date());
	        move.setPlayer(null);
	        move.setGame(game);

	        moveRepository.save(move);

	        return move;
	    }
	    

	    public GameStatus checkCurrentGameStatus(Game game) {
	        if (GameLogic.isWinner(getPlayerMovePositionsInGame(game, game.getFirstPlayer()))) {
	            return GameStatus.FIRST_PLAYER_WON;
	        } else if (GameLogic.isWinner(getPlayerMovePositionsInGame(game, game.getSecondPlayer()))) {
	            return GameStatus.SECOND_PLAYER_WON;
	        } else if (GameLogic.isBoardIsFull(getTakenMovePositionsInGame(game))) {
	            return GameStatus.TIE;
	        } else if (game.getGameType() == GameType.COMPETITION && game.getSecondPlayer() == null ) {
	            return GameStatus.WAITS_FOR_PLAYER;
	        } else {
	            return GameStatus.IN_PROGRESS;
	        }

	    }


	    public List<MoveDto> getMovesInGame(Game game) {

	        List<Move> movesInGame = moveRepository.findByGame(game);
	        List<MoveDto> moves = new ArrayList<>();
	        Piece currentPiece = game.getFirstPlayerPieceCode();

	        for(Move move :  movesInGame) {
	        	MoveDto moveDTO = new MoveDto();
	            moveDTO.setBoardColumn(move.getBoardColumn());
	            moveDTO.setBoardRow(move.getBoardRow());
	            moveDTO.setCreated(move.getCreated());
	            moveDTO.setGameStatus(move.getGame().getGameStatus());
	            moveDTO.setUserName(move.getPlayer() == null ? GameType.COMPUTER.toString() : move.getPlayer().getUsername() );
	            moveDTO.setPlayerPieceCode(currentPiece);
	            moves.add(moveDTO);

	            currentPiece = currentPiece == Piece.X ? Piece.O : Piece.X;
	        }

	        return moves;
	    }

	    public List<Position> getTakenMovePositionsInGame(Game game) {
	        return moveRepository.findByGame(game).stream()
	                .map(move -> new Position(move.getBoardRow(), move.getBoardColumn()))
	                .collect(Collectors.toList());
	    }

	    public List<Position> getPlayerMovePositionsInGame(Game game, User player) {

	        return moveRepository.findByGameAndPlayer(game, player).stream()
	                .map(move -> new Position(move.getBoardRow(), move.getBoardColumn()))
	                .collect(Collectors.toList());
	    }

	    public int getTheNumberOfPlayerMovesInGame(Game game, User player) {
	        return moveRepository.countByGameAndPlayer(game, player);
	    }

	    public boolean isPlayerTurn(Game game, User firstPlayer, User secondPlayer) {
	        return GameLogic.playerTurn(getTheNumberOfPlayerMovesInGame(game, firstPlayer),
	                getTheNumberOfPlayerMovesInGame(game, secondPlayer));
	    }
	    
}
