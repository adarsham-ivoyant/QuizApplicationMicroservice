package com.ivoyant.questionservice.controller;


import com.ivoyant.questionservice.entity.Question;
import com.ivoyant.questionservice.entity.QuestionWrapper;
import com.ivoyant.questionservice.entity.Responsee;
import com.ivoyant.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionCountroller {

    @Autowired
    private QuestionService questionService;
    @GetMapping("allQuestions")
    public List<Question> getAllQuestions(){
        return questionService.getAllGuestion();
    }

    @GetMapping("category/{cat}")
    public List<Question> getQuestionByCategory(@PathVariable("cat") String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return  questionService.addQuestion(question);
    }

    @GetMapping("getQuestionById/{id}")
    public Question getQuestionById(@PathVariable("id") int id) {
        return questionService.getQuestionById(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") Integer id){
        return questionService.deleteQuestions(id);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,
                                                             @RequestParam Integer numQuestions){
        return questionService.getQuestionsForQuiz(categoryName, numQuestions);
    }

    @PostMapping("getQuestion")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionId){
        return questionService.getQuestionsFromId(questionId);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Responsee> responsees){
        return questionService.getScore(responsees);
    }
}
