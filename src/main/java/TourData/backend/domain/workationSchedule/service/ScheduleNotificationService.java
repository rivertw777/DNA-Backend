package TourData.backend.domain.workationSchedule.service;

import TourData.backend.domain.user.model.entity.User;
import TourData.backend.domain.workationSchedule.model.entity.WorkationSchedule;
import TourData.backend.domain.workationSchedule.repository.WorkationScheduleRepository;
import TourData.backend.global.email.service.EmailService;
import java.time.LocalDateTime;
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
        LocalDateTime now = LocalDateTime.now();

        List<WorkationSchedule> expiredSchedules = workationScheduleRepository.findByEndDateBeforeAndIsExpiredFalse(now);

        for (WorkationSchedule schedule : expiredSchedules) {
            sendEmail(schedule);
        }
    }

    private void sendEmail(WorkationSchedule schedule){
        User user = schedule.getUser();
        String email =  user.getEmail();
        String subject = getSubject(schedule.getLocationName());
        String text = getText(user.getUsername(), schedule.getLocationName());
        emailService.sendEmail(email, subject, text);
        upDateStatus(user, schedule);
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

    private void upDateStatus(User user, WorkationSchedule schedule) {
        user.setHasReceivedEmail(true);
        schedule.setIsExpired(true);
    }

}