package schedule.controller;


import schedule.dto.CalendarRequestDto;
import schedule.dto.CalendarResponseDto;
import org.springframework.web.bind.annotation.*;
import schedule.service.CalendarService;

import java.util.List;


// 일정 생성하기 API 를 받을 수 있는 Controller 와 메서드 생성
@RestController //Controller 과 ResponseBody 합친 것
@RequestMapping("/schedule")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }


    // 1단계 : 일정 작성
    @PostMapping("/register")
    public CalendarResponseDto createCalendar(@RequestBody CalendarRequestDto requestDto) {
        return calendarService.createCalendar(requestDto);
    }

    // 2단계 : 선택한 일정 조회
    @GetMapping("/lookup/{id}")
    public List<CalendarResponseDto> getCalendars(@PathVariable("id") String id) {
        return calendarService.getCalendars();
    }

    // 3단계 : 일정 목록 조회
    @GetMapping("/lookup-all")
    public List<CalendarResponseDto> getCalendars() {
        return calendarService.getCalendars();
    }

    // 4단계 : 선택한 일정 수정
    @PutMapping("/plan/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody CalendarRequestDto requestDto) {
        return calendarService.updateCalendar(id, requestDto);
    }

    // 5단계 : 선택한 일정 삭제
    @DeleteMapping("/delete/{id}")
    public Long deleteCalendar(@PathVariable Long id, @RequestParam String password) {
        return calendarService.deleteCalendar(id, password);
    }
}