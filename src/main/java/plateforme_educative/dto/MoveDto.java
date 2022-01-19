package plateforme_educative.dto;

import java.util.Date;

import plateforme_educative.models.GameStatus;
import plateforme_educative.models.Piece;


public class MoveDto {
	private int boardColumn;
    private int boardRow;
    private Date created;
    private String userName;
    private GameStatus gameStatus;
    private Piece playerPieceCode;
	public int getBoardColumn() {
		return boardColumn;
	}
	public void setBoardColumn(int boardColumn) {
		this.boardColumn = boardColumn;
	}
	public int getBoardRow() {
		return boardRow;
	}
	public void setBoardRow(int boardRow) {
		this.boardRow = boardRow;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public GameStatus getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
	public Piece getPlayerPieceCode() {
		return playerPieceCode;
	}
	public void setPlayerPieceCode(Piece playerPieceCode) {
		this.playerPieceCode = playerPieceCode;
	}
    
}
