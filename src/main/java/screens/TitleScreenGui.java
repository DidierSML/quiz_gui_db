package screens;

import constants.CommonConstants;
import database.Category;
import database.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TitleScreenGui extends JFrame {

    private JComboBox categoriesMenu;
    private JTextField numberOfQuestionsTextField;

    public TitleScreenGui(){

        //Call the constructor of the superclass with the title of "Title Screen"
        super("Title Screen");

        //Set the size of the JFrame to 400 px wide and 565 px tall
        setSize(400,565);

        //Set the layout manager of the frame to null, allowing manual positioning of the components
        setLayout(null);

        //Set the frame to be centered on the screen when displayed
        setLocationRelativeTo(null);

        //Disable resizing of the frame by the user
        setResizable(false);

        //Set the default close operation of the frame to exit after the application has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Change the background Color
        getContentPane().setBackground(CommonConstants.LIGHT_BLUE);

        addGuiComponents();

    }


    //Adding Visual Components
    private void addGuiComponents(){

        //Title label
        JLabel titleLabel = new JLabel("Quiz Game!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBounds(0,20,390,43);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(titleLabel);

        //Choose Category Label
        JLabel chooseCategoryLabel = new JLabel("Choose a Category");
        chooseCategoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chooseCategoryLabel.setBounds(0,90,400,43);
        chooseCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chooseCategoryLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(chooseCategoryLabel);

        //Category drop down menu
        ArrayList <String> categoryList = JDBC.getCategories();
        categoriesMenu = new JComboBox(categoryList.toArray());
        categoriesMenu.setBounds(20,120,337,45);
        categoriesMenu.setForeground(CommonConstants.DARK_BLUE);
        add(categoriesMenu);

        //Num of questions label
        JLabel numOfQuestionsLabel = new JLabel("Number of Questions: ");
        numOfQuestionsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        numOfQuestionsLabel.setBounds(20,190,172,20);
        numOfQuestionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numOfQuestionsLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(numOfQuestionsLabel);

        //Num of questions text input field
        numberOfQuestionsTextField = new JTextField("10");
        numberOfQuestionsTextField.setFont(new Font("Arial", Font.BOLD, 16));
        numberOfQuestionsTextField.setBounds(200,190,148,26);
        numberOfQuestionsTextField.setHorizontalAlignment(SwingConstants.CENTER);
        numberOfQuestionsTextField.setForeground(CommonConstants.DARK_BLUE);
        add(numberOfQuestionsTextField);

        //Start button
        JButton startButton = new JButton("Start!");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBounds(65,290,262,45);
        startButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        startButton.setForeground(CommonConstants.LIGHT_BLUE);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Dispose of this Screen
                if(validateInput()){
                    //Retrieve Category
                    Category category = JDBC.getCategory(categoriesMenu.getSelectedItem().toString());

                    //Invalid Category
                    if(category == null) return;

                    int numOfQuestions = Integer.parseInt(numberOfQuestionsTextField.getText());

                    //Load Quiz Screen
                    QuizScreenGui quizScreenGui = new QuizScreenGui(category, numOfQuestions);
                    quizScreenGui.setLocationRelativeTo(TitleScreenGui.this);

                    //Dispose of this Scream
                    TitleScreenGui.this.dispose();

                    //Display Quiz Screen
                    quizScreenGui.setVisible(true);
                }
            }
        });
        add(startButton);

        //Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBounds(65,350,262,45);
        exitButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        exitButton.setForeground(CommonConstants.LIGHT_BLUE);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Dispose of this Screen
                TitleScreenGui.this.dispose();
            }
        });
        add(exitButton);

        //Create a Question Button
        JButton createAQuestionButton = new JButton("Create a Question");
        createAQuestionButton.setFont(new Font("Arial", Font.BOLD, 16));
        createAQuestionButton.setBounds(65,420,262,45);
        createAQuestionButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        createAQuestionButton.setForeground(CommonConstants.LIGHT_BLUE);
        createAQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create question screen gui
                CreateQuestionScreenGui createQuestionScreenGui = new CreateQuestionScreenGui();
                createQuestionScreenGui.setLocationRelativeTo(TitleScreenGui.this);

                //Dispose of this title screen
                TitleScreenGui.this.dispose();

                //Display create a question screen gui
                createQuestionScreenGui.setVisible(true);



            }
        });
        add(createAQuestionButton);


    }

    //True - valid Input
    //False - invalid Input
    private boolean validateInput(){
        //Num of Questions field must not be empty
        if(numberOfQuestionsTextField.getText().replaceAll(" ", " ").length() <= 0) return false;

        //No category is Chosen
        if(categoriesMenu.getSelectedItem() == null ) return false;

        return true;

        }

}
