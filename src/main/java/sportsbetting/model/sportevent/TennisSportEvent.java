package sportsbetting.model.sportevent;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class TennisSportEvent extends SportEvent{

    public TennisSportEvent(String title, LocalDateTime startDate, LocalDateTime endDate) {
        super(title, startDate, endDate);
        this.type = "tennis";
    }

    public TennisSportEvent(String title) {
        super(title);
        this.type = "tennis";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
