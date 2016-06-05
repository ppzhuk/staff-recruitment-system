import recruitment.gui.SimpleForm;
import recruitment.services.RestAPI;

import javax.swing.*;

/**
 * Created by Zhuk Pavel on 05.06.2016.
 */
public class Main {
    public static void main(String[] args) {
        RestAPI.start();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleForm();
            }
        });
    }
}
