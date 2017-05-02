package com.zxxxy.coolarithmetic.entity;

import java.io.Serializable;

/**
 * 问题的实体类
 */
public class Question implements Serializable {

    //编号
    //@Property(nameInDb = "Field1")
    private Long id;

    //年级
    private int grade;

    //闯关
    private int advance;

    //闯关
    private int questionId;

    //问题
    private String question;

    //如果questionType是图片“image”，那么question就是图片的地址。
    //如果questionType是文字“text”，那么question就是相应的文字。
    private String questionType;

    //四个选项
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;

    //答案
    private int answer;

    //详情
    private String explanation;

    //详情
    private String selectedAnswer;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getAdvance() {
        return advance;
    }

    public void setAdvance(int advance) {
        this.advance = advance;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
