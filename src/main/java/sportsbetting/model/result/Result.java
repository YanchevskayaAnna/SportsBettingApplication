package sportsbetting.model.result;

import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.sportevent.SportEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "results")
@Data
@NoArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private SportEvent sportEvent;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Outcome> outcomes;

    public Result(SportEvent sportEvent, List<Outcome> outcomes) {
        this.sportEvent = sportEvent;
        this.outcomes = outcomes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(id, result.id) &&
                Objects.equals(sportEvent, result.sportEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sportEvent);
    }
}
