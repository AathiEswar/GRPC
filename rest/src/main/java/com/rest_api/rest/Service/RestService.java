package com.rest_api.rest.Service;

import com.rest_api.rest.Entity.RestEntity;
import com.rest_api.rest.Repository.RestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestService {

    private final RestRepository todoRepository;

    public RestService(RestRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<RestEntity> findAll() {
        return todoRepository.findAll();
    }

    public ResponseEntity<RestEntity> getTodoById(Long id) {
        return todoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<RestEntity> createTodo(RestEntity todo) {
        RestEntity savedTodo = todoRepository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTodo);
    }

    public ResponseEntity<RestEntity> updateTodo(Long id, RestEntity todo) {
        Optional<RestEntity> existingTodo = todoRepository.findById(id);
        if (existingTodo.isPresent()) {
            todo.setId(id); // Update the entity with the correct ID
            RestEntity updatedTodo = todoRepository.save(todo);
            return ResponseEntity.ok(updatedTodo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<Void> deleteTodoById(Long id) {
        if (todoRepository.findById(id).isPresent()) {
            todoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
