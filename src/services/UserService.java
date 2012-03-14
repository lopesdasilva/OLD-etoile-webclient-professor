/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import db.DBConnect;
import db.SQLInstruct;
import etoile.javaapi.question.*;
import etoile.javapi.professor.Discipline;
import etoile.javapi.professor.Module;
import etoile.javapi.professor.Professor;
import etoile.javapi.professor.Test;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Ruben
 */
public class UserService implements Serializable{

    DBConnect db;
    Professor current_student;

    public UserService(DBConnect db, Professor current_student) {
        this.db = db;
        this.current_student = current_student;
    }


    public void addProfessor(Professor student) throws SQLException {
        String sqlStatement = SQLInstruct.addStudent(student.getUsername(), student.getPassword(), student.getFirstname(), student.getLastname(), student.getEmail());
        db.updateDB(sqlStatement);
        System.out.println("SELECT id FROM student WHERE student.username = "+student.getUsername()+"");
        ResultSet rSet = db.queryDB("SELECT id FROM student WHERE student.username = '"+student.getUsername()+"'");
        while(rSet.next()){
        String statmentAddCourse = SQLInstruct.registerStudentCourse(rSet.getInt(1),1);
        db.updateDB(statmentAddCourse);
        }
        
    }

    
    //TODO: FIX ME!
    public void updateDisciplines(int professor_id) throws SQLException {
//        String sqlStatement = SQLInstruct.getDisciplines(course.getId());
//        ResultSet rSet = db.queryDB(sqlStatement);
//
//        while (rSet.next()) {
//            Discipline d = new Discipline(rSet.getInt(1), rSet.getString(2));
//            course.addDiscipline(d);
//            updateModules(d);
//        }
    }

    public void updateModules(Discipline discipline) throws SQLException {
        String sqlStatement = SQLInstruct.getModules(discipline.getId());
        ResultSet rSet = db.queryDB(sqlStatement);

        while (rSet.next()) {
            discipline.addModule(new Module(rSet.getInt(1), rSet.getString(2)));
        }
    }

    public void updateTests(Module module) throws SQLException {
        
        module.setTests(new LinkedList<Test>()); //to restart tests No repeats
        
        String sqlStatement = SQLInstruct.getTests(module.getId());
        ResultSet rSet = db.queryDB(sqlStatement);

        while (rSet.next()) {


            module.addTest(new Test(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getDate(5), rSet.getDate(6)));
        }
        
    }

    public void updateQuestions(Test test) throws SQLException {
        
        LinkedList<Question> openquestions = getOpenQuestions(test);
        getOpenQuestionURLs(openquestions);
        LinkedList<Question> onechoicequestions =getOneChoiceQuestions(test);
        getOneChoiceQuestionURLs(onechoicequestions);
        LinkedList<Question> multiplechoicequestions = getMultipleChoiceQuestions(test);
        getMultipleChoiceQuestionURLs(multiplechoicequestions);
        Collections.sort(test.questions);

    }

    private LinkedList<Question> getOpenQuestions(Test test) throws SQLException {
        //OPENQUESTIONS
        String sqlStatement = SQLInstruct.getOpenQuestions(test.getId());
        ResultSet rSet = db.queryDB(sqlStatement);
        LinkedList<Question> questions = new LinkedList<Question>();
        while (rSet.next()) {
            Question q = new OpenQuestion(rSet.getInt(1), rSet.getString(2));
            q.setNumber(rSet.getInt(3));
            test.addQuestion(q);
            questions.add(q);
            String sqlStatement_correct = SQLInstruct.getOpenQuestionAnswer(rSet.getInt(1), current_student.getId());
            ResultSet rSet_answer = db.queryDB(sqlStatement_correct);
            if (rSet_answer.next()) {
                q.setUserAnswer(rSet_answer.getString(2));
                q.setAnswerId(rSet_answer.getInt(1));
            }else {
                String sqlStatementAddAnswer = SQLInstruct.insertOpenQuestionAnswer(current_student.getId(), q.getId(), "No Answer.");
                db.updateDB(sqlStatementAddAnswer);
                q.setUserAnswer("No Answer.");
            }

        }
        return questions;
    }

