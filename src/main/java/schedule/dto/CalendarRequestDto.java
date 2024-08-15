package schedule.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CalendarRequestDto {

    private Long scheduleId;
    private String password;
    private Timestamp creationDate;
    private Timestamp modifyDate;
    private String scheduleInformation;

}
