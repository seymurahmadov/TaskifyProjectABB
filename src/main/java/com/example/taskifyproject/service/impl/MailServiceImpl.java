package com.example.taskifyproject.service.impl;

import com.example.taskifyproject.entity.TaskEntity;
import com.example.taskifyproject.entity.UserEntity;
import com.example.taskifyproject.service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendTaskAssignmentNotification(UserEntity user, TaskEntity task) {

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("New Task Assignment: " + task.getTitle());
            mailMessage.setText("You have been assigned a new task:\n\n"
                    + "Title: " + task.getTitle() + "\n"
                    + "Description: " + task.getDescription() + "\n"
                    + "Due Date: " + task.getDeadLine() + "\n"
                    + "Status: " + task.getTaskType() + "\n\n"
                    + "Please log in to eTaskify to view and manage the task.");

            javaMailSender.send(mailMessage);
        }
    }

