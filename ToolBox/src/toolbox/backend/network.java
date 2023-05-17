/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toolbox.backend;

import com.developpez.adiguba.shell.Shell;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class network {
    //fonction de test de connexion
    public void Ping(String address){
        if(!address.equals("")){
            try {
            Shell shell = new Shell();
            shell.setDirectory(new File("C:\\Windows\\System32"));
            String result = shell.command("ping "+address+" > log.txt").consumeAsString();
        } catch (IOException ex) {
            System.out.println("error while executing operation");
        } catch (IllegalStateException ex) {
            System.out.println("error while executing operation");
        } 
        }else{
            System.out.println("error");
        }
        
    }
     //recuperer l'adresse ip de la machine     
    public void showAddress (JLabel label){
             try {
                 InetAddress address =  InetAddress.getLocalHost();
                 label.setText("Ip address for this computer: "+address.getHostAddress());
             } catch (UnknownHostException ex) {
                label.setText("Ip address for this computer is not available");
             }
    }
    //
        public void IpConfig(){
            try {
            Shell shell = new Shell();
            shell.setDirectory(new File("C:\\Windows\\System32"));
            String result = shell.command("ipconfig > log.txt").consumeAsString();
        } catch (IOException ex) {
            System.out.println("error while executing operation");
        } catch (IllegalStateException ex) {
            System.out.println("error while executing operation");
        } 
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
        public boolean create(){    
        //File script = new File("C:\\Windows\\System32\\script.txt");
        File log = new File("C:\\Windows\\System32\\log.txt");
        System.out.println("fichier crée");
        return true;
    }
        
 public static void main(String[] args){
        
        try {
            Shell shell = new Shell();
            shell.setDirectory(new File("C:\\Windows\\System32"));
            String result = shell.command("ipconfig > log.txt").consumeAsString();
        } catch (IOException ex) {
            System.out.println("error while executing operation");
        } catch (IllegalStateException ex) {
            System.out.println("error while executing operation");
        } 
    }
}
