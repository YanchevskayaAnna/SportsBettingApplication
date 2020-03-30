package sportsbetting.dao;

import sportsbetting.model.bet.Bet;
import sportsbetting.model.sportevent.SportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findBySportEvent(SportEvent sportEvent);
}
