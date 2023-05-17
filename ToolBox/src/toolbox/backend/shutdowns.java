/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toolbox.backend;

import com.developpez.adiguba.shell.Shell;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class shutdowns {
  public void shut(int nbre){
            try {
            Shell shell = new Shell();
            shell.setDirectory(new File("C:\\Windows\\System32"));
            String result = shell.command("shutdown -s -t "+nbre+" > log.txt").consumeAsString();
        } catch (IOException ex) {
            Logger.getLogger(ListVolume.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(ListVolume.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
public void now(){
      try {
          Runtime.getRuntime().exec("shutdown -s -t 0");
      } catch (IOException ex) {
         System.out.println("error");
      }
}  
}
