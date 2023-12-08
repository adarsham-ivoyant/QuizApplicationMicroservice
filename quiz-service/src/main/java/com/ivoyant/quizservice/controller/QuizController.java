package com.ivoyant.quizservice.controller;


import com.ivoyant.quizservice.entity.QuestionWrapper;
import com.ivoyant.quizservice.entity.QuizDto;
import com.ivoyant.quizservice.entity.Responsee;
import com.ivoyant.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller for quiz
@RestController
@RequestMapping("quiz/")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String>  createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategoryName(),quizDto.getNumQuestions(), quizDto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@PathVariable Integer id){
            return quizService.getQuizQuestions(id);
    }

    @PostMapping("Submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Responsee> responses){
        return quizService.calculateResult(id,responses);
    }
}
