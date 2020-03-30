package sportsbetting.service;

import sportsbetting.model.bet.Bet;
import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.sportevent.SportEvent;

import java.util.List;
import java.util.Optional;

public interface IBetService {
    Bet save(Bet bet);
    List<Bet> findAll();
    Optional<Bet> getBetById(long id);
    Bet updateBet(long id, Bet bet);
    boolean deleteBet(long id);

    List<Bet> findBySportEvent(SportEvent sportEvent);
    Outcome defineWinner(Bet bet);
}