    private LinkedList<Question> getOneChoiceQuestions(Test test) throws SQLException {
        String sqlStatement = SQLInstruct.getOneChoiceQuestions(test.getId());
        ResultSet rSet = db.queryDB(sqlStatement);
        LinkedList<Question> questions = new LinkedList<Question>();
        while (rSet.next()) {
            OneChoiceQuestion op = new OneChoiceQuestion(rSet.getInt(1), rSet.getString(2));
            op.setNumber(rSet.getInt(3));
            test.addQuestion(op);
            questions.add(op);
            //Hypothesis
            String sqlStatement_hypothesis = SQLInstruct.getOneChoiceHypothesis(rSet.getInt(1));
            ResultSet rSet_hypothesis = db.queryDB(sqlStatement_hypothesis);
            while (rSet_hypothesis.next()) {
                op.addPossibleAnswser(rSet_hypothesis.getString(2));
                if (rSet_hypothesis.getInt(3) == 1) {
                    op.setCorrectAnswer(rSet_hypothesis.getString(2));
                }
            }


            String sqlStatement_useranswer = SQLInstruct.getOneChoiceAnswer(rSet.getInt(1), current_student.getId());
            ResultSet rSet_answer = db.queryDB(sqlStatement_useranswer);
            if (rSet_answer.next()) {
                op.setAnswerId(rSet_answer.getInt(1));
                op.setUserAnswer(rSet_answer.getString(2));
            }else{
                String sqlStatementAddAnswer = SQLInstruct.insertOneChoiceQuestionAnswer(current_student.getId(),op.getId());
                db.updateDB(sqlStatementAddAnswer);
                op.setUserAnswer("No Answer.");
            }

        }
        
        return questions;
    }

        public LinkedList<Question> getMultipleChoiceQuestions(Test test) throws SQLException {
        String sqlStatement = SQLInstruct.getMultipleChoiceQuestions(test.getId());
        ResultSet rSet = db.queryDB(sqlStatement);
        LinkedList<Question> questions = new LinkedList<Question>();
        while (rSet.next()) {
            MultipleChoiceQuestion mp = new MultipleChoiceQuestion(rSet.getInt(1), rSet.getString(2));
            mp.setNumber(rSet.getInt(3));
            test.addQuestion(mp);
            questions.add(mp);
            //Hypothesis
            String sqlStatement_hypothesis = SQLInstruct.getMultipleChoiceHypothesis(rSet.getInt(1));
            ResultSet rSet_hypothesis = db.queryDB(sqlStatement_hypothesis);
            LinkedList<String> correct = new LinkedList<String>();
            while (rSet_hypothesis.next()) {
                mp.addPossibleAnswser(rSet_hypothesis.getString(2));
                if (rSet_hypothesis.getInt(3) == 1) {
                    correct.add(rSet_hypothesis.getString(2));
                }
            }
            mp.setCorrectAnswers(correct);
            String sqlStatement_useranswer= SQLInstruct.getMultipleChoiceAnswer(rSet.getInt(1), current_student.getId());
            ResultSet rSet_answer = db.queryDB(sqlStatement_useranswer);
            LinkedList<String> answers = new LinkedList<String>();
            while(rSet_answer.next()){
            answers.add(rSet_answer.getString(2));

            }
            mp.setUserAnswers(answers);
        }
        
        return questions;
    }
    
     private void getOpenQuestionURLs(LinkedList<Question> questions) throws SQLException {
      for(Question q : questions){
          String sqlStatement = SQLInstruct.getOpenQuestionURLs(q.getId());
          ResultSet rSet = db.queryDB(sqlStatement);
          while(rSet.next()){
              q.addURL(new URL(rSet.getInt(1),rSet.getString(2),rSet.getString(3),"noname",rSet.getInt(4)));
          }
      }
    }
     
    private void getOneChoiceQuestionURLs(LinkedList<Question> questions) throws SQLException {
        for(Question q : questions){
          String sqlStatement = SQLInstruct.getOneChoiceURLs(q.getId());
          ResultSet rSet = db.queryDB(sqlStatement);
          while(rSet.next()){
              q.addURL(new URL(rSet.getInt(1),rSet.getString(2),rSet.getString(3),"noname",rSet.getInt(4)));
          }
      }
    } 
    
