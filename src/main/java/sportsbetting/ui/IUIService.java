package sportsbetting.ui;

import java.util.List;

public interface IUIService {
    void showListForChoice(String title, List events);
    int chooseEvent(List events);
}
