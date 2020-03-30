package sportsbetting.service;

import sportsbetting.dao.OutcomeOddRepository;
import sportsbetting.model.outcome.OutcomeOdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultOutcomeOddService implements IOutcomeOddService {
    private OutcomeOddRepository outcomeOddRepository;

    @Autowired
    public DefaultOutcomeOddService(OutcomeOddRepository outcomeOddRepository) {
        this.outcomeOddRepository = outcomeOddRepository;
    }

    @Override
    public OutcomeOdd save(OutcomeOdd outcomeOdd) {
        return outcomeOddRepository.save(outcomeOdd);
    }

    @Override
    public List<OutcomeOdd> findAll() {
        return outcomeOddRepository.findAll();
    }
}
