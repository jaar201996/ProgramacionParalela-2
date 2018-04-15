package proyectoftp;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JProgressBar;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import java.io.File;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MiguelSC
 */
public class cargar extends Thread {

    JProgressBar progreso;

    static final String servidor = "www.peru-software.com";
    static final int puerto = 21;
    static final String usuario = "pp20172@peru-software.com";
    static final String password = "fisi20172";

    static FTPClient ftp = new FTPClient();

    public cargar(JProgressBar progreso) {
        super();
        this.progreso = progreso;
        progreso.setBackground(Color.blue);
        progreso.setForeground(Color.green);
    }

    @Override
    public void run() {
        try {
            ftp.connect(servidor, puerto);
            ftp.login(usuario, password);
            ftp.enterLocalPassiveMode();

            // Esencial para mostrar el tamaño original de descarga
            ftp.setFileType(ftp.BINARY_FILE_TYPE);

            // lists files and directories in the current working directory
            FTPFile[] files = ftp.listFiles();
            // iterates over the files and prints details for each
            DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int tamano;
            int tamTotal = 0;
            int tamAcumulado = 0;

            for (FTPFile file : files) {

                if (file.getName().equals("so01.pptx")) { //captura y muestra datos de un archivo especifico
                    String details = file.getName();
                    if (file.isDirectory()) {
                        tamano = (int) file.getSize();
                        details = "[" + details + "]";
                    }
                    tamTotal = (int) file.getSize();
                    details += "\t\t" + file.getSize();
                    details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());
                    System.out.println(details);
                }
            }

            /*
            1era forma
             */
             /*String archivoRemoto="/so01.pptx";
            String archivoLocal="introduccion.pptx";
            OutputStream streamLocal=
               new BufferedOutputStream(new FileOutputStream(archivoLocal));
            
           
            ftp.setFileType(ftp.BINARY_FILE_TYPE);
            boolean exito= ftp.retrieveFile(archivoRemoto, streamLocal);
            if(exito){
                Logger.getLogger(FtpFileDownload.class.getName()).log(Level.INFO,"Descargado con exito");
            }
            else{
                Logger.getLogger(FtpFileDownload.class.getName()).log(Level.INFO,"Problemas en la descarga");
            }*/
 
             /*
            2da forma
             */
            String archivoRemoto = "/so01.pptx";
            File archivoLocal = new File("introduccion.pptx");
            OutputStream streamLocal
                    = new BufferedOutputStream(new FileOutputStream(archivoLocal));

            InputStream inputStream = ftp.retrieveFileStream(archivoRemoto);
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            int sum = 0;
            int a = 0;

            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                streamLocal.write(bytesArray, 0, bytesRead);
                sum = sum + bytesRead;                 // sum acumula la cantidad descargada
                System.out.println("Descargue: " + sum);
                tamAcumulado = tamTotal / 10;
                if (sum >= tamAcumulado && a <= 100) { // si sobrepasa el x0 % aumenta la barra
                    progreso.setValue(a);
                    pausa(300); 
                    a = a + 10;
                    tamAcumulado = tamAcumulado + tamTotal / 10;
                }
                //System.out.println(bytesRead);
            }
            boolean exito = ftp.completePendingCommand();
            if (exito) {
                System.out.println("Descarga hecha :3 ");
            }
            streamLocal.close();
            inputStream.close();

        } catch (IOException ex) {
            Logger.getLogger(cargar.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.logout();
                    ftp.disconnect();
                } catch (IOException ex) {
                    Logger.getLogger(cargar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void pausa(int mlseg) {
        try {
            Thread.sleep(mlseg);

        } catch (Exception e) {
        }
    }
}
