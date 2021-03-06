import com.google.gson.Gson;
import recruitment.facade.Facade;
import recruitment.gui.LoginForm;
import recruitment.mappers.PersonMapper;
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
        RestAPI.start();
        SwingUtilities.invokeLater(() -> LoginForm.main(null));
    }
}
