package sportsbetting.states;

import sportsbetting.model.sportevent.SportEvent;
import sportsbetting.service.ISportEventService;
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
public class ChooseSportEvent extends State {
    private ISportEventService sportEventService;

    @Autowired
    public ChooseSportEvent(MessageUtils messageUtils, IUIService uaService, ISportEventService sportEventService) {
        super(messageUtils, uaService);
        this.sportEventService = sportEventService;
    }

    public Integer getPriority() {
        return 1;
    }

    @Override
    public int update() {
        List<SportEvent> sportEvents = sportEventService.findAll();
        uaService.showListForChoice("event", sportEvents);
        setEvents(sportEvents);
        return uaService.chooseEvent(sportEvents);
    }
}
