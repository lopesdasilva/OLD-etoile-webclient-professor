/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import etoile.javaapi.question.OpenQuestion;
import etoile.javaapi.question.Question;
import java.util.LinkedList;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author lopesdasilva
 */


public class testManager {
    
    private LinkedList<Question> questionsToAdd = new LinkedList<Question>();

    private String newQuestionText;

    
    public testManager(){
        questionsToAdd.add(new OpenQuestion(999,"test Question 1"));
         questionsToAdd.add(new OpenQuestion(999,"test Question 2"));
    }
    
    
    public String getNewQuestionText() {
        return newQuestionText;
    }

    public void setNewQuestionText(String newQuestionText) {
        this.newQuestionText = newQuestionText;
    }
    
    
    
    public LinkedList<Question> getQuestionsToAdd() {
        return questionsToAdd;
    }

    public void setQuestionsToAdd(LinkedList<Question> questionsToAdd) {
        this.questionsToAdd = questionsToAdd;
    }
    
    
    public void addQuestion(){
        System.out.println("adding question");
    }
}
