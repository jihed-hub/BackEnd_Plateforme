package plateforme_educative.dto;

import plateforme_educative.models.GameType;
import plateforme_educative.models.Piece;

public class GameDto {
	 private int id;
	    private GameType gameType;
	    private Piece firstPlayerPieceCode;
		public GameDto(GameType gameType, Piece firstPlayerPieceCode) {
			super();
			this.gameType = gameType;
			this.firstPlayerPieceCode = firstPlayerPieceCode;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public GameType getGameType() {
			return gameType;
		}
		public void setGameType(GameType gameType) {
			this.gameType = gameType;
		}
		public Piece getFirstPlayerPieceCode() {
			return firstPlayerPieceCode;
		}
		public void setFirstPlayerPieceCode(Piece firstPlayerPieceCode) {
			this.firstPlayerPieceCode = firstPlayerPieceCode;
		}
		
	    
}
