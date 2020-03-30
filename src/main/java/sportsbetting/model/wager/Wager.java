package sportsbetting.model.wager;

import sportsbetting.model.outcome.OutcomeOdd;
import sportsbetting.model.user.Currency;
import sportsbetting.model.user.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Wager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Player player;

    @NotNull(message = "*Please provide an outcome")
    @OneToOne
    private OutcomeOdd outcomeOdd;
    @NotNull(message = "*Please provide an amount")
    private double amount;
    private Currency currency;
    private LocalDate timestamp;
    private Boolean processed;
    private Boolean win;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wager wager = (Wager) o;
        return Objects.equals(id, wager.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
