/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoftp;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import static proyectoftp.cargar.ftp;

/**
 *
 * @author MiguelSC
 */
public class Presentacion extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    cargar hilo;

    static final String servidor = "www.peru-software.com";
    static final int puerto = 21;
    static final String usuario = "pp20172@peru-software.com";
    static final String password = "fisi20172";

    static FTPClient ftp = new FTPClient();

    public Presentacion() {
        try {
            initComponents();
            setLocationRelativeTo(null);

            ftp.connect(servidor, puerto);
            ftp.login(usuario, password);
            ftp.enterLocalPassiveMode();

            // Esencial para mostrar el tamaño original de descarga
            ftp.setFileType(ftp.BINARY_FILE_TYPE);

            // lists files and directories in the current working directory
            FTPFile[] files = ftp.listFiles();
            // iterates over the files and prints details for each
            DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String details = "";
            for (FTPFile file : files) {

                details = details + file.getName();
                if (file.isDirectory()) {
                    details = "[" + details + "]";
                }
                details += "\t\t" + file.getSize();
                details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());
                details += "\n";
                //System.out.println(details);    
            }
            listaArchivos.setText(details);

            hilo = new cargar(progreso);
          
            
        } catch (IOException ex) {
            Logger.getLogger(Presentacion.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        progreso = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaArchivos = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        descargar = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoftp/descarga.png"))); // NOI18N

        progreso.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                progresoStateChanged(evt);
            }
        });

        listaArchivos.setColumns(20);
        listaArchivos.setRows(5);
        jScrollPane1.setViewportView(listaArchivos);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("LISTA DE ARCHIVOS");

        descargar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        descargar.setText("DESCARGAR");
        descargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descargarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(progreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(descargar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descargar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progreso, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void progresoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_progresoStateChanged
        if (progreso.getValue() == 100) {
            dispose();
            
            Ventana v = new Ventana();
            v.setVisible(true);
            /*
            //File file = new File("C:\\Users\\GRLIMA\\Documents\\NetBeansProjects\\ProyectoFTP\\introduccion.pptx");
            File file = new File("C:/Users/GRLIMA/Documents/NetBeansProjects/ProyectoFTP/introduccion.pptx");
            
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = null;
                desktop = Desktop.getDesktop();
                try {
                    desktop.open(file);
                } catch (IOException ex) {
                    Logger.getLogger(Presentacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             */
        }
    }//GEN-LAST:event_progresoStateChanged

    private void descargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descargarActionPerformed
            hilo.start();
            hilo = null;
    }//GEN-LAST:event_descargarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton descargar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea listaArchivos;
    private javax.swing.JProgressBar progreso;
    // End of variables declaration//GEN-END:variables
}
