package com.rest_api.rest.Controller;

import com.rest_api.rest.Entity.RestEntity;
import com.rest_api.rest.Service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class Controller {

    private final RestService todoService;

    // Constructor-based injection (good practice)
    public Controller(RestService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<RestEntity>> getAllTodos() {
        List<RestEntity> todos = todoService.findAll();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestEntity> getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    @PostMapping
    public ResponseEntity<RestEntity> createTodo(@RequestBody RestEntity todo) {
        return todoService.createTodo(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestEntity> updateTodo(@PathVariable Long id, @RequestBody RestEntity todo) {
        return todoService.updateTodo(id, todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
        return todoService.deleteTodoById(id);
    }
}
