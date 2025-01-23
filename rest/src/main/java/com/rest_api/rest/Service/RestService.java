package com.rest_api.rest.Service;

import com.rest_api.rest.Entity.RestEntity;
import com.rest_api.rest.Repository.RestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestService{

    @Autowired
    private RestRepository todoRepository;

    public List<RestEntity> findAll() {
        return todoRepository.findAll();
    }

    public Optional<RestEntity> findById(Long id) {
        return todoRepository.findById(id);
    }

    public RestEntity save(RestEntity todo) {
        return todoRepository.save(todo);
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }
}
