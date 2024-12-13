package iuh.edu.vn.backend.services;

import iuh.edu.vn.backend.models.Candidate;
import iuh.edu.vn.backend.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendApplicationEmail(Candidate candidate, Job job) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(candidate.getEmail());
        message.setSubject("Job Application: " + job.getJobName());
        message.setText("Dear " + candidate.getFullName() + ",\n\nYou have been suggested for the job: " + job.getJobName() + ".\n\nBest regards,\nPhat's Company");
        mailSender.send(message);
    }
}
