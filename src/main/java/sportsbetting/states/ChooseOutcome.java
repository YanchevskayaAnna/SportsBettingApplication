package sportsbetting.states;

import sportsbetting.model.bet.Bet;
import sportsbetting.model.outcome.Outcome;
import sportsbetting.service.IOutcomeService;
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
public class ChooseOutcome extends State {

    private static final String TITLE = "outcome";
    private IOutcomeService outcomeService;

    @Autowired
    public ChooseOutcome(MessageUtils messageUtils, IUIService uaService, IOutcomeService outcomeService) {
        super(messageUtils, uaService);
        this.outcomeService = outcomeService;
    }

    public Integer getPriority() {
        return 3;
    }

    @Override
    public int update() {
        Bet bet = (Bet) this.getEvents().get(getSelectedItem()-1);
        List<Outcome> outcomes = outcomeService.findByBet(bet);
        setEvents(outcomes);
        uaService.showListForChoice(TITLE, outcomes);
        return uaService.chooseEvent(outcomes);
    }

}
