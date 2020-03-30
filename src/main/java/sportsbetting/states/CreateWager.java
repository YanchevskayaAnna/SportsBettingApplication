package sportsbetting.states;

import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.wager.Wager;
import sportsbetting.service.IWagerService;
import sportsbetting.ui.IUIService;
import sportsbetting.utils.MessageUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class CreateWager extends State {

    private IWagerService wagerService;

    @Autowired
    public CreateWager(MessageUtils messageUtils, IUIService uaService, IWagerService wagerService) {
        super(messageUtils, uaService);
        this.wagerService = wagerService;
    }

    public Integer getPriority() {
        return 4;
    }

    @Override
    public int update() {
        Outcome outcome = (Outcome) this.getEvents().get(getSelectedItem() - 1);
        Wager wager = wagerService.createWager(outcome);
        return (wager == null) ? -1 : 0;
    }

    public void printChoice() {
        messageUtils.displayMessage("wagersuccess", null);
    }

}
