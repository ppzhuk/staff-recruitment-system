package recruitment.gui;

import javax.swing.*;

/**
 * Created by Zhuk Pavel on 06.06.2016.
 */
public class SimpleForm {
    
    public SimpleForm() {
        JFrame jfrm = new JFrame("simple rfame");
        jfrm.setSize(275, 100);
        jfrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel label = new JLabel("test label");
        jfrm.add(label);
        jfrm.setVisible(true);
    }
}
