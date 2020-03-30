package sportsbetting.service;

import sportsbetting.dao.SportEventRepository;
import sportsbetting.model.bet.Bet;
import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.result.Result;
import sportsbetting.model.sportevent.SportEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultSportEventService implements ISportEventService {

    private IBetService betService;
    private SportEventRepository sportEventRepository;
    private IResultService resultService;

    @Autowired
    public DefaultSportEventService(IBetService betService, SportEventRepository sportEventRepository, IResultService resultService) {
        this.betService = betService;
        this.sportEventRepository = sportEventRepository;
        this.resultService = resultService;
    }

    @Override
    public SportEvent save(SportEvent sportEvent) {
        return sportEventRepository.save(sportEvent);
    }

    @Override
    public List<SportEvent> findAll() {
        return sportEventRepository.findAll();
    }

    @Override
    public Optional<SportEvent> getSportEventById(long id) {
        return sportEventRepository.findById(id);
    }

    @Override
    public SportEvent updateSportEvent(long id, SportEvent sportEvent) {
        return sportEventRepository.save(sportEvent);
    }

    @Override
    public boolean deleteSportEvent(long id) {
        sportEventRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Result> getResults(List<SportEvent> sportEvents) {
        List<Result> results = new ArrayList<>();
        for (SportEvent sportEvent: sportEvents) {
            Result result = new Result();
            result.setSportEvent(sportEvent);
            List<Outcome> outcomes = new ArrayList<>();
            for (Bet bet: betService.findBySportEvent(sportEvent)) {
                outcomes.add(betService.defineWinner(bet));
            }
            result.setOutcomes(outcomes);
            results.add(resultService.save(result));
        }

        return results;
    }
}
