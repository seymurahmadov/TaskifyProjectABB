package com.example.taskifyproject.controller;

import com.example.taskifyproject.dto.request.TaskRequestDto;
import com.example.taskifyproject.dto.response.TaskResponseDto;
import com.example.taskifyproject.exception.handler.SuccessDetails;
import com.example.taskifyproject.service.impl.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/task")
@SecurityRequirement(name = "Bearer Authentication")
public class TaskController {
    private final TaskServiceImpl taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(summary = "Get all tasks")
    public ResponseEntity<SuccessDetails<List<TaskResponseDto>>> getAll() {
        return  ResponseEntity.ok(new SuccessDetails<>(taskService.getAll(), HttpStatus.OK.value(),true));
    }

    @PostMapping
    @Operation(summary = "Create task")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody TaskRequestDto taskRequestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder errorMsg = new StringBuilder("Validation error(s): ");
            for (FieldError error : errors) {
                errorMsg.append(error.getDefaultMessage()).append("; ");
            }
            return ResponseEntity.badRequest().body(errorMsg);
        }
        taskService.create(taskRequestDto);
        return ResponseEntity.ok(new SuccessDetails<>("Task save Successfully!", HttpStatus.OK.value(),true));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SuccessDetails<String>> delete(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.ok(new SuccessDetails<>("Task delete Successfully!", HttpStatus.OK.value(),true));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SuccessDetails<String>> update(@PathVariable Long id, @RequestBody TaskRequestDto taskRequestDto){
        taskService.update(id, taskRequestDto);
        return ResponseEntity.ok(new SuccessDetails<>("Task update Successfully!", HttpStatus.OK.value(),true));
    }
}
