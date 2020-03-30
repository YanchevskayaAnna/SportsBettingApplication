package sportsbetting.service;

import sportsbetting.model.result.Result;
import sportsbetting.model.sportevent.SportEvent;

import java.util.List;
import java.util.Optional;

public interface ISportEventService {
      SportEvent save(SportEvent sportEvent);
      List<SportEvent> findAll();
      Optional<SportEvent> getSportEventById(long id);
      SportEvent updateSportEvent(long id, SportEvent sportEvent);
      boolean deleteSportEvent(long id);

      List<Result> getResults(List<SportEvent> sportEvents);
}
