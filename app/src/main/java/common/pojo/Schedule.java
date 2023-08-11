package common.pojo;

import biweekly.ICalendar;
import lombok.Builder;

@Builder
public class Schedule {
    private String accountId;
    private ICalendar iCalendar;

    public String serialize() {
        return iCalendar.writeJson();
    }
}
