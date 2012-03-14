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
public class MultipleChoiceQuestion extends Question implements Serializable, Comparable<Question> {

    public String text;
    public int id;
    public LinkedList<String> userAnswer = new LinkedList<String>();
    public LinkedList<String> possibleAnswers = new LinkedList<String>();
    public LinkedList<String> correctAnswers = new LinkedList<String>();
    public QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
    private LinkedList<URL> urls = new LinkedList<URL>();
    private boolean isFirst = false;
    private boolean isLast = false;
    private boolean isOneChoice = false;
    private boolean isMultipleChoice = true;
    private boolean isOpen = false;
    private int number;

   
    @Override
    public String getUserAnswersString() {
        StringBuilder sb= new StringBuilder();
        for (String s : userAnswer){
            sb.append(s);
            sb.append(", ");
        }
        return sb.toString();
    }

    
    
    
    public boolean isIsMultipleChoice() {
        return isMultipleChoice;
    }

    public void setIsMultipleChoice(boolean isMultipleChoice) {
        this.isMultipleChoice = isMultipleChoice;
    }

    public boolean isIsOneChoice() {
        return isOneChoice;
    }

    public void setIsOneChoice(boolean isOneChoice) {
        this.isOneChoice = isOneChoice;
    }

    public boolean isIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isIsFirst() {
        return isFirst;
    }

    public boolean isIsLast() {
        return isLast;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public MultipleChoiceQuestion(int id, String text) {
        this.text = text;
        this.id = id;
    }

    public MultipleChoiceQuestion(String name, int id, LinkedList<String> possibleAnswers, LinkedList<String> correctAnswers) {
        this.text = name;
        this.id = id;
        this.possibleAnswers = possibleAnswers;
        this.correctAnswers = correctAnswers;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * Don't use this!
     *
     * @param userAnswer
     * @return always false
     */
    @Override
    public void setUserAnswer(String userAnswer) {
    }

    @Override
    public void setUserAnswers(LinkedList<String> userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }

    @Override
    public LinkedList<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    @Override
    public void setPossibleAnswers(LinkedList<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    @Override
    public boolean addPossibleAnswser(String possibleAnswer) {
        possibleAnswers.add(possibleAnswer);
        return true;
    }

    @Override
    public void setCorrectAnswer(String correctAnswer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getCorrectAnswer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean setCorrectAnswers(LinkedList<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
        return true;
    }

    @Override
    public LinkedList<String> getCorrectAnswers() {
        return correctAnswers;
    }

    @Override
    public LinkedList<URL> getURLS() {
        return urls;
    }

    @Override
    public boolean setURLS(LinkedList<URL> urls) {
        this.urls = urls;
        return true;
    }

    @Override
    public void addURL(URL url) {
        urls.add(url);
    }

    @Override
    public String getUserAnswer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LinkedList<String> getUserAnswers() {
        return userAnswer;
    }

    @Override
    public boolean isFirst() {
        return isFirst;
    }

    @Override
    public boolean isLast() {
        return isLast;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
       this.number=number;
    }

    @Override
    public void setAnswerId(int aInt) {
//         this.answer_id=aInt;
    }
    
    @Override
    public int compareTo(Question t) {
        if (t.getNumber()>this.number)
            return -1;
        if (t.getNumber()==this.number)
            return 0;
        return 1;
    }

    @Override
    public boolean isOpenQuestion() {
        return isOpen;
    }

    @Override
    public boolean isMultipleChoiceQuestion() {
        return isMultipleChoice;
    }

    public boolean isOneChoiceQuestion() {
        return isOneChoice;
    }

    @Override
    public int getAnswerId() {
        return 0;
    }
}
