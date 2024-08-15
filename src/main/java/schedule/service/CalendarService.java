package schedule.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import schedule.dto.CalendarRequestDto;
import schedule.dto.CalendarResponseDto;
import schedule.entity.Calendar;
import schedule.repository.CalendarRepository;

import java.util.List;

@Service
public class CalendarService {
    private final JdbcTemplate jdbcTemplate;

    public CalendarService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public CalendarResponseDto createCalendar(CalendarRequestDto requestDto) {
        // RequestDto -> Entity
        Calendar calendar = new Calendar(requestDto);

        // DB 저장
        CalendarRepository calendarRepository = new CalendarRepository(jdbcTemplate);
        Calendar saveCalendar = calendarRepository.save(calendar);

        // Entity -> ResponseDto
        CalendarResponseDto calendarResponseDto = new CalendarResponseDto(saveCalendar);

        return calendarResponseDto;
    }

    public List<CalendarResponseDto> getCalendars() {
        // DB 조회
        CalendarRepository calendarRepository = new CalendarRepository(jdbcTemplate);
        return calendarRepository.findAll();
    }

    public Long updateCalendar(Long id, CalendarRequestDto requestDto) {
        CalendarRepository calendarRepository = new CalendarRepository(jdbcTemplate);
        // 해당 메모가 DB에 존재하는지 확인
        Calendar calendar = calendarRepository.findById(id);
        if (calendar != null) {
            // memo 내용 수정
            calendarRepository.update(id, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("입력하신 아이디는 존재하지 않습니다.");
        }
    }

    public Long deleteCalendar(Long id, String password) {
        CalendarRepository calendarRepository = new CalendarRepository(jdbcTemplate);
        // 해당 메모가 DB에 존재하는지 확인
        Calendar calendar = calendarRepository.findById(id);
        if (calendar != null) {
            // memo 삭제
            calendarRepository.delete(id);
            return id;
        } else {
            throw new IllegalArgumentException("입력하신 아이디는 존재하지 않습니다.");
        }
    }
}
