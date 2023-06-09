package com.srinu.example.api;

import com.srinu.example.entity.Result;
import com.srinu.example.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v0/result")
public class ResultApi {
    @Autowired
    ResultService resultService;

    @PostMapping
    public ResponseEntity<Result> create(@RequestBody Result result) {
        return ResponseEntity.ok(resultService.create(result));
    }

    @PutMapping
    public ResponseEntity<Result> update(@RequestParam(name = "satScore") Integer score, @RequestParam(name = "name") String name) {
        return ResponseEntity.ok(resultService.update(name, score));
    }

    @GetMapping
    public ResponseEntity<List<Result>> getAll() {
        return ResponseEntity.ok(resultService.getAll());
    }

    @GetMapping(value = "/rank")
    public ResponseEntity<Integer> getRank(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(resultService.getRank(name));
    }

    @DeleteMapping
    public void deleteResult(@RequestParam("name") String name) {
        resultService.delete(name);
    }
}