     private void getMultipleChoiceQuestionURLs(LinkedList<Question> questions) throws SQLException {
       for(Question q : questions){
          String sqlStatement = SQLInstruct.getMultipleChoiceURLs(q.getId());
          ResultSet rSet = db.queryDB(sqlStatement);
          while(rSet.next()){
              q.addURL(new URL(rSet.getInt(1),rSet.getString(2),rSet.getString(3),"noname",rSet.getInt(4)));
          }
      } 
    } 
     
     //TODO: FIX ME!
     public Discipline getDiscipline(String name){
       
        for(Course c : current_student.getCourses()){
            for(Discipline d: c.getDisciplines()){
                if(d.getName().equals(name))
                    return d;
            }
        }
    return null;
    }

     
     //TODO: FIX ME!
    public Module getModule(String value) {
        //TODO Cant exist modules with the same name
        for(Course c: current_student.getCourses()){
            for(Discipline d: c.getDisciplines()){
               for(Module m: d.getModules())
                   if(m.getName().equals(value))
                       return m;
            }
        }
        return null;
    }

    

    //UPDATE QUESTIONS
    
    public void updateOpenAnswer(int answer_id, String openanswer) throws SQLException{
        // answer_id este valor é obtido após ser feito um get na Question.getAnswerId();
        
        String sqlStatement = SQLInstruct.updateOpenQuetionAnswer(answer_id, openanswer);
        db.updateDB(sqlStatement);
        
    }
    
    public void updateOneChoiceAnswer(int answer_id,String answer) throws SQLException{
        String sqlStatement = SQLInstruct.updateOneChoiceAnswer(answer_id,answer);
        db.updateDB(sqlStatement);
    }
    
    public void updateMultipleChoiceAnswer(int question_id, LinkedList<String> userAnswers) throws SQLException{
            String sqlStatement_useranswer= SQLInstruct.getMultipleChoiceAnswer(question_id, current_student.getId());
            ResultSet rSet_answer = db.queryDB(sqlStatement_useranswer);
            //remover respostas anteriores
            while(rSet_answer.next()){
             String SqlStatement_removeAnswer = "DELETE FROM multiplechoiceanswer WHERE id='"+rSet_answer.getInt(1)+"'";
             db.updateDB(SqlStatement_removeAnswer);
            }
            //adicionar respostas actuais
            for(String answer: userAnswers){
                String sqlStatement = SQLInstruct.insertMultipleChoiceAnswer(question_id,current_student.getId(),answer);
                db.updateDB(sqlStatement);
            }
            
    }
    
    public void vote(int url_id) throws SQLException{
        String sqlStatement = SQLInstruct.vote(url_id);
        db.updateDB(sqlStatement);
    }
    
    public void addURL(String url_name, String url , QuestionType type, int question_id ) throws SQLException{
        
        String sqlStatement = SQLInstruct.addUrl(url_name, url);
        db.updateDB(sqlStatement);
                String sqlGetUrl = SQLInstruct.getLastURLInserted();
        ResultSet rSet=db.queryDB(sqlGetUrl);
                if(rSet.next()){
        if (type==QuestionType.OPEN){ // adiciona À openquestion


            String sqlLinkURLOpenQuestion = SQLInstruct.linkURLOpenQuestion(question_id, rSet.getInt(1));
            System.out.println("OPEN: "+sqlLinkURLOpenQuestion);

            db.updateDB(sqlLinkURLOpenQuestion);
         
        }else if(type==QuestionType.ONE_CHOICE){ // adiciona À onechoicequestion

            String sqlLinkURLOneChoiceQuestion = SQLInstruct.linkURLOneChoiceQuestion(question_id, rSet.getInt(1));
            System.out.println("ONECHOICE: "+sqlLinkURLOneChoiceQuestion);
            db.updateDB(sqlLinkURLOneChoiceQuestion);
          
            
        }else{//adiciona à multiplechoicequestion

            String sqlLinkURLMultipleChoiceQuestion = SQLInstruct.linkURLMultipleChoiceQuestion(question_id, rSet.getInt(1));
            System.out.println("MULTIPLE: "+sqlLinkURLMultipleChoiceQuestion);

            db.updateDB(sqlLinkURLMultipleChoiceQuestion);
        
    }
    
                }
    }
   
}
