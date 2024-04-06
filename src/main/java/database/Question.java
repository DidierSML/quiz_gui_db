package database;

import java.util.ArrayList;

//Data model to represent data from the Question table
public class Question {

    private int question_Id;
    private int category_Id;
    private String questionText;

    private ArrayList <Answer> answers;

    //Constructor
    public Question(int question_Id, int category_Id, String questionText) {
        this.question_Id = question_Id;
        this.category_Id = category_Id;
        this.questionText = questionText;
    }

    //Getters
    public int getQuestion_Id() {
        return question_Id;
    }

    public int getCategory_Id() {
        return category_Id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    //Setter Answers
    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }


}
