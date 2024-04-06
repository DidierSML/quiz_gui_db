import screens.CreateQuestionScreenGui;
import screens.TitleScreenGui;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        //Ensures Swing GUI tasks are executed on the event dispatch thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Create and display the "title screen" gui window
//                new TitleScreenGui().setVisible(true);

                //Create and display the "Create Questions Gui"
                new CreateQuestionScreenGui().setVisible(true);
            }
        });
    }
}
