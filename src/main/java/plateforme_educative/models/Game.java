package plateforme_educative.models;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Check;

import plateforme_educative.models.User;

@Entity
@Check(constraints = "first_player_piece_code = 'O' or first_player_piece_code = 'X' " +
        "and game_type = 'COMPUTER' or game_type = 'COMPETITION' " +
        "and game_status = 'IN_PROGRESS' or game_status = 'FIRST_PLAYER_WON' or game_status = 'SECOND_PLAYER_WON'" +
        "or game_status = 'TIE' or game_status = 'WAITS_FOR_PLAYER' ")
public class Game {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "second_player_id", nullable = true)
    private User secondPlayer;

    @ManyToOne
    @JoinColumn(name = "first_player_id", nullable = false)
    private User firstPlayer;

    @Enumerated(EnumType.STRING)
    private Piece firstPlayerPieceCode;

    @Enumerated(EnumType.STRING)
    private GameType gameType;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @Column(name = "created", nullable = false)
    private Date created;

	public Game() {}

	public Game(User secondPlayer, User firstPlayer, Piece firstPlayerPieceCode, GameType gameType,
			GameStatus gameStatus, Date created) {
		super();
		this.secondPlayer = secondPlayer;
		this.firstPlayer = firstPlayer;
		this.firstPlayerPieceCode = firstPlayerPieceCode;
		this.gameType = gameType;
		this.gameStatus = gameStatus;
		this.created = created;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(User secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	public User getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(User firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public Piece getFirstPlayerPieceCode() {
		return firstPlayerPieceCode;
	}

	public void setFirstPlayerPieceCode(Piece firstPlayerPieceCode) {
		this.firstPlayerPieceCode = firstPlayerPieceCode;
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
