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
public class ListDisk {

   
    private static Process process;
    
    public boolean create(){    
        //File script = new File("C:\\Windows\\System32\\script.txt");
        File log = new File("C:\\Windows\\System32\\log.txt");
        System.out.println("fichier crée");
        return true;
    }
/*    public boolean createLog(){
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
    }*/
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
    public String clean(String nbre){
        String[] carac = {"0","1","2","3","4","5","6","7","8"};
        String value="";
        for(int i=0; i<carac.length; i++){
                if (nbre.equals(carac[i])){
                    value = carac[i]; 
                } else {
                    
                }
            }
        if(!value.equals("")){
            try {
            Shell shell = new Shell();
            shell.setDirectory(new File("C:\\Windows\\System32"));
            String result = shell.command("diskpart/s cleanDisk"+nbre+".txt > log.txt").consumeAsString();
        } catch (IOException ex) {
            System.out.println("error while executing operation");
        } catch (IllegalStateException ex) {
            System.out.println("error while executing operation");
        }
            return "success";
        }else{
            return "error";
        }
        
    }
    public String mbrToGpt(String nbre){
         String[] carac = {"0","1","2","3","4","5","6","7","8"};
        String value="";
        for(int i=0; i<carac.length; i++){
                if (nbre.equals(carac[i])){
                    value = carac[i]; 
                } else {
                    
                }
            }
        if(!nbre.equals("")){
            try {
            Shell shell = new Shell();
            shell.setDirectory(new File("C:\\Windows\\System32"));
            String result = shell.command("diskpart/s mbrToGpt"+nbre+".txt > log.txt").consumeAsString();
        } catch (IOException ex) {
            System.out.println("error while executing operation");
        } catch (IllegalStateException ex) {
            System.out.println("error while executing operation");
        }
            return "success";
        }else{
            return "error";
        }
        
    }
        
    public String gptToMbr(String nbre){
        String[] carac = {"0","1","2","3","4","5","6","7","8"};
        String value="";
        for(int i=0; i<carac.length; i++){
                if (nbre.equals(carac[i])){
                    value = carac[i]; 
                } else {
                    
                }
            }
        if(!nbre.equals("")){
            try {
            Shell shell = new Shell();
            shell.setDirectory(new File("C:\\Windows\\System32"));
            String result = shell.command("diskpart/s gptToMbr"+nbre+".txt > log.txt").consumeAsString();
        } catch (IOException ex) {
            System.out.println("error while executing operation");
        } catch (IllegalStateException ex) {
            System.out.println("error while executing operation");
        }
            return "success";
        }else{
            return "error";
        }
        
    }
    
    
            
    public static void main(String[] args){
        
        try {
            ListDisk d = new ListDisk();
            Shell shell = new Shell();
            shell.setDirectory(new File("C:\\Windows\\System32"));
            String result = shell.command("diskpart/s listDisk.txt > log.txt").consumeAsString();
        } catch (IOException ex) {
            Logger.getLogger(ListDisk.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(ListDisk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
