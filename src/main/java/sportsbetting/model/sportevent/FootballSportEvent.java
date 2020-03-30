package sportsbetting.model.sportevent;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class FootballSportEvent extends SportEvent{

    public FootballSportEvent(String title, LocalDateTime startDate, LocalDateTime endDate) {
        super(title, startDate, endDate);
        this.type = "football";
    }

    public FootballSportEvent(String title) {
        super(title);
        this.type = "football";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
