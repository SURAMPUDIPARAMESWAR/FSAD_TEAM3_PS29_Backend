package com.fsad.controller;

import com.fsad.model.Student;
import com.fsad.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = {"http://localhost:5173", "https://fsad-team-3-ps-29.vercel.app"})
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping
    public Student addStudent(@RequestBody Student s) {
        return service.addStudent(s);
    }

    @GetMapping
    public List<Student> getStudents() {
        return service.getAllStudents();
    }
}