package sportsbetting.service;

import sportsbetting.model.outcome.OutcomeOdd;

import java.util.List;

public interface IOutcomeOddService {
    OutcomeOdd save(OutcomeOdd outcomeOdd);
    List<OutcomeOdd> findAll();
}
