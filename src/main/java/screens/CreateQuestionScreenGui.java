package screens;

import constants.CommonConstants;

import javax.swing.*;
import java.awt.*;

public class CreateQuestionScreenGui extends JFrame {

    private JTextArea questionTextArea;
    private JTextField categoryTextField;
    private JTextField[] answerTextFields;
    private ButtonGroup buttonGroup;
    private JRadioButton [] answerRadioButtons;

    public CreateQuestionScreenGui(){

        super("Create a Question");
        setSize(851,565);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(CommonConstants.LIGHT_BLUE);

        answerRadioButtons = new JRadioButton [4];
        answerTextFields = new JTextField [4];
        buttonGroup = new ButtonGroup();

        addGuiComponents();

    }

    private void addGuiComponents(){

        //Title label
        JLabel titleLabel = new JLabel("Create your own Question");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50,15,310,24);
        titleLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(titleLabel);

        //Question Label
        JLabel questionLabel = new JLabel("Question: ");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setBounds(50,60,93,20);
        questionLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(questionLabel);

        //Question text area
        questionTextArea = new JTextArea();
        questionTextArea.setFont(new Font("Arial", Font.BOLD, 16));
        questionTextArea.setBounds(50,90,310,110);
        questionTextArea.setForeground(CommonConstants.DARK_BLUE);
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        add(questionTextArea);

        //Category Label
        JLabel categoryLabel = new JLabel("Category: ");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        categoryLabel.setBounds(50,250,93,20);
        categoryLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(categoryLabel);

        //Category Text input Field
        categoryTextField = new JTextField();
        categoryTextField.setFont(new Font("Arial", Font.BOLD, 16));
        categoryTextField.setBounds(50,280,310,36);
        categoryTextField.setForeground(CommonConstants.DARK_BLUE);
        add(categoryTextField);


        addAnswerComponents();


    }

    private void addAnswerComponents() {

        //vertical space between each answer component
        int verticalSpacing = 100;

        //We are going to create 4 answers labels, 4 radio buttons, and 4 text input fields
        for(int i = 0; i < 4; i++){
            //Answer label
            JLabel answerLabel = new JLabel("Answer #" + (i + 1));
            answerLabel.setFont(new Font("Arial", Font.BOLD,16));
            answerLabel.setBounds(470,60 + (i * verticalSpacing),93,20);
            answerLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
            add(answerLabel);

            //Radio Buttons
            answerRadioButtons [i] = new JRadioButton();
            answerRadioButtons [i].setBounds(440, 100 + (i * verticalSpacing), 21,21);
            answerRadioButtons [i].setBackground(null);
            buttonGroup.add(answerRadioButtons [i]);
            add(answerRadioButtons[i]);

            //Answer TextFields
            answerTextFields [i] = new JTextField();
            answerTextFields [i].setBounds(470, 90 + (i * verticalSpacing), 310,36);
            answerTextFields[i].setFont(new Font("Arial", Font.PLAIN,16));
            answerTextFields[i].setForeground(CommonConstants.DARK_BLUE);
            add( answerTextFields[i]);

        }


    }
}
