package com.example.taskifyproject.service;

import com.example.taskifyproject.entity.TaskEntity;
import com.example.taskifyproject.entity.UserEntity;

import java.util.List;

public interface MailService {
    void sendTaskAssignmentNotification(UserEntity user, TaskEntity task);
}