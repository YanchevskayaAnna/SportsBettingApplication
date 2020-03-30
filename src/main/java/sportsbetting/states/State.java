package sportsbetting.states;

import sportsbetting.ui.IUIService;
import sportsbetting.utils.MessageUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@Data
@NoArgsConstructor
public abstract class State {

    @Autowired
    private List<State> states;
    private State nextState;
    private State exitState;
    private List events;
    private int selectedItem;
    protected MessageUtils messageUtils;
    protected IUIService uaService;

    @Autowired
    public State(MessageUtils messageUtils, IUIService uaService) {
        this.messageUtils = messageUtils;
        this.uaService = uaService;
    }

    public final void updateState() {
        selectedItem = update();
        if (selectedItem == -1) {
            messageUtils.displayMessage("chosenExit", null);
            nextState = getExitState();
        }
        else if (selectedItem >=0) {
            printChoice();
            nextState = getNextState();
            nextState.setEvents(events);
            nextState.setSelectedItem(selectedItem);
        }
        if (selectedItem >= -1){
            nextState.updateState();
        }
    }

    private State getExitState() {
        if (exitState == null) {
            Optional<State> exitStateOptional =
                    states.stream()
                            .filter(el -> el.getPriority() < 0)
                            .findFirst();
            exitState = exitStateOptional.get();
        }
        return exitState;
    }

    private State getNextState() {
        Optional<State> nextState =
                states.stream()
                        .filter(el -> el.getPriority() > getPriority())
                        .min(Comparator.comparing(State::getPriority));

        Optional<State> firstState =
                states.stream()
                        .filter(el -> el.getPriority() > 0)
                        .min(Comparator.comparing(State::getPriority));

        return nextState.orElseGet(firstState::get);
    }

    public abstract Integer getPriority();
    public abstract int update();

    public void printChoice(){
        messageUtils.displayMessage("goodChoise", new Object[]{events.get(selectedItem - 1)});
    }

}
