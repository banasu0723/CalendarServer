package schedule.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import schedule.dto.CalendarRequestDto;
import schedule.dto.CalendarResponseDto;
import schedule.entity.Calendar;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

// 일정 생성하기 API 를 받을 수 있는 Controller 와 메서드 생성
@RestController //Controller 과 ResponseBody 합친 것
@RequestMapping("/schedule")
public class CalendarController {

    private final JdbcTemplate jdbcTemplate;

    public CalendarController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //일정 작성
    @PostMapping("/register")
    public CalendarResponseDto createCalendar(@RequestBody CalendarRequestDto requestDto) {
        //RequestDto -> Entity
        Calendar calendar = new Calendar(requestDto);

        KeyHolder keyHolder = new GeneratedKeyHolder(); //기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO calendar (scheduleId, password, creationDate, modifyDate, scheduleInformation) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, calendar.getScheduleId());
            preparedStatement.setString(2, calendar.getPassword());
            preparedStatement.setTimestamp(3, calendar.getCreationDate());
            preparedStatement.setTimestamp(4, calendar.getModifyDate());
            preparedStatement.setString(5, calendar.getScheduleInformation());
            return preparedStatement;
        }, keyHolder);

        // DB Insert 후 받아온 기본키 확인
        long scheduleId = keyHolder.getKey().longValue();
        calendar.setScheduleId(scheduleId);

        // Entity -> ResponseDto
        CalendarResponseDto calendarResponseDto = new CalendarResponseDto(calendar);
        return calendarResponseDto;

    }

    // 특정 일정 조회
    @GetMapping("/lookup/{id}")
    public CalendarResponseDto getCalendarById(@PathVariable("id") Long id) {
        // DB 조회
        String sql = "SELECT * FROM calendar WHERE scheduleId = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<CalendarResponseDto>() {
                @Override
                public CalendarResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long scheduleId = rs.getLong("scheduleId");
                    String scheduleInformation = rs.getString("scheduleInformation");
                    return new CalendarResponseDto(scheduleId, scheduleInformation);
                }
            });
        } catch (Exception e) {
            // 해당 ID의 일정이 없는 경우 출력
            throw new RuntimeException("해당 ID의 일정이 없습니다.");
        }
    }

    //일정 목록 조회
    @GetMapping("/lookup-all")
    public List<CalendarResponseDto> getCalendars() {
        // DB 조회
        String sql = "SELECT * FROM Schedule ";

        return jdbcTemplate.query(sql, new RowMapper<CalendarResponseDto>() {
            @Override
            public CalendarResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long scheduleId = rs.getLong("scheduleId");
                String scheduleInformation = rs.getString("scheduleInformation");
                return new CalendarResponseDto(scheduleId, scheduleInformation);
            }
        });
    }

}