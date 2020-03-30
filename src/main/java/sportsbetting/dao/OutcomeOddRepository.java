package sportsbetting.dao;

import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.outcome.OutcomeOdd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutcomeOddRepository extends JpaRepository<OutcomeOdd, Long> {
    List<OutcomeOdd> findByOutcome(Outcome outcome);
}
