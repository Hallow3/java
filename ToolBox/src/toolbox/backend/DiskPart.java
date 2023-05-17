package toolbox.backend;

import com.developpez.adiguba.shell.ProcessConsumer;
import com.developpez.adiguba.shell.Shell;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author Brayant
 */
public class DiskPart {

   
    private static Process process;
    
    public boolean create(){    
        //File script = new File("C:\\Windows\\System32\\script.txt");
        File log = new File("C:\\Windows\\System32\\log.txt");
        System.out.println("fichier crée");
        return true;
    }
    public boolean createLog(){
        Boolean bool= false;
          File script = new File("C:\\Windows\\System32\\diskpart.bat");
        try {
            PrintWriter writer = new PrintWriter(script);
            writer.println("diskpart /s script.txt > log.txt");
            System.out.println("ecriture reussie");
            bool=true;
        } catch (FileNotFoundException ex) {
            System.out.println("erreur lors de la creation des fichier de parametre");
            bool=false;
        }
        return bool;
    }
    public void readMethod(JTextArea label){
        File logRead = new File("C:\\Windows\\System32\\log.txt");
        try {
            FileReader fr = new FileReader(logRead);
            BufferedReader buf = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;
            while((line=buf.readLine())!= null){
           sb.append(line);
           sb.append("\n");
            }
            fr.close();
            //System.out.println(sb.toString());
            label.setText(sb.toString());
        } catch (FileNotFoundException ex) {
            System.out.println("erreur lors de la lecture du fichier");
        } catch (IOException ex) {
            System.out.println("erreur lors de la lecture du fichier");
        }
    }
    
            
    public static void main(String[] args){
        
        try {
            DiskPart d = new DiskPart();
            Shell shell = new Shell();
            shell.setDirectory(new File("C:\\Windows\\System32"));
            String result = shell.exec("C:\\Users\\marina la star\\Desktop\\Nouveau dossier (3)\\commande.bat").consumeAsString();
        } catch (IOException ex) {
            Logger.getLogger(DiskPart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(DiskPart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
