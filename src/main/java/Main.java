import com.google.gson.Gson;
import recruitment.facade.Facade;
import recruitment.gui.LoginForm;
import recruitment.models.Mark;
import recruitment.services.RestAPI;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;

/**
 * Created by Zhuk Pavel on 05.06.2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
//        RestAPI.start();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginForm.main(null);
            }
        });

        Gson gson = new Gson();
        Mark[] arr = gson.fromJson(new FileReader("src\\main\\resources\\marks.json"), Mark[].class);

        for (Mark m : arr) {
            System.out.println(m);
        }
    }
}
