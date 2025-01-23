package com.rest_api.rest.Controller;

import com.rest_api.rest.Entity.RestEntity;
import com.rest_api.rest.Service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class Controller{

    @Autowired
    private RestService todoService;

    @GetMapping
    public List<RestEntity> getAllTodos() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestEntity> getTodoById(@PathVariable Long id) {
        return todoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RestEntity> createTodo(@RequestBody RestEntity todo) {
        RestEntity savedTodo = todoService.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestEntity> updateTodo(@PathVariable Long id, @RequestBody RestEntity todo) {
        return todoService.findById(id)
                .map(existingTodo -> {
                    todo.setId(id);
                    return ResponseEntity.ok(todoService.save(todo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
        if (todoService.findById(id).isPresent()) {
            todoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

