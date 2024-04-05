package screens;

import constants.CommonConstants;

import javax.swing.*;

public class TitleScreenGui extends JFrame {

    public TitleScreenGui(){
        //call the constructor of the superclass with the title of "Title Screen"
        super("Title Screen");

        //set the size of the JFrame to 400 px wide and 565 px tall
        setSize(400,565);

        //set the layout manager of the frame to null, allowing manual positioning of the components
        setLayout(null);

        //set the frame to be centered on the screen when displayed
        setLocationRelativeTo(null);

        //disable resizing of the frame by the user
        setResizable(false);

        //set the default close operation of the frame to exit after the application has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Change the background Color
        getContentPane().setBackground(CommonConstants.LIGHT_BLUE);



    }
}
