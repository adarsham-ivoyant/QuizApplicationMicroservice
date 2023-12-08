package com.ivoyant.quizservice.service;


import com.ivoyant.quizservice.dao.QuizDao;
import com.ivoyant.quizservice.entity.QuestionWrapper;
import com.ivoyant.quizservice.entity.Quiz;
import com.ivoyant.quizservice.entity.Responsee;
import com.ivoyant.quizservice.feign.QuizInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    public QuizDao quizDao;

    @Autowired
    public QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions=quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsId(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz= quizDao.findById(id).get();
        List<Integer> questionsIds=quiz.getQuestionsId();

        ResponseEntity<List<QuestionWrapper>> questions=quizInterface.getQuestionsFromId(questionsIds);

        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Responsee> responses) {
           ResponseEntity<Integer> score= quizInterface.getScore(responses);
           return score;
    }
}
