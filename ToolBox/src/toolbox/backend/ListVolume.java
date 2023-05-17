package toolbox.backend;

import com.developpez.adiguba.shell.Shell;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Brayant
 */
public class ListVolume {

   
    private static Process process;
    public String result;
    
    public void setResult(String val){
      this.result = val;  
    }
    public String getResult(){
      return this.result ; 
    }
    public boolean create(){    
        //File script = new File("C:\\Windows\\System32\\script.txt");
        File log = new File("C:\\Windows\\System32\\log.txt");
        System.out.println("fichier crée");
        return true;
    }
   /* public boolean createLog(){
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
            setResult( sb.toString());
        } catch (FileNotFoundException ex) {
            System.out.println("erreur lors de la lecture du fichier");
        } catch (IOException ex) {
            System.out.println("erreur lors de la lecture du fichier");
        }
    }
        public String read(){
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
            return sb.toString();
        } catch (FileNotFoundException ex) {
            System.out.println("erreur lors de la lecture du fichier");
        } catch (IOException ex) {
            System.out.println("erreur lors de la lecture du fichier");
        }
        return "erreur";
    }
    
     public void format(String nbre){
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
            String result = shell.command("diskpart/s formatvolume"+nbre+".txt > log.txt").consumeAsString();
        } catch (IOException ex) {
            System.out.println("error while executing operation");
        } catch (IllegalStateException ex) {
            System.out.println("error while executing operation");
        } 
        }else{
            System.out.println("error");
        }
        
    }
     
      public String fatToNtfs(String caractere){
          //List<String> alphabet = new ArrayList<>();
          String[] carac = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
            for(int i=0; i<carac.length; i++){
                if (caractere.equalsIgnoreCase(carac[i])){
                    try {
                        Shell shell = new Shell();
                        shell.setDirectory(new File("C:\\Windows\\System32"));
                        String result = shell.command("CONVERT "+caractere.toUpperCase()+": /FS:NTFS").consumeAsString();
                      
                    } catch (IOException ex) {
                        System.out.println("error while executing operation");
                    } catch (IllegalStateException ex) {
                        System.out.println("error while executing operation");
                    } 
                    return "success";
                } else {
                    
                }
            }
        return "error";
      }
      
      public String ntfsToFat(String nbre){
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
            String result = shell.command("diskpart/s formatfat"+nbre+".txt > log.txt").consumeAsString();
        } catch (IOException ex) {
            System.out.println("error while executing operation");
        } catch (IllegalStateException ex) {
            System.out.println("error while executing operation");
        }
        return "success";
        }
        else{
          return "error";  
        }
    }
            
    public static void main(String[] args){
        
        try {
            ListVolume d = new ListVolume();
            Shell shell = new Shell();
            shell.setDirectory(new File("C:\\Windows\\System32"));
            String result = shell.command("diskpart/s listvolume.txt > log.txt").consumeAsString();
        } catch (IOException ex) {
            Logger.getLogger(ListVolume.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(ListVolume.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
