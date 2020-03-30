package sportsbetting.service;

import sportsbetting.model.bet.Bet;
import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.outcome.OutcomeOdd;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IOutcomeService {
    Outcome save(Outcome outcome);
    Optional<Outcome> getOutcomeById(long id);
    Outcome updateOutcome(long id, Outcome outcome);
    boolean deleteOutcome(long id);
    List<Outcome> findAll();
    Optional<OutcomeOdd> getCurrentOutcomeOdd(Outcome outcome, LocalDateTime currentDate);
    List<OutcomeOdd> getCurrentOutcomeOdd(List<Outcome> outcomes, LocalDateTime currentDate);
    List<Outcome> findByBet(Bet bet);
}
