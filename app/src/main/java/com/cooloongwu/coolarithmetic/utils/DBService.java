package com.cooloongwu.coolarithmetic.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cooloongwu.coolarithmetic.base.AppConfig;
import com.cooloongwu.coolarithmetic.entity.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * 连接数据库
 * Created by LGL on 2016/6/4.
 */
public class DBService {

    private SQLiteDatabase db;

    //构造方法
    public DBService() {
        //连接数据库
        db = SQLiteDatabase.openDatabase("/data/data/com.cooloongwu.coolarithmetic/databases/" + AppConfig.questionsDB,
                null,
                SQLiteDatabase.OPEN_READWRITE
        );
    }

    //获取数据库的数据
    public List<Question> getQuestion() {
        List<Question> list = new ArrayList<>();
        //执行sql语句
        Cursor cursor = db.rawQuery("select * from questions", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();
            //遍历
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                Question question = new Question();
                //ID
                question.setId(cursor.getLong(cursor.getColumnIndex("id")));
                //问题
                question.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
                question.setQuestionId(cursor.getInt(cursor.getColumnIndex("questionId")));

                question.setGrade(cursor.getInt(cursor.getColumnIndex("grade")));
                question.setAdvance(cursor.getInt(cursor.getColumnIndex("advance")));
                //四个选择
                question.setAnswerA(cursor.getString(cursor.getColumnIndex("answerA")));
                question.setAnswerB(cursor.getString(cursor.getColumnIndex("answerB")));
                question.setAnswerC(cursor.getString(cursor.getColumnIndex("answerC")));
                question.setAnswerD(cursor.getString(cursor.getColumnIndex("answerD")));
                //答案
                question.setAnswer(cursor.getInt(cursor.getColumnIndex("answer")));
                //解析
                question.setExplanation(cursor.getString(cursor.getColumnIndex("explanation")));
                //设置为没有选择任何选项
                list.add(question);
            }
        }
        cursor.close();
        db.close();
        return list;

    }

}
