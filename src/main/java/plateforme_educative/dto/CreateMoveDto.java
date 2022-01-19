package plateforme_educative.dto;

import javax.validation.constraints.NotNull;

public class CreateMoveDto {
	 @NotNull
	    int boardRow;
	    @NotNull
	    int boardColumn;
		public int getBoardRow() {
			return boardRow;
		}
		public void setBoardRow(int boardRow) {
			this.boardRow = boardRow;
		}
		public int getBoardColumn() {
			return boardColumn;
		}
		public void setBoardColumn(int boardColumn) {
			this.boardColumn = boardColumn;
		}
	    
	    
}
