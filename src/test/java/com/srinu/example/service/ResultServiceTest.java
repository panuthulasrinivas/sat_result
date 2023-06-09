package com.srinu.example.service;

import com.srinu.example.entity.Result;
import com.srinu.example.exception.ResultException;
import com.srinu.example.repository.ResultRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.module.ResolutionException;
import java.util.Optional;

public class ResultServiceTest {
    ResultService resultService;

    Integer maxMarks = 100;
    ResultRepository resultRepository = Mockito.mock(ResultRepository.class);

    @BeforeEach
    public void setUp() {
        resultService = new ResultService();
        ReflectionTestUtils.setField(resultService, "maxMarks", maxMarks);
        ReflectionTestUtils.setField(resultService, "resultRepository", resultRepository);
    }


    @Test
    public void testCreate() {
        Result result = new Result();
        result.setName("srinu");
        result.setSatScore(50);
        Mockito.when(resultRepository.findById(result.getName())).thenReturn(Optional.empty());
        Mockito.when(resultRepository.save(result)).thenReturn(result);
        Result dbResult = resultService.create(result);
        Assertions.assertEquals(result, dbResult);
        Assertions.assertEquals(dbResult.getResultStatus(), Result.ResultStatus.PASS);
    }

    @Test
    public void testCreateFail() {
        Result result = new Result();
        result.setName("srinu");
        result.setSatScore(20);
        Mockito.when(resultRepository.findById(result.getName())).thenReturn(Optional.empty());
        Mockito.when(resultRepository.save(result)).thenReturn(result);
        Result dbResult = resultService.create(result);
        Assertions.assertEquals(result, dbResult);
        Assertions.assertEquals(dbResult.getResultStatus(), Result.ResultStatus.FAIL);
    }

    @Test
    public void testCreateGreaterThanMaxScore() {
        Result result = new Result();
        result.setName("srinu");
        result.setSatScore(200);
        Assertions.assertThrows(ResultException.class, () -> resultService.create(result));
    }

    @Test
    public void testCreateNameAlreadyExist() {
        Result result = new Result();
        result.setName("srinu");
        result.setSatScore(80);
        Mockito.when(resultRepository.findById(result.getName())).thenReturn(Optional.of(result));
        Assertions.assertThrows(ResultException.class, () -> resultService.create(result));
    }

    @Test
    public void testUpdate() {
        Result result = new Result();
        result.setName("srinu");
        result.setSatScore(50);
        Mockito.when(resultRepository.findById(result.getName())).thenReturn(Optional.of(result));
        Mockito.when(resultRepository.save(result)).thenReturn(result);
        Result dbResult = resultService.update(result.getName(),60);
        Assertions.assertEquals(result, dbResult);
        Assertions.assertEquals(dbResult.getResultStatus(), Result.ResultStatus.PASS);
    }

    @Test
    public void testUpdateFail() {
        Result result = new Result();
        result.setName("srinu");
        result.setSatScore(50);
        Mockito.when(resultRepository.findById(result.getName())).thenReturn(Optional.of(result));
        Mockito.when(resultRepository.save(result)).thenReturn(result);
        Result dbResult = resultService.update(result.getName(),25);
        Assertions.assertEquals(result, dbResult);
        Assertions.assertEquals(dbResult.getResultStatus(), Result.ResultStatus.FAIL);
    }

    @Test
    public void testUpdateGreaterThanMaxScore() {
        Assertions.assertThrows(ResultException.class, () -> resultService.update("srinu",200));
    }

    @Test
    public void testUpdateNameAlreadyExist() {
        Mockito.when(resultRepository.findById("srinu")).thenReturn(Optional.empty());
        Assertions.assertThrows(ResultException.class, () -> resultService.update("srinu",70));
    }
}
