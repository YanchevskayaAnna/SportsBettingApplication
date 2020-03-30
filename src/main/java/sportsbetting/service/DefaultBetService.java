package sportsbetting.service;

import sportsbetting.dao.BetRepository;
import sportsbetting.model.bet.Bet;
import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.sportevent.SportEvent;
import sportsbetting.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultBetService implements IBetService {

    private IOutcomeService outcomeService;
    private BetRepository betRepository;
    private RandomUtils randomUtils;

    @Autowired
    public DefaultBetService(IOutcomeService outcomeService, BetRepository betRepository, RandomUtils randomUtils) {
        this.outcomeService = outcomeService;
        this.betRepository = betRepository;
        this.randomUtils = randomUtils;
    }

    @Override
    public Outcome defineWinner(Bet bet) {
        List<Outcome> outcomes = outcomeService.findByBet(bet);
        return outcomes.get(randomUtils.defineRandomNumber(outcomes.size()));
    }

    @Override
    public Bet save(Bet bet) {
        return betRepository.save(bet);
    }

    @Override
    public List<Bet> findAll() {
        return betRepository.findAll();
    }

    @Override
    public Optional<Bet> getBetById(long id) {
        return betRepository.findById(id);
    }

    @Override
    public Bet updateBet(long id, Bet bet) {
        return betRepository.save(bet);
    }

    @Override
    public boolean deleteBet(long id) {
        betRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Bet> findBySportEvent(SportEvent sportEvent) {
        return betRepository.findBySportEvent(sportEvent);
    }
}
