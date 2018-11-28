package com.micropole.sxwine.module.personal.Bean;

/**
 * Created by JohnnyH on 2018/6/12.
 */

public class FQAEntity {

    /**
     * question_id : 1
     * question : 为什么无法分享二维码
     * answer : 因为你充的钱不够
     */

    private String question_id;
    private String question;
    private String answer;

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
