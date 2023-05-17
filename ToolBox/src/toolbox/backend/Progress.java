/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package toolbox.backend;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import toolbox.frontend.home.Log;

/**
 *
 * @author marina la star
 */
public class Progress extends JFrame{
private Thread t;
JOptionPane pane;
public int progresseValue;
private JProgressBar bar;
private ListVolume startVolume = new ListVolume();
private ListDisk startDisk = new ListDisk();
private String operation;
private String contains;
public Progress(String title,String value){
this.operation = title;
this.contains = value;
this.setBackground(Color.black);
this.setResizable(false);
this.setUndecorated(true);
this.setSize(300, 30);
this.setTitle(title);
this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
this.setLocationRelativeTo(null);
t = new Thread(new Traitement());
bar = new JProgressBar();
bar.setMaximum(500);
bar.setMinimum(0);
bar.setStringPainted(true);
bar.setBackground(Color.BLACK);
this.getContentPane().add(bar, BorderLayout.CENTER);
t.start();
this.setVisible(true);
this.setAlwaysOnTop(true);
}
public void informationMessage(){
   pane = new JOptionPane();
   pane.showMessageDialog(this,"proccess finished","status",JOptionPane.INFORMATION_MESSAGE);
}
public void errorMessage(){
   pane = new JOptionPane();
   pane.showMessageDialog(this,"an error occured","status",JOptionPane.ERROR_MESSAGE);
}
class Traitement implements Runnable{
@Override
public void run(){
for(int val = 0; val <= 500; val++){
bar.setValue(val);
if(bar.getValue()== 2 && operation.equals("formatting")){
   startVolume.format(contains);
}
else if(bar.getValue()== 2 && operation.equals("cleaning")){
  startDisk.clean(contains);
}
else if(bar.getValue()== 2 && operation.equals("convert GPT To MBR")){
  startDisk.gptToMbr(contains);
}
else if(bar.getValue()== 2 && operation.equals("convert MBR To GPT")){
  startDisk.mbrToGpt(contains);
}
else if(bar.getValue()== 2 && operation.equals("converting FAT32 To NTFS")){
  startVolume.fatToNtfs(contains);
}
else if(bar.getValue()== 2 && operation.equals("converting NTFS To FAT32")){
  startVolume.format(contains);
}
else{
    
} 
// definition des operation apres lecture du fichier d'erreur

if(bar.getValue()== 270 && operation.equals("formatting")){
   if(!startVolume.read().contains("%")){
       dispose();
       if(startVolume.read().contains("100%")){
       informationMessage();
       }else{
       errorMessage();
       Log logFile = new Log();
       logFile.setVisible(true);
       t.stop();
       }
   }
}
else if(bar.getValue()== 270 && operation.equals("cleaning")){
     if(startVolume.read().contains("DiskPart a r�ussi � nettoyer le disque.")){
       //si le traitement n'est pas en cour d'execution:
       }else{
       errorMessage();
       Log logFile = new Log();
       logFile.setVisible(true);
       t.stop();
     }
}
else if(bar.getValue()== 270 && operation.equals("convert GPT To MBR")){
      if(!startVolume.read().contains("%")){
       //si le traitement n'est pas en cour d'execution:
       dispose();
       if(startVolume.read().contains("100%")){
       informationMessage();
       }else{
       errorMessage();
       Log logFile = new Log();
       logFile.setVisible(true);
       t.stop();
     }
   }
}
else if(bar.getValue()== 270 && operation.equals("convert MBR To GPT")){
       if(!startVolume.read().contains("%")){
       //si le traitement n'est pas en cour d'execution:
       dispose();
       if(startVolume.read().contains("100%")){
       informationMessage();
       }else{
       errorMessage();
       Log logFile = new Log();
       logFile.setVisible(true);
       t.stop();
     }
   }
}
else if(bar.getValue()== 270 && operation.equals("converting FAT32 To NTFS")){
       if(!startVolume.read().contains("%")){
       //si le traitement n'est pas en cour d'execution:
       dispose();
       if(startVolume.read().contains("100%")){
       informationMessage();
       }else{
       errorMessage();
       Log logFile = new Log();
       logFile.setVisible(true);
       t.stop();
     }
   }
}
else if(bar.getValue()== 270 && operation.equals("converting NTFS To FAT32")){
       if(!startVolume.read().contains("%")){
       //si le traitement n'est pas en cour d'execution:
       dispose();
       if(startVolume.read().contains("100%")){
       informationMessage();
       }else{
       errorMessage();
       Log logFile = new Log();
       logFile.setVisible(true);
       t.stop();
     }
   }
}
else{
    
}
//verification apres 100%

if(bar.getValue()== 499 && operation.equals("formatting")){
   if(!startVolume.read().contains("%")){
       //si loperation est terminer on verifie :
    dispose();
    if(startVolume.read().contains("100%")){
    //pane.showMessageDialog(this,"proccess finished","status",JOptionPane.INFORMATION_MESSAGE);
       informationMessage();
     }else{
    //pane.showMessageDialog(this,"proccess finished with errors","status",JOptionPane.ERROR_MESSAGE);
       errorMessage();
    Log logFile = new Log();
    logFile.setVisible(true);
}
   }
}
else if(bar.getValue()== 499 && operation.equals("cleaning")){
  if(startVolume.read().contains("DiskPart a r�ussi � nettoyer le disque.")){
       //si loperation est terminer on verifie :
       dispose();
    //pane.showMessageDialog(this,"proccess finished","status",JOptionPane.INFORMATION_MESSAGE);
       informationMessage();
     }else{
    //pane.showMessageDialog(this,"proccess finished with errors","status",JOptionPane.ERROR_MESSAGE);
       errorMessage();
    Log logFile = new Log();
    logFile.setVisible(true);
  } 
   
}
else if(bar.getValue()== 499 && operation.equals("convert GPT To MBR")){
  if(!startVolume.read().contains("%")){
       //si loperation est terminer on verifie :
    dispose();
    if(startVolume.read().contains("100%")){
    //pane.showMessageDialog(this,"proccess finished","status",JOptionPane.INFORMATION_MESSAGE);
       informationMessage();
     }else{
    //pane.showMessageDialog(this,"proccess finished with errors","status",JOptionPane.ERROR_MESSAGE);
       errorMessage();
    Log logFile = new Log();
    logFile.setVisible(true);
}
   }
}
else if(bar.getValue()== 499 && operation.equals("convert MBR To GPT")){
  if(!startVolume.read().contains("%")){
       //si loperation est terminer on verifie :
    dispose();
    if(startVolume.read().contains("100%")){
    //pane.showMessageDialog(this,"proccess finished","status",JOptionPane.INFORMATION_MESSAGE);
       informationMessage();
     }else{
    //pane.showMessageDialog(this,"proccess finished with errors","status",JOptionPane.ERROR_MESSAGE);
       errorMessage();
    Log logFile = new Log();
    logFile.setVisible(true);
}
   }
}
else if(bar.getValue()== 499 && operation.equals("converting FAT32 To NTFS")){
  if(!startVolume.read().contains("%")){
       //si loperation est terminer on verifie :
    dispose();
    if(startVolume.read().contains("100%")){
    //pane.showMessageDialog(this,"proccess finished","status",JOptionPane.INFORMATION_MESSAGE);
       informationMessage();
     }else{
    //pane.showMessageDialog(this,"proccess finished with errors","status",JOptionPane.ERROR_MESSAGE);
       errorMessage();
    Log logFile = new Log();
    logFile.setVisible(true);
}
   }
}
else if(bar.getValue()== 499 && operation.equals("converting NTFS To FAT32")){
  if(!startVolume.read().contains("%")){
       //si loperation est terminer on verifie :
    dispose();
    if(startVolume.read().contains("100%")){
    //pane.showMessageDialog(this,"proccess finished","status",JOptionPane.INFORMATION_MESSAGE);
       informationMessage();
     }else{
    //pane.showMessageDialog(this,"proccess finished with errors","status",JOptionPane.ERROR_MESSAGE);
       errorMessage();
    Log logFile = new Log();
    logFile.setVisible(true);
}
   }
}
else{
    
}
if(bar.getValue()==500){
  dispose();  
}

try {
t.sleep(15);
} catch (InterruptedException e) {
// TODO Auto-generated catch block
}
}
//pane.showMessageDialog(null,"proccess finished","status",JOptionPane.INFORMATION_MESSAGE);
}
}
}
