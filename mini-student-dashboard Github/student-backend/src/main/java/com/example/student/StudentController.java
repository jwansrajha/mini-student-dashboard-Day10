package com.example.student;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    private final JdbcTemplate jdbcTemplate;

    public StudentController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // GET /api/students : read rows from PostgreSQL
    @GetMapping
    public List<Student> getStudents() {
        return jdbcTemplate.query(
            "SELECT id, name, grade FROM students ORDER BY id",
            (rs, rowNum) -> new Student(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("grade")
            )
        );
    }

    // POST /api/students : create a row in PostgreSQL
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@RequestBody NewStudent request) {
        if (request.name() == null || request.name().isBlank()
                || request.name().trim().length() > 100
                || request.grade() == null
                || request.grade() < 0 || request.grade() > 100) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Provide a name and a grade from 0 to 100"
            );
        }

        return jdbcTemplate.queryForObject(
            "INSERT INTO students (name, grade) VALUES (?, ?) RETURNING id, name, grade",
            (rs, rowNum) -> new Student(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("grade")
            ),
            request.name().trim(), request.grade()
        );
    }

    public record Student(long id, String name, int grade) {}
    public record NewStudent(String name, Integer grade) {}
}
