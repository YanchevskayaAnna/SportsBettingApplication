package sportsbetting.model.outcome;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "outcomeodds")
@Data
@NoArgsConstructor
public class OutcomeOdd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "outcomes_id",
            referencedColumnName = "id")
    private Outcome outcome;

    private double oddValue;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;

    public OutcomeOdd(Outcome outcome) {
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return outcome.getValue() + ", odd : " + Double.toString(oddValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutcomeOdd that = (OutcomeOdd) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(outcome, that.outcome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, outcome);
    }
}
