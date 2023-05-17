package toolbox.backend;

import static java.lang.System.getProperty;
import static java.lang.System.getenv;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 *
 * @author brayant
 */
public class InfoSystem {
   public String nom_system;
    public String nom_hote;
    public String type_processeur;
    public String version_system;
    public String fabriquant;
    public String langue;
    public String nombre_processeurs;
    public String taille_de_la_ram;
    public String nbre_de_coeurs;
    public InfoSystem(){

    }

    public  String getNom_system() {
        this.nom_system = getProperty("os.name");
        return nom_system;
    }

    public String getNom_hote() {
        this.nom_hote = getProperty("user.name");
        return nom_hote;
    }

    public String getType_processeur() {
        this.type_processeur = getProperty("sun.arch.data.model");
        return type_processeur +" bits";
    }

    public String getVersion_system() {
        this.version_system = getProperty("os.version");
        return version_system;
    }

    public String getFabriquant() {
        this.fabriquant = getProperty("java.vm.vendor");
        return fabriquant;
    }

    public String getLangue() {
        this.langue = getProperty("user.language");
        return langue;
    }

    public String getNombre_processeurs() {
        int nbre = Runtime.getRuntime().availableProcessors();
        this.nombre_processeurs = ""+nbre;
        return nombre_processeurs;
    }

    public String getTaille_de_la_ram() {
        OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();
        long taille = Runtime.getRuntime().totalMemory();
        this.taille_de_la_ram = ""+taille;
        return taille_de_la_ram;
    }

    public String getNbre_de_coeurs() {
        this.nbre_de_coeurs = getenv("NUMBER_OF_PROCESSORS");
        return nbre_de_coeurs;
    }

    @Override
    public String toString() {
        return "Your Operating System:\n" +
                "\n operating system name :" + getNom_system() + '\'' +
                "\n host name :" + getNom_hote() + '\'' +
                "\n processor type :" + getType_processeur()+ '\'' +
                "\n operating system version :" + getVersion_system() + '\'' +
                "\n constructor :" + getFabriquant() + '\'' +
                "\n language :" + getLangue() + '\'' +
                //", nombre_processeurs='" + getNombre_processeurs() + '\'' +
                "\n available Memory :" + getTaille_de_la_ram() + '\'' +
                "\n processor number :" + getNbre_de_coeurs() + '\''
                ;
    }
    
}
