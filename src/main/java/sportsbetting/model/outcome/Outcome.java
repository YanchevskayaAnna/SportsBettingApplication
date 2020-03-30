package sportsbetting.model.outcome;

import sportsbetting.model.bet.Bet;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "outcomes")
@Data
@NoArgsConstructor
public class Outcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bets_id",
            referencedColumnName = "id")
    private Bet bet;

    private String value;

    @OneToMany(mappedBy = "outcome", fetch = FetchType.LAZY)
    private List<OutcomeOdd> odds;

    public Outcome(String value) {
        this.value = value;
    }

    public Outcome(String value, Bet bet) {
        this.value = value;
        this.bet = bet;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Outcome outcome = (Outcome) o;
        return Objects.equals(id, outcome.id) &&
                Objects.equals(bet, outcome.bet) &&
                Objects.equals(value, outcome.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bet);
    }
}
