package schedule.dto;

import lombok.Getter;
import schedule.entity.Calendar;


//Client 에 데이터를 반환할 때 사용할 클래스 생성
@Getter
public class CalendarResponseDto {
    //private long scheduleId;
    private Long scheduleId;
    private String scheduleInformation;

    public CalendarResponseDto(Calendar calendar) {
        this.scheduleId = calendar.getScheduleId();
        this.scheduleInformation = calendar.getScheduleInformation();
    }

    public CalendarResponseDto(Long scheduleId, String scheduleInformaiton) {
        this.scheduleId = scheduleId;
        this.scheduleInformation = scheduleInformaiton;
    }
}
