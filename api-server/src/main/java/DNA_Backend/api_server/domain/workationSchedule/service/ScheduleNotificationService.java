package DNA_Backend.api_server.domain.workationSchedule.service;

import static ch.qos.logback.core.util.StringUtil.capitalizeFirstLetter;

import DNA_Backend.api_server.domain.user.model.enums.PopupStatus;
import DNA_Backend.api_server.domain.user.model.entity.User;
import DNA_Backend.api_server.domain.workationSchedule.model.entity.WorkationSchedule;
import DNA_Backend.api_server.domain.workationSchedule.repository.WorkationScheduleRepository;
import DNA_Backend.api_server.common.email.service.EmailService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleNotificationService {

    private static final String SUBJECT_PREFIX = "[DNA] How was your workation in ";

    private final WorkationScheduleRepository workationScheduleRepository;
    private final EmailService emailService;

    @Transactional
    @Scheduled(cron = "0 0 2 * * *")
    public void notifyExpiredSchedules() {
        LocalDate now = LocalDate.now();
        List<WorkationSchedule> expiredSchedules = workationScheduleRepository.findByEndDateBeforeAndIsExpiredFalse(now);
        expiredSchedules.forEach(this::sendEmail);
    }

    private void sendEmail(WorkationSchedule schedule){
        User user = schedule.getUser();
        String email =  user.getEmail();
        String locationName = capitalizeFirstLetter(schedule.getLocation().getName().getValue());
        String subject = getSubject(locationName);
        String text = getText(user.getUsername(), locationName);

        emailService.sendEmail(email, subject, text);
        updateStatus(user, schedule);
    }

    private String getSubject(String locationName) {
        return SUBJECT_PREFIX + locationName + "?";
    }

    private String getText(String username, String locationName) {
        String text = "Hello, " + username + "!\n"
                + "We hope you had a wonderful experience during your workation in " + locationName + ".\n"
                + "Your feedback is valuable to us, and we would love to hear your thoughts about the workation location.\n"
                + "Please visit our website at https://dna-kangwon.site/ to leave a review.\n"
                + "Thank you for helping us improve our services!";
        return text;
    }

    private void updateStatus(User user, WorkationSchedule schedule) {
        user.setPopupStatus(PopupStatus.REVIEW_WRITING);
        schedule.setIsExpired(true);
    }

}