package sportsbetting.dao;

import sportsbetting.model.user.Player;
import sportsbetting.model.wager.Wager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WagerRepository extends JpaRepository<Wager, Long> {
    List<Wager> findByPlayer(Player player);
}
