package sportsbetting.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import static sportsbetting.utils.Utils.LOCALE_LANGUAGE;

@Service
public class MessageUtils {
    private MessageSource messageSource;

    @Autowired
    public MessageUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void displayMessage(String message, Object[] args) {
        System.out.println(messageSource.getMessage(message, args, LOCALE_LANGUAGE));
    }

    public void exit() {
        System.exit(1);
    }

}
