package com.cooloongwu.coolarithmetic.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 年级的实体类
 * Created by CooLoongWu on 2017-3-31 14:09.
 */

@Entity
public class Question {
    @Id
    private Long id;
    private String question;  //具体的问题

    private int grade;          //年级
    private int advance;        //关卡
    private int questionId;     //每个关卡的ID

    //四个选项
    public String answerA;
    public String answerB;
    public String answerC;
    public String answerD;

    //正确答案
    public String answer;

    //答案解析
    public String explaination;

    //用户选中的答案
    public int selectedAnswer;

    @Generated(hash = 1686890386)
    public Question(Long id, String question, int grade, int advance,
                    int questionId, String answerA, String answerB, String answerC,
                    String answerD, String answer, String explaination,
                    int selectedAnswer) {
        this.id = id;
        this.question = question;
        this.grade = grade;
        this.advance = advance;
        this.questionId = questionId;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.answer = answer;
        this.explaination = explaination;
        this.selectedAnswer = selectedAnswer;
    }

    @Generated(hash = 1868476517)
    public Question() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getGrade() {
        return this.grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getAdvance() {
        return this.advance;
    }

    public void setAdvance(int advance) {
        this.advance = advance;
    }

    public int getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswerA() {
        return this.answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return this.answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return this.answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return this.answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplaination() {
        return this.explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    public int getSelectedAnswer() {
        return this.selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

}
