package sportsbetting.service;

import sportsbetting.dao.WagerRepository;
import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.outcome.OutcomeOdd;
import sportsbetting.model.result.Result;
import sportsbetting.model.sportevent.SportEvent;
import sportsbetting.model.user.Player;
import sportsbetting.model.wager.Wager;
import sportsbetting.utils.MessageUtils;
import sportsbetting.utils.ScannerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DefaultWagerService implements IWagerService {

    private IOutcomeService outcomeService;
    private WagerRepository wagerRepository;
    private MessageUtils messageUtils;
    private ScannerUtils scanner;

    @Autowired
    public DefaultWagerService(IOutcomeService outcomeService, WagerRepository wagerRepository, MessageUtils messageUtils, ScannerUtils scanner) {
        this.outcomeService = outcomeService;
        this.wagerRepository = wagerRepository;
        this.messageUtils = messageUtils;
        this.scanner = scanner;
    }

    @Override
    public Wager save(Wager wager) {
        return wagerRepository.save(wager);
    }

    @Override
    public Wager createWager(Outcome outcome) {
        OutcomeOdd outcomeOdd = outcomeService.getCurrentOutcomeOdd(outcome, LocalDateTime.now()).orElseThrow(() -> new RuntimeException("There is no outcomeOdd."));
        messageUtils.displayMessage("outcomeValue", new Object[]{outcome, outcomeOdd.getOddValue()});
        messageUtils.displayMessage("makeYourBet", null);
        if (!(scanner.hasInputDouble())) {
            messageUtils.displayMessage("enterValue", null);
            return createWager(outcome);
        }

        double wagerSum = scanner.getInputDouble();
        if (wagerSum == -1) {
            return null;
        } else {
            messageUtils.displayMessage("betAccepted", new Object[]{wagerSum});
        }

        Wager wager = new Wager();
        wager.setTimestamp(LocalDate.now());
        wager.setAmount(wagerSum);
        wager.setOutcomeOdd(outcomeOdd);
        wager.setCurrency(null);
        wager.setPlayer(null);
        return wagerRepository.save(wager);

    }

    @Override
    public double checkWinner(Wager wager, List<Result> results) {
        OutcomeOdd outcomeOdd = wager.getOutcomeOdd();
        Outcome outcome = outcomeOdd.getOutcome();
        SportEvent sportEvent = outcome.getBet().getSportEvent();
        for (Result result : results) {
            if (result.getSportEvent().equals(sportEvent)) {
                if (result.getOutcomes().contains(outcome)) {
                    return wager.getAmount() * outcomeOdd.getOddValue();
                }
                return 0;
            }
        }
        return 0;
    }

    @Override
    public List<Wager> findAll() {
        return wagerRepository.findAll();
    }

    @Override
    public List<Wager> findAllByPlayer(Player player){
        return wagerRepository.findByPlayer(player);
    }

    @Override
    public Optional<Wager> getWagerById(long id) {
        return wagerRepository.findById(id);
    }

    @Override
    public Wager updateWager(long id, Wager wager) {
        return wagerRepository.save(wager);
    }

    @Override
    public boolean deleteWager(long id) {
        wagerRepository.deleteById(id);
        return true;
    }
}
