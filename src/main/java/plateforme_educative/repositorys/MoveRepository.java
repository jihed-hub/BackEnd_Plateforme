package plateforme_educative.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import plateforme_educative.models.Game;
import plateforme_educative.models.Move;
import plateforme_educative.models.User;


@Repository
public interface MoveRepository extends JpaRepository<Move, Long>{
	 List<Move> findByGame(Game game);
	 List<Move> findByGameAndPlayer(Game game, User player);
	 int countByGameAndPlayer(Game game, User player);
}
