//package form;
package rs.np.storage_manager_server.form;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import rs.np.storage_manager_common.domain.User;
import rs.np.storage_manager_server.connection.Server;

/**
 * JFrame klasa koja predstavlja glavnu formu servera.
 * @author Milan
 */
public class ServerForm extends javax.swing.JFrame {
	/**
	 * privatni atribut server, predstavlja instancu klase Server sa metodama za rad sa klijentima
	 */
private Server server;
    /**
     * Neparametrizovani konstruktor klase. Poziva initComponents, setExtendedState i 
     * menja tekst dugmeta txtState na "Server is off"
     */
    public ServerForm() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        txtState.setText("Server is off.");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnOn = new javax.swing.JButton();
        btnOff = new javax.swing.JButton();
        txtState = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(153, 153, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("TOGGLE SERVER OPTION:");

        btnOn.setBackground(new java.awt.Color(102, 255, 102));
        btnOn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnOn.setText("Server on");
        btnOn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnActionPerformed(evt);
            }
        });

        btnOff.setBackground(new java.awt.Color(255, 51, 51));
        btnOff.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnOff.setText("Server off");
        btnOff.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOffActionPerformed(evt);
            }
        });

        txtState.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtState.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtState.setToolTipText("");

        jMenu1.setText("Server");

        jMenuItem1.setText("Settings");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Active users");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");

        jMenuItem3.setText("About software");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(305, 305, 305)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnOn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOff, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtState, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(192, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOff, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(txtState, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Event handler za klik na dugme off. Gasi se server i zavrsava proces
     * @param evt tipa java.awt.event.ActionEvent predstavlja klik na dugme btnOff
     */
    private void btnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOffActionPerformed
        // TODO add your handling code here:
        if(server == null || server.getServerSocket()==null){
            JOptionPane.showMessageDialog(this, "Server is already turned off.");
        }
        else{
            try {
                server.getServerSocket().close();
                txtState.setText("Server is off.");
                System.exit(1);
            } catch (IOException ex) {
                Logger.getLogger(ServerForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnOffActionPerformed
    /**
     * Event handler za klik na dugme on. Pokrece se server
     * 
     * @param evt tipa java.awt.event.ActionEvent, predstavlja klik na dugme on
     */
    private void btnOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnActionPerformed
        // TODO add your handling code here:
        if(server == null || !server.isAlive()){
            try {
                server = new Server();
                server.establishServer();
                server.start();
                txtState.setText("Server is on.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "This port is being used by another process."
                        + " Check if there's an instance of the program already running in the background.");
                Logger.getLogger(ServerForm.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }else
            JOptionPane.showMessageDialog(this, "Server is already turned on.");
    }//GEN-LAST:event_btnOnActionPerformed
    /**
     * Event handler za klik na meni opciju settings form. Otvara se nova forma za podesavanja
     * @param evt tipa java.awt.event.ActionEvent, predstavalja klik na meni "Settings"
     */
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        new SettingsForm(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    /**
     * Event handler za klik na meni opciju "active users". Otvara se nova forma za prikaz tabele aktivnih korisnika
     * @param evt tipa java.awt.event.ActionEvent, predstavalja klik na meni "Active users"
     */
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        try{
        List<User> users = server.getActiveUsers();
        new ActiveUserForm(this, true, users).setVisible(true);
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(this, "Please start the server before checking for active users");
            System.out.println(ex.getMessage());
            Logger.getLogger(ServerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    /**
     * Event handler za klik na meni opciju "About". Otvara se novi prozor sa informacijama o serveru.
     * @param evt tipa java.awt.event.ActionEvent, predstavalja klik na meni "About"
     */
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "WIP");
    }//GEN-LAST:event_jMenuItem3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOff;
    private javax.swing.JButton btnOn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JLabel txtState;
    // End of variables declaration//GEN-END:variables
}
