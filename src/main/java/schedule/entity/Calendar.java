package schedule.entity;

import org.springframework.cglib.core.Local;
import org.springframework.data.relational.core.mapping.Column;
import schedule.dto.CalendarRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


//일정 데이터를 저장할 Calendar 클래스 생성
@Getter
@Setter
@NoArgsConstructor
public class Calendar {
    //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private long scheduleId;
    private String password;
    private Date creationDate;
    private Date modifyDate;
    private String name;
    private String scheduleInformation;

    public Calendar(CalendarRequestDto calendarRequestDto) {
        this.scheduleId = calendarRequestDto.getScheduleId();
        this.scheduleInformation = calendarRequestDto.getScheduleInformation();
        this.password = calendarRequestDto.getPassword();
        this.creationDate = calendarRequestDto.getCreationDate();
        this.modifyDate = calendarRequestDto.getModifyDate();
    }

    public void update(CalendarRequestDto calendarRequestDto) {
        this.scheduleId = calendarRequestDto.getScheduleId();
        this.scheduleInformation = calendarRequestDto.getScheduleInformation();
    }

}
