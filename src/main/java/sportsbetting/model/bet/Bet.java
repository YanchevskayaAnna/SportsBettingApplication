package sportsbetting.model.bet;

import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.sportevent.SportEvent;
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
@Table(name = "bets")
@Data
@NoArgsConstructor
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "events_id", referencedColumnName = "id")
    private SportEvent sportEvent;

    private String description;
    private BetType betType;

    @OneToMany(mappedBy = "bet", fetch = FetchType.LAZY)
    private List<Outcome> outcomes;

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return Objects.equals(id, bet.id) &&
                Objects.equals(sportEvent, bet.sportEvent) &&
                betType == bet.betType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sportEvent, betType);
    }
}
