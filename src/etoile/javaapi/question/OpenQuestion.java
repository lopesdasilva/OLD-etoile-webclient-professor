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
public class OpenQuestion extends Question implements Serializable, Comparable<Question> {

    public String text;
    public int id;
    public QuestionType questionType = QuestionType.OPEN;
    private LinkedList<URL> urls = new LinkedList<URL>();
    private boolean isFirst = false;
    private boolean isLast = false;
    private boolean isOpen = true;
    private boolean isOneChoice = false;
    private boolean isMultipleChoice = false;
    private int number;
    
    public String userAnswer;
    private int answer_id;

    public boolean isIsMultipleChoice() {
        return isMultipleChoice;
    }

    public boolean isIsOneChoice() {
        return isOneChoice;
    }

    public void setIsMultipleChoice(boolean isMultipleChoice) {
        this.isMultipleChoice = isMultipleChoice;
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

    public OpenQuestion(int id, String text) {
        this.text = text;
        this.id = id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setUserAnswer(String userAnswser) {
        this.userAnswer = userAnswser;
    }

    public String getUserAnswer() {
        return this.userAnswer;
    }

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }

    /**
     * Don't use this!
     *
     * @return
     */
    @Override
    public LinkedList<String> getPossibleAnswers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Don't use this!
     *
     * @return
     */
    @Override
    public void setPossibleAnswers(LinkedList<String> possibleAnswers) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Don't use this!
     *
     * @return
     */
    @Override
    public boolean addPossibleAnswser(String possibleAnswer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Don't use this!
     *
     * @return
     */
    @Override
    public void setCorrectAnswer(String correctAnswer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Don't use this!
     *
     * @return
     */
    @Override
    public String getCorrectAnswer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setUserAnswers(LinkedList<String> userAnswer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean setCorrectAnswers(LinkedList<String> correctAnswers) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LinkedList<String> getCorrectAnswers() {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public LinkedList<String> getUserAnswers() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        this.answer_id = aInt;
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

    @Override
    public boolean isOneChoiceQuestion() {
        return isOneChoice;
    }

    @Override
    public int getAnswerId() {
        return answer_id;
    }

    @Override
    public String getUserAnswersString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
