package schedule.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.PathVariable;
import schedule.dto.CalendarRequestDto;
import schedule.dto.CalendarResponseDto;
import schedule.entity.Calendar;

import java.sql.*;
import java.util.List;

public class CalendarRepository {
    private final JdbcTemplate jdbcTemplate;

    public CalendarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1단계
    public Calendar save(Calendar calendar) {
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO calendar (scheduleId, password, creationDate, modifyDate, scheduleInformation) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, calendar.getScheduleId());
            preparedStatement.setString(2, calendar.getPassword());
            preparedStatement.setDate(3, calendar.getCreationDate());
            preparedStatement.setDate(4, calendar.getModifyDate());
            preparedStatement.setString(5, calendar.getScheduleInformation());
            return preparedStatement;
        }, keyHolder);

        // DB Insert 후 받아온 기본키 확인
        long scheduleId = keyHolder.getKey().longValue();
        calendar.setScheduleId(scheduleId);

        return calendar;
    }

    // 2단계
    public CalendarResponseDto getCalendarById(Long id) {
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


    // 3단계
    public List<CalendarResponseDto> findAll() {
        // DB 조회
        String sql = "SELECT * FROM Schedule";

        return jdbcTemplate.query(sql, new RowMapper<CalendarResponseDto>() {
            @Override
            public CalendarResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Date modifyDate = rs.getDate("modifyDate");
                String name = rs.getString("name");
                return new CalendarResponseDto(modifyDate.getTime(), name);
            }
        });
    }

    // 4단계
    public void update(Long id, CalendarRequestDto requestDto) {
        String sql = "UPDATE Schedule SET username = ?, contents = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getScheduleId(), requestDto.getScheduleInformation(), id);
    }

    // 5단계
    public void delete(Long id) {
        String sql = "DELETE FROM Schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    public Calendar findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM Schedule WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Calendar calendar = new Calendar();
                calendar.setName(resultSet.getString("username"));
                calendar.setScheduleInformation(resultSet.getString("contents"));
                return calendar;
            } else {
                return null;
            }
        }, id);
    }
}
