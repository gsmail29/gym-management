package scheduler.lambda.component;

import java.time.Instant;
import java.util.Date;

import biweekly.ICalendar;
import common.accessor.DynamoDbAccessor;
import common.pojo.Account;
import common.pojo.Schedule;

public class ScheduleCreatorComponent {
    private final DynamoDbAccessor dbAccessor;

    public ScheduleCreatorComponent() {
        dbAccessor = new DynamoDbAccessor();
    }
    
    public void createSchedule(final String accountId) {

        final Account accountDetails = dbAccessor.readAccountDetailsFromDb(accountId);
        //create an empty schedule
        final Schedule schedule = Schedule.builder()
            .accountId(accountDetails.getAccountId())
            .iCalendar(getNewCalendar(accountDetails))
            .build();
        //write the schedule to s3

    }

    private ICalendar getNewCalendar(final Account account) {
        final ICalendar iCalendar = new ICalendar();
        iCalendar.setName(account.getAccountId() + ":" + "calender");
        iCalendar.setLastModified(Date.from(Instant.now()));
        return iCalendar;
    }

}
