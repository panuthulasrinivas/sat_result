package com.srinu.example.service;

import com.srinu.example.entity.Result;
import com.srinu.example.exception.ResultException;
import com.srinu.example.repository.ResultRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {
    @Value("${result.max.marks}")
    Integer maxMarks;
    @Autowired
    private ResultRepository resultRepository;

    @Transactional
    public Result create(Result result) {
        if (result.getSatScore() > maxMarks) {
            throw new ResultException(HttpStatus.BAD_REQUEST, String.format("SAT score %s is greater than max score %s", result.getSatScore(), maxMarks));
        }
        Optional<Result> dbResult = resultRepository.findById(result.getName());
        if (dbResult.isPresent()) {
            throw new ResultException(HttpStatus.BAD_REQUEST, String.format("Result already exist with the name %s", result.getName()));
        }
        double percentage = (result.getSatScore() * 100) / maxMarks;
        if (percentage >= 30) {
            result.setResultStatus(Result.ResultStatus.PASS);
        } else {
            result.setResultStatus(Result.ResultStatus.FAIL);
        }
        return resultRepository.save(result);
    }

    @Transactional
    public Result update(String name, Integer score) {
        if (score > maxMarks) {
            throw new ResultException(HttpStatus.BAD_REQUEST, String.format("SAT score %s is greater than max score %s", score, maxMarks));
        }
        Optional<Result> dbResultOptional = resultRepository.findById(name);
        if (dbResultOptional.isEmpty()) {
            throw new ResultException(HttpStatus.BAD_REQUEST, String.format("Result does not exist with the name %s", name));
        }
        Result dbResult = dbResultOptional.get();
        dbResult.setSatScore(score);
        double percentage = (score * 100) / maxMarks;
        if (percentage >= 30) {
            dbResult.setResultStatus(Result.ResultStatus.PASS);
        } else {
            dbResult.setResultStatus(Result.ResultStatus.FAIL);
        }
        return resultRepository.save(dbResult);
    }

    public List<Result> getAll() {
        return resultRepository.findAll();
    }

    @Transactional
    public void delete(String name) {
        resultRepository.deleteById(name);
    }

    public int getRank(String name) {
        Optional<Result> result = resultRepository.findById(name);
        if (result.isPresent()) {
            return resultRepository.getRank(result.get().getSatScore());
        }
        return 0;
    }

}
