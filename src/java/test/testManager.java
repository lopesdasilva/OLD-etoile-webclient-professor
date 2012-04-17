/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import etoile.javaapi.question.MultipleChoiceQuestion;
import etoile.javaapi.question.OneChoiceQuestion;
import etoile.javaapi.question.OpenQuestion;
import etoile.javaapi.question.Question;
import etoile.javapi.professor.ServiceManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author lopesdasilva
 */
public class testManager {

    private LinkedList<Question> questionsToAdd = new LinkedList<Question>();
    private String questionsToAddSelection;
    private ServiceManager manager;
    private String testName;
    private String testDescription;
    private int selectedModule;
    private Date startDate;
    private Date finishDate;
    private String newQuestionText;
    private String newOneChoiceHypothesisText;
    private String newOneChoiceQuestionText;
    private LinkedList<String> oneChoicehypothesis = new LinkedList<String>();
    private String newMultipleChoiceHypothesisText;
    private String newMultipleChoiceQuestionText;
    private LinkedList<String> multipleChoicehypothesis = new LinkedList<String>();
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getNewMultipleChoiceHypothesisText() {
        return newMultipleChoiceHypothesisText;
    }

    public void setNewMultipleChoiceHypothesisText(String newMultipleChoiceHypothesisText) {
        this.newMultipleChoiceHypothesisText = newMultipleChoiceHypothesisText;
    }

    public String getNewMultipleChoiceQuestionText() {
        return newMultipleChoiceQuestionText;
    }

    public void setNewMultipleChoiceQuestionText(String newMultipleChoiceQuestionText) {
        this.newMultipleChoiceQuestionText = newMultipleChoiceQuestionText;
    }

    public LinkedList<String> getMultipleChoicehypothesis() {
        return multipleChoicehypothesis;
    }

    public void setMultipleChoicehypothesis(LinkedList<String> multipleChoicehypothesis) {
        this.multipleChoicehypothesis = multipleChoicehypothesis;
    }

    public String getNewOneChoiceHypothesisText() {
        return newOneChoiceHypothesisText;
    }

    public void setNewOneChoiceHypothesisText(String newOneChoiceHypothesisText) {
        this.newOneChoiceHypothesisText = newOneChoiceHypothesisText;
    }

    public LinkedList<String> getOneChoicehypothesis() {
        return oneChoicehypothesis;
    }

    public void setOneChoicehypothesis(LinkedList<String> oneChoicehypothesis) {
        this.oneChoicehypothesis = oneChoicehypothesis;
    }

    public String getNewOneChoiceQuestionText() {
        return newOneChoiceQuestionText;
    }

    public void setNewOneChoiceQuestionText(String newOneChoiceQuestionText) {
        this.newOneChoiceQuestionText = newOneChoiceQuestionText;
    }

    public Date getFinish() {
        return finishDate;
    }

    public void setFinish(Date finish) {
        this.finishDate = finish;
    }

    public int getSelectedModule() {
        return selectedModule;
    }

    public void setSelectedModule(int selectedModule) {
        this.selectedModule = selectedModule;
    }

    public Date getStart() {
        return startDate;
    }

    public void setStart(Date start) {
        this.startDate = start;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getQuestionsToAddSelection() {
        return questionsToAddSelection;
    }

    public void setQuestionsToAddSelection(String questionsToAddSelection) {
        this.questionsToAddSelection = questionsToAddSelection;
    }

    public testManager(ServiceManager manager) {

        this.manager = manager;
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

    public String addQuestion() {
        System.out.println("Selected Module:" + selectedModule);
        System.out.println("adding question text:" + newQuestionText);
        questionsToAdd.add(new OpenQuestion(0, newQuestionText));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Question Added"));

        newQuestionText="";
        return "success";
    }

    public String addOneChoiceQuestion() {
        System.out.println("Adding One Choice Question");
        questionsToAdd.add(new OneChoiceQuestion(newOneChoiceQuestionText, 0, oneChoicehypothesis, ""));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Question Added"));
        
    newOneChoiceHypothesisText="";
    newOneChoiceQuestionText="";
    oneChoicehypothesis = new LinkedList<String>();
        return "success";
    }

    public String addMultipleChoiceQuestion() {
        System.out.println("Adding Multiple Choice Question");
        questionsToAdd.add(new MultipleChoiceQuestion(newMultipleChoiceQuestionText, 0, multipleChoicehypothesis, multipleChoicehypothesis));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Question Added"));
   
    newMultipleChoiceHypothesisText="";
    newMultipleChoiceQuestionText="";
    multipleChoicehypothesis = new LinkedList<String>();
        
        return "success";
    }

    public void removeQuestion() {
        System.out.println("Removing Selected Questions");
        for (Question q : questionsToAdd) {
            if (q.getText().equals(questionsToAddSelection)) {
                questionsToAdd.remove(q);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Question Removed"));
    }
    
       
    
    public String submitTest() {
        try {
            System.out.println("Submiting test");
            int testid = manager.userService().addTest(testName, "Teacher", new java.sql.Date(startDate.getTime()), new java.sql.Date(finishDate.getTime()), testDescription, selectedModule,url);
            int questionNumber = 0;
            for (Question q : questionsToAdd) {

                questionNumber++;
                if (q.isOpenQuestion()) {
                    manager.userService().addOpenQuestion(q.getText(), testid, questionNumber);
                }
                if (q.isOneChoiceQuestion()) {
                    manager.userService().addOneChoiceQuestion(q, testid, questionNumber);
                }
                if (q.isMultipleChoiceQuestion()) {
                    manager.userService().addMultipleChoiceQuestion(q, testid, questionNumber);
                }
            }
            clearTest();
            

        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fail to add Test"));
            Logger.getLogger(testManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Test Added"));
        return "addTest";
    }

    public void addOneChoiceHypothesis() {
        System.out.println("adding hypothesis");
        oneChoicehypothesis.add(newOneChoiceHypothesisText);

    }

    public void addMultipleChoiceHypothesis() {
        System.out.println("adding hypothesis");
        multipleChoicehypothesis.add(newMultipleChoiceHypothesisText);
    }

    private void clearTest() {
    questionsToAdd = new LinkedList<Question>();
    questionsToAddSelection="";
    testName="";
    testDescription="";
    startDate=null;
    finishDate=null;
    newQuestionText="";
    newOneChoiceHypothesisText="";
    newOneChoiceQuestionText="";
    oneChoicehypothesis = new LinkedList<String>();
    newMultipleChoiceHypothesisText="";
    newMultipleChoiceQuestionText="";
    multipleChoicehypothesis = new LinkedList<String>();
    url="";
    }
}
