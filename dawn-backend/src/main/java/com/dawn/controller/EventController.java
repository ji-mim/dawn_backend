package com.dawn.controller;


import com.dawn.domain.Event;
import com.dawn.service.EventService;
import com.dawn.service.dto.EventResponse;
import com.dawn.service.dto.EventSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@Tag(name = "Event", description = "사건 관련 API")
public class EventController {

    private final EventService eventService;

    @GetMapping("/viewEventByDate")
    @Operation(summary = "오늘 기준으로 가까운 사건 조회")
    public ResponseEntity<List<EventSummaryResponse>> viewByDate() {
        List<EventSummaryResponse> responseList = eventService.getUpcomingEvents().stream()
                .map(EventSummaryResponse::from)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/viewEventList")
    @Operation(summary = "전체 사건 목록 조회 (최신순)")
    public ResponseEntity<List<EventSummaryResponse>> viewAll() {
        List<EventSummaryResponse> responseList = eventService.getAllByDateDesc().stream()
                .map(EventSummaryResponse::from)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/viewEventBySearch")
    @Operation(summary = "사건 키워드 검색 (ex. 이름, 국가, 카테고리)")
    public ResponseEntity<List<EventSummaryResponse>> search(@RequestParam String keyword) {
        List<EventSummaryResponse> responseList = eventService.search(keyword).stream()
                .map(EventSummaryResponse::from)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/viewEventInfo")
    @Operation(summary = "사건 상세 조회")
    public ResponseEntity<EventResponse> detail(@RequestParam Long id) {
        Event event = eventService.getById(id);
        return ResponseEntity.ok(EventResponse.from(event));
    }
}