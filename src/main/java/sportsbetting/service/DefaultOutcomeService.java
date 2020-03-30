package sportsbetting.service;

import sportsbetting.dao.OutcomeOddRepository;
import sportsbetting.dao.OutcomeRepository;
import sportsbetting.model.bet.Bet;
import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.outcome.OutcomeOdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultOutcomeService implements IOutcomeService {

    private OutcomeRepository outcomeRepository;
    private OutcomeOddRepository outcomeOddRepository;

    @Autowired
    public DefaultOutcomeService(OutcomeRepository outcomeRepository, OutcomeOddRepository outcomeOddRepository) {
        this.outcomeRepository = outcomeRepository;
        this.outcomeOddRepository = outcomeOddRepository;
    }

    @Override
    public Outcome save(Outcome outcome) {
        return outcomeRepository.save(outcome);
    }

    @Override
    public Optional<OutcomeOdd> getCurrentOutcomeOdd(Outcome outcome, LocalDateTime currentDate) {
        return outcomeOddRepository.findByOutcome(outcome).stream()
                .filter(odd -> odd.getValidFrom().compareTo(currentDate) <= 0)
                .filter(odd -> odd.getValidTo().compareTo(currentDate) >= 0)
                .findAny();
    }

    @Override
    public List<OutcomeOdd> getCurrentOutcomeOdd(List<Outcome> outcomes, LocalDateTime currentDate) {
        return outcomes
                .stream()
                .map(el-> getCurrentOutcomeOdd(el, LocalDateTime.now()).get())
                .collect(Collectors.toList());
    }

    public List<Outcome> findByBet(Bet bet) {
        return outcomeRepository.findByBet(bet);
    }

    @Override
    public List<Outcome> findAll() {
        return outcomeRepository.findAll();
    }

    @Override
    public Optional<Outcome> getOutcomeById(long id) {
        return outcomeRepository.findById(id);
    }

    @Override
    public Outcome updateOutcome(long id, Outcome outcome) {
        return outcomeRepository.save(outcome);
    }

    @Override
    public boolean deleteOutcome(long id) {
        outcomeRepository.deleteById(id);
        return true;
    }
}
