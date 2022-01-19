package plateforme_educative.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import plateforme_educative.models.Game;
import plateforme_educative.models.GameStatus;
import plateforme_educative.models.GameType;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,Long>{
	List<Game> findByGameTypeAndGameStatus(GameType GameType, GameStatus GameStatus);
    List<Game> findByGameStatus(GameStatus gameStatus);
}
