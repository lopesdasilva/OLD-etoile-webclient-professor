/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package etoile.javaapi.question;

import java.io.Serializable;
import java.util.LinkedList;


/**
 *
 * @author Rui
 */
public abstract class Question implements Serializable, Comparable<Question> {
    
    public abstract void addURL(URL url);
    
    public abstract LinkedList<URL> getURLS();
    
    public abstract boolean setURLS(LinkedList<URL> urls);
    
    public abstract void setCorrectAnswer(String correctAnswer);
   
    public abstract boolean setCorrectAnswers(LinkedList<String> correctAnswers);
    
    public abstract String getCorrectAnswer();
    
    public abstract QuestionType getQuestionType();
    
    public abstract String getText();
    
    public abstract int getId();
    
    public abstract String getUserAnswer();
    
    public abstract LinkedList<String> getUserAnswers();
       
    public abstract void setUserAnswer(String userAnswer);
    
    public abstract void setUserAnswers(LinkedList<String> userAnswer);
    
    public abstract LinkedList<String> getPossibleAnswers();
   
    public abstract LinkedList<String> getCorrectAnswers();
    
    public abstract void setPossibleAnswers(LinkedList<String> possibleAnswers);
    
    public abstract boolean addPossibleAnswser(String possibleAnswer);
    
    public abstract boolean isFirst();
    
    public abstract boolean isLast();
    
    public abstract int getNumber();
    
    public abstract void setNumber(int number);

    public abstract  void setAnswerId(int aInt) ;
    
    public abstract int getAnswerId();
    
    public abstract boolean isOpenQuestion();
    
    public abstract boolean isMultipleChoiceQuestion();
    
    public abstract boolean isOneChoiceQuestion();
    public abstract String getUserAnswersString();
}
