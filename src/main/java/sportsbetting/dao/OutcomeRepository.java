package sportsbetting.dao;

import sportsbetting.model.bet.Bet;
import sportsbetting.model.outcome.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {
    List<Outcome> findByBet(Bet bet);
}
