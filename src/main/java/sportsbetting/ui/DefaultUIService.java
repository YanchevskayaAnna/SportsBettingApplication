package sportsbetting.ui;

import sportsbetting.utils.MessageUtils;
import sportsbetting.utils.ScannerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUIService implements IUIService {

    private MessageUtils messageUtils;
    private ScannerUtils scannerUtils;

    @Autowired
    public DefaultUIService(MessageUtils messageUtils, ScannerUtils scannerUtils) {
        this.messageUtils = messageUtils;
        this.scannerUtils = scannerUtils;
    }

    @Override
    public void showListForChoice(String title, List events) {
        messageUtils.displayMessage("selectEvent", new Object[]{title});
        for (int i = 1; i <= events.size(); i++) {
            System.out.printf("\"%d\": %s\n", i, events.get(i - 1));
        }
        System.out.println("\"-1\": to exit");
    }

    @Override
    public int chooseEvent(List events) {
        int size = events.size();
        if (!(scannerUtils.hasInputInt())) {
            return wrongEvent(events);
        }
        int number = scannerUtils.getInputInt();
        if ((number > size) || (number < 0 && number != -1)) {
            return wrongEvent(events);
        }
        return number;
    }

    private int wrongEvent(List events) {
        messageUtils.displayMessage("wrongValue",  new Object[]{events.size()});
        return chooseEvent(events);
    }
}
