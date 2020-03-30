package sportsbetting.states;

import sportsbetting.model.bet.Bet;
import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.result.Result;
import sportsbetting.model.sportevent.SportEvent;
import sportsbetting.model.wager.Wager;
import sportsbetting.service.ISportEventService;
import sportsbetting.service.IWagerService;
import sportsbetting.ui.IUIService;
import sportsbetting.utils.MessageUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
public class Exit extends State {

    private IWagerService wagerService;
    private ISportEventService sportEventService;

    @Autowired
    public Exit(MessageUtils messageUtils, IUIService uaService, IWagerService wagerService, ISportEventService sportEventService) {
        super(messageUtils, uaService);
        this.wagerService = wagerService;
        this.sportEventService = sportEventService;
    }

    public Integer getPriority() {
        return -1;
    }

    @Override
    public int update() {
        List<Result> results = sportEventService.getResults(sportEventService.findAll());
        double totalWinSum = 0;

        for (Wager wager : wagerService.findAll()){
            Outcome wagerOutcome = wager.getOutcomeOdd().getOutcome();
            Bet bet = wagerOutcome.getBet();
            SportEvent sportEvent = bet.getSportEvent();
            System.out.printf("Sport event: \"%s\"\n", sportEvent);
            System.out.printf("Bet: \"%s\"\n", bet);
            double winSum = wagerService.checkWinner(wager, results);
            totalWinSum += winSum;
            if (winSum > 0) {
                messageUtils.displayMessage("win", new Object[]{winSum});
            } else {
                messageUtils.displayMessage("lost", null);
            }
        }
        messageUtils.displayMessage("totalWin", new Object[]{totalWinSum});
        messageUtils.displayMessage("goodbye", null);
        messageUtils.exit();
        return -2;
    }

}
