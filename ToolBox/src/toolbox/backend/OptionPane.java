package toolbox.backend;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author marina la star
 */
public class OptionPane extends JFrame{
    public OptionPane(){
        //this.setAlwaysOnTop(true);
        //this.setVisible(true);
    }
    public void message(String message, boolean value){
        JOptionPane pane = new JOptionPane();
        if(value==true){
            pane.showMessageDialog(this,message,"status",JOptionPane.ERROR_MESSAGE);
        }else{
            pane.showMessageDialog(this,message,"status",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
