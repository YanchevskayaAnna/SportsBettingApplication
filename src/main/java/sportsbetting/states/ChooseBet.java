package sportsbetting.states;

import sportsbetting.model.bet.Bet;
import sportsbetting.model.sportevent.SportEvent;
import sportsbetting.service.IBetService;
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
public class ChooseBet extends State {

    private static final String TITLE = "bet";
    private IBetService betService;

    @Autowired
    public ChooseBet(MessageUtils messageUtils, IUIService uaService, IBetService betService) {
        super(messageUtils, uaService);
        this.betService = betService;
    }

    public Integer getPriority() {
        return 2;
    }

    @Override
    public int update() {
        SportEvent sportEvent = (SportEvent) this.getEvents().get(getSelectedItem() - 1);
        List<Bet> bets = betService.findBySportEvent(sportEvent);
        setEvents(bets);
        uaService.showListForChoice(TITLE, bets);
        return uaService.chooseEvent(bets);
    }

}
