package database;

//Data model to represent data from the Answer table
public class Answer {

    private int answer_Id;
    private int question_Id;

    private String answerText;
    private boolean isCorrect;

    //Constructor with Parameters
    public Answer(int answer_Id, int question_Id, String answerText, boolean isCorrect) {
        this.answer_Id = answer_Id;
        this.question_Id = question_Id;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    //Getter
    public int getAnswer_Id() {
        return answer_Id;
    }

    public int getQuestion_Id() {
        return question_Id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
