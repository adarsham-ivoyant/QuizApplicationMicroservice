package com.ivoyant.questionservice.service;


import com.ivoyant.questionservice.dao.QuestionDao;
import com.ivoyant.questionservice.entity.Question;
import com.ivoyant.questionservice.entity.QuestionWrapper;
import com.ivoyant.questionservice.entity.Responsee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    private static final Logger logger= LoggerFactory.getLogger(QuestionService.class);
    public List<Question> getAllGuestion() {
        return questionDao.findAll();
    }

    public List<Question> getQuestionsByCategory(String category){
        if(questionDao.findByCategory(category).isEmpty()){
            System.out.println("Request "+category+" Not Found");
        }
        return questionDao.findByCategory(category);
    }


    public Question getQuestionById(Integer id ){
        if(questionDao.findById(id).isEmpty()){
            System.out.println("Requested "+id+" is Not found");
        }
        return questionDao.findById(id).get();

    }


    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestions(Integer id){
        questionDao.deleteById(id);
        return new ResponseEntity<>("deleted Successfully",HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName,Integer numQuestions){
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers =new ArrayList<>();
        List<Question> questions=new ArrayList<>();

        for(Integer id:questionIds){
             questions.add(questionDao.findById(id).get());
        }

        for (Question question:questions){
            QuestionWrapper wrapper=new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Responsee> responses) {
        int right = 0;

        for(Responsee response : responses){
            Question question=questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer()))
                right++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
