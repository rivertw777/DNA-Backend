package DNA_Backend.api_server.domain.workationOffice.controller;

import DNA_Backend.api_server.domain.workationOffice.dto.response.BookmarkedWorkationOfficeResponse;
import DNA_Backend.api_server.domain.workationOffice.dto.response.CheckWorkationOfficeBookmarkResponse;
import DNA_Backend.api_server.domain.workationOffice.service.WorkationOfficeBookmarkService;
import DNA_Backend.api_server.common.security.auth.UserDetailsCustom;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workation-offices")
public class WorkationOfficeController {

    private final WorkationOfficeBookmarkService workationOfficeBookmarkService;

    @Operation(summary = "USER - 워케이션 오피스 북마크")
    @PostMapping("/{officeId}/bookmark")
    public ResponseEntity<Void> bookmarkWorkationOffice(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
                                                 @Valid @PathVariable("officeId") Long officeId) {
        Long userId = userDetailsCustom.getUser().getId();
        workationOfficeBookmarkService.bookmarkWorkationOffice(userId, officeId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "USER - 워케이션 오피스 북마크 취소")
    @DeleteMapping("/{officeId}/bookmark")
    public ResponseEntity<Void> unbookmarkWorkationOffice(@AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
                                                   @Valid @PathVariable("officeId") Long officeId) {
        Long userId = userDetailsCustom.getUser().getId();
        workationOfficeBookmarkService.unbookmarkWorkationOffice(userId, officeId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "USER - 워케이션 오피스 북마크 여부 확인")
    @GetMapping("/{officeId}/bookmark")
    public ResponseEntity<CheckWorkationOfficeBookmarkResponse> checkWorkationOfficeBookmark(
            @AuthenticationPrincipal UserDetailsCustom userDetailsCustom,
            @Valid @PathVariable("officeId") Long officeId) {
        Long userId = userDetailsCustom.getUser().getId();
        CheckWorkationOfficeBookmarkResponse response = workationOfficeBookmarkService.checkWorkationOfficeBookmark(userId, officeId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "USER - 전체 북마크 워케이션 오피스 조회")
    @GetMapping("/bookmark")
    public ResponseEntity<List<BookmarkedWorkationOfficeResponse>> getAllBookmarkedWorkationOffices(
            @AuthenticationPrincipal UserDetailsCustom userDetailsCustom) {
        Long userId = userDetailsCustom.getUser().getId();
        List<BookmarkedWorkationOfficeResponse> responses = workationOfficeBookmarkService.getAllBookmarkedWorkationOffices(userId);
        return ResponseEntity.ok(responses);
    }

}