package schedule.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CalendarRequestDto {

    private Long scheduleId;
    private String name;
    private String password;
    private Date creationDate;
    private Date modifyDate;
    private String scheduleInformation;

}
