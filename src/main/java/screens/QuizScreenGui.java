package screens;

import constants.CommonConstants;
import database.Answer;
import database.Category;
import database.JDBC;
import database.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QuizScreenGui extends JFrame implements ActionListener {

    private JLabel scoreLabel;
    private JTextArea questionTextArea;
    private JButton [] answerButtons;
    private JButton nextButton;

    //Current Quiz category
    private Category category;

    //Question based on Category
    private ArrayList <Question> questions;
    private Question currentQuestion;
    private int currentQuestionNumber;
    private int numOfQuestions;
    private int score;
    private boolean firstChoiceMade;

    public QuizScreenGui (Category category, int numOfQuestions){
        super("Quiz Game");
        setSize(400, 565);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(CommonConstants.LIGHT_BLUE);

        answerButtons = new JButton[4];
        this.category = category;
        this.numOfQuestions = numOfQuestions;

        //Load the questions based of category;
        questions = JDBC.getQuestions(category);

        if(questions != null) {
            //Adjust number of questions to choose the min between the user´s input and
            //the total of questions in the database
            this.numOfQuestions = Math.min(numOfQuestions, questions.size());

            //Load the answers for each question
            for (Question question : questions) {
                ArrayList<Answer> answers = JDBC.getAnswers(question);
                question.setAnswers(answers);
            }

            //Load the current question
            currentQuestion = questions.get(currentQuestionNumber);
        }else {
            // Si no se encuentran preguntas para la categoría dada, muestra un mensaje de error o maneja el caso según lo deseado.
            JOptionPane.showMessageDialog(this, "No hay preguntas disponibles para esta categoría.");
        }

        addGuiComponents();

    }

    private void addGuiComponents(){

        if (currentQuestion != null) {
            //Topic Label
            JLabel topicLabel = new JLabel("Topic: " + category.getCategoryName());
            topicLabel.setFont(new Font("Arial", Font.BOLD, 16));
            topicLabel.setBounds(15, 15, 250, 16);
            topicLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
            add(topicLabel);

            //Score Label
            scoreLabel = new JLabel("Score:" + score + "/" + numOfQuestions);
            scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
            scoreLabel.setBounds(270, 15, 96, 20);
            scoreLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
            add(scoreLabel);

            //Question text area
            questionTextArea = new JTextArea(currentQuestion.getQuestionText());
            questionTextArea.setFont(new Font("Arial", Font.BOLD, 16));
            questionTextArea.setBounds(15, 50, 350, 91);
            questionTextArea.setLineWrap(true);
            questionTextArea.setWrapStyleWord(true);
            questionTextArea.setEditable(false);
            questionTextArea.setForeground(CommonConstants.BRIGHT_YELLOW);
            questionTextArea.setBackground(CommonConstants.LIGHT_BLUE);
            add(questionTextArea);

            addAnswerComponents();

        }else {
            JOptionPane.showMessageDialog(this,"No hay pregunta actual");

        }

        //Return to title
        JButton returnToTitleButton = new JButton("Return to Title");
        returnToTitleButton.setFont(new Font("Arial", Font.BOLD, 16));
        returnToTitleButton.setBounds(60,420,262,35);
        returnToTitleButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        returnToTitleButton.setForeground(CommonConstants.LIGHT_BLUE);
        returnToTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Load Title Screen
                TitleScreenGui titleScreenGui = new TitleScreenGui();
                titleScreenGui.setLocationRelativeTo(QuizScreenGui.this);

                //Dispose of this Screen
                QuizScreenGui.this.dispose();

                //Display title screen
                titleScreenGui.setVisible(true);
            }
        });
        add(returnToTitleButton);

        //Next Button
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setBounds(240,470,80,35);
        nextButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        nextButton.setForeground(CommonConstants.LIGHT_BLUE);
        nextButton.setVisible(false);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Hide -Next Button-
                nextButton.setVisible(false);

                //Reset first choice flag
                firstChoiceMade = false;

                //Update current question to the next question
                currentQuestion = questions.get(++currentQuestionNumber);
                questionTextArea.setText(currentQuestion.getQuestionText());

                //Reset and Update the Answer Buttons
                for(int i = 0; i < currentQuestion.getAnswers().size(); i++){
                    Answer answer = currentQuestion.getAnswers().get(i);

                    //Reset Background Color for Button
                    answerButtons[i].setBackground(Color.WHITE);

                    //Update answer text
                    answerButtons[i].setText(answer.getAnswerText());


                }

            }
        });
        add(nextButton);
    }

    private void addAnswerComponents() {
        //Apply a 60px vertical space between each answer button
        int verticalSpacing = 60;

        for(int i = 0;i < currentQuestion.getAnswers().size(); i++){
            Answer answer = currentQuestion.getAnswers().get(i);

            JButton answerButton = new JButton(answer.getAnswerText());
            answerButton.setBounds(60, 180 + (i * verticalSpacing), 262, 45);
            answerButton.setFont(new Font("Arial", Font.BOLD, 18));
            answerButton.setHorizontalAlignment(SwingConstants.LEFT);
            answerButton.setBackground(Color.WHITE);
            answerButton.setForeground(CommonConstants.DARK_BLUE);
            answerButton.addActionListener(this);
            answerButtons[i] = answerButton;
            add(answerButton);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton answerButton = (JButton) e.getSource();

        //Find Correct Answer
        Answer correctAnswer = null;
        for(Answer answer: currentQuestion.getAnswers()){
            if(answer.isCorrect()) {
                correctAnswer = answer;
                break;
            }
        }

        if(answerButton.getText().equals(correctAnswer.getAnswerText())){
            //User Chose the Right Answer

            //Change Button to Green
            answerButton.setBackground(CommonConstants.LIGHT_GREEN);

            //Increase Score only if it was the first choice
            if(!firstChoiceMade){
                scoreLabel.setText("Score:" + score + "/" + numOfQuestions);
            }

            //Check to see if it was the last question
            if(currentQuestionNumber == numOfQuestions - 1){
                //Display final results
                JOptionPane.showMessageDialog(QuizScreenGui.this
                            , "Your Final Score is " + score + "/" + numOfQuestions);
            }else{
                //Make -Next Button - Visible
                nextButton.setVisible(true);
            }
        }else{
            //Make Button Red to indicate incorrect choice
            answerButton.setBackground(CommonConstants.LIGHT_RED);
        }

        firstChoiceMade = true;

    }
}
