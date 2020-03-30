package sportsbetting.service;

import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.result.Result;
import sportsbetting.model.user.Player;
import sportsbetting.model.wager.Wager;

import java.util.List;
import java.util.Optional;

public interface IWagerService {
    Wager save(Wager wager);
    Wager createWager(Outcome outcome);
    Optional<Wager> getWagerById(long id);
    Wager updateWager(long id, Wager wager);
    boolean deleteWager(long id);

    double checkWinner(Wager wager, List<Result> results);
    List<Wager> findAll();
    List<Wager> findAllByPlayer(Player player);
}
