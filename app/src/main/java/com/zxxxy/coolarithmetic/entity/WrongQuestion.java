package com.zxxxy.coolarithmetic.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 错误问题的实体类
 */
@Entity
public class WrongQuestion {

    //编号
    @Id
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

    @Generated(hash = 1503640707)
    public WrongQuestion(Long id, int grade, int advance, int questionId,
                         String question, String questionType, String answerA, String answerB,
                         String answerC, String answerD, int answer, String explanation,
                         String selectedAnswer) {
        this.id = id;
        this.grade = grade;
        this.advance = advance;
        this.questionId = questionId;
        this.question = question;
        this.questionType = questionType;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.answer = answer;
        this.explanation = explanation;
        this.selectedAnswer = selectedAnswer;
    }

    @Generated(hash = 93514715)
    public WrongQuestion() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionType() {
        return this.questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
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

    public int getAnswer() {
        return this.answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getSelectedAnswer() {
        return this.selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

}
