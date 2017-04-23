package invertedIndex.gui;


import javax.swing.*;
import java.awt.*;

/**
 * Created by sina on 11/30/16.
 */
public class ProgramFrame extends JFrame{

    private Dimension d = new Dimension(1000,1000);


    public ProgramFrame() {

        this.setSize(d);
        this.setLocation(200,200);
        this.setLayout(null);

        this.add(new ProgramPanel(d));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        this.setVisible(true);



    }
}
