package database;

import java.sql.*;

public class JDBC {

    //MySQL Configurations
    private static final String DB_URL = "jdbc:mysql://3306/quiz_gui_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "DSML1";

    /*
           question - the question to be inserted
           category - the category of the question to be inserted if not already in the database
           answers - the answers to be inserted
           correctIndex - determines which of the answers is the correct answer
     */

    public static boolean saveQuestionCategoryAndAnswerToDatabase (String question, String category,
                                              String [] answers, int correctIndex){

        //Establish a DB Connection
        try{
            Connection connection = DriverManager.getConnection(
                    DB_URL,DB_USER,DB_PASSWORD
            );

        //Insert category if it´s new, otherwise retrieve it from the database
        Category categoryObject = getCategory(category);
        if(categoryObject == null) {
            //insert new category to database
            categoryObject = insertCategory(category);
        }

            //Insert question to database
            Question questionObject = insertQuestion(categoryObject, question);

            //Insert answers to database
            return insertAnswers(questionObject, answers, correctIndex);

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;/////////////////////
    }

    //Question Methods
    private static Question insertQuestion(Category category, String questionText){

        try{
            Connection connection = DriverManager.getConnection(
                    DB_URL,DB_USER,DB_PASSWORD
            );

            PreparedStatement insertQuestionQuery = connection.prepareStatement(
                    "INSERT INTO QUESTION (CATEGORY_ID, QUESTION_TEXT)" +
                            "VALUES(?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            insertQuestionQuery .setInt(1, category.getCategory_Id());
            insertQuestionQuery .setString(2, questionText);
            insertQuestionQuery .executeUpdate();

            //Check for the question id
            ResultSet resultSet = insertQuestionQuery.getGeneratedKeys();
            if(resultSet.next()){
                int questionId = resultSet.getInt(1);
                return new Question(questionId, category.getCategory_Id(), questionText);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        //Return null id there was an error inserting the question to the database
        return null;
    }


    //Category Methods
    private static Category getCategory (String category){
        try{
            Connection connection = DriverManager.getConnection(
                    DB_URL,DB_USER,DB_PASSWORD
            );

            PreparedStatement getCategoryQuery = connection.prepareStatement(
                    "SELECT * FROM CATEGORY WHERE CATEGORY_NAME = ?"
            );
            getCategoryQuery.setString(1, category);

            //Execute query and store results
            ResultSet resultSet = getCategoryQuery.executeQuery();
            if(resultSet.next()){
                //Found the category
                int category_Id = resultSet.getInt("category_id");
                return new Category(category_Id, category);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //Return null if it could not find the category in the database
        return null;
    }

    private static Category insertCategory (String category){

        try{
            Connection connection = DriverManager.getConnection(
                    DB_URL,DB_USER,DB_PASSWORD
            );

            PreparedStatement insertCategoryQuery = connection.prepareStatement(
                    "INSERT INTO CATEGORY (CATEGORY_NAME)" +
                            "VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            insertCategoryQuery.setString(1, category);
            insertCategoryQuery.executeUpdate();

            //Get the Category id that gets automatically incremented for each new insert in the category table
            ResultSet resultSet = insertCategoryQuery.getGeneratedKeys();
            if(resultSet.next()){
                int categoryId = resultSet.getInt(1);
                return new Category(categoryId,category);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //Return null if it could not find the category in the database
        return null;

    }

    //Answer Methods
    //True - successfully inserted answers
    //False - failed inserted answers
    private static boolean insertAnswers (Question question, String [] answers, int correctIndex){

        try{
            Connection connection = DriverManager.getConnection(
                    DB_URL,DB_USER,DB_PASSWORD
            );

            PreparedStatement insertAnswerQuery = connection.prepareStatement(
                    "INSERT INTO ANSWERS (QUESTION_ID, ANSWER_TEXT, IS_CORRECT)" +
                            "VALUES(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            insertAnswerQuery.setInt(1,question.getQuestion_Id());

            for(int i = 0; i < answers.length; i++ ){
                insertAnswerQuery.setString(2, answers [i]);

                if (i == correctIndex){
                    insertAnswerQuery.setBoolean(3, true);
                }else{
                    insertAnswerQuery.setBoolean(3, false);
                }

                insertAnswerQuery.executeUpdate();
            }

            return true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;

    }

}
