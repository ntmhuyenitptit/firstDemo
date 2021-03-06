/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import Model.KMessage;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nam Lee
 */
public class RoomFrame extends javax.swing.JFrame implements inReceiveMessage {

    ListenServer listenServer;

    /**
     * Creates new form RoomFrame
     */
    // Thiet lap phong
    public RoomFrame(ListenServer listenServer) {
        initComponents();

        setLocationRelativeTo(null);
        this.listenServer = listenServer;
        this.listenServer.receive = this;
        setIcon();
        try {
            listenServer.SendMessage(21, null);
        } catch (IOException ex) {
            Logger.getLogger(RoomFrame.class.getName()).log(Level.SEVERE, null, ex);
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
        btnJoinRoom = new javax.swing.JButton();
        lstRoom = new java.awt.List();
        btnViewRoom = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Cập nhật");
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        btnJoinRoom.setText("Vào phòng");
        btnJoinRoom.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnJoinRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnJoinRoomMouseClicked(evt);
            }
        });

        btnViewRoom.setText("Vào xem chơi");
        btnViewRoom.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnViewRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnViewRoomMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lstRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnJoinRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnViewRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lstRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnJoinRoom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnViewRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        //Cập nhật danh sách
        try {
            listenServer.SendMessage(21, null);
        } catch (Exception e) {
            System.out.println(e);
        }

    }//GEN-LAST:event_jButton1MouseClicked

    private void btnJoinRoomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnJoinRoomMouseClicked
        // TODO add your handling code here:
        //Tham gia một phòng
        try {
            // nếu còn chỗ thì cho vào :
            if (lstRoom.getSelectedIndex() >= 0) {
                listenServer.SendMessage(20, lstRoom.getSelectedIndex());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnJoinRoomMouseClicked

    private void btnViewRoomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewRoomMouseClicked
        // TODO add your handling code here:

        try {
            //Vao xem phòng
            if (lstRoom.getSelectedIndex() >= 0) {
                listenServer.SendMessage(41, lstRoom.getSelectedIndex());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnViewRoomMouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(RoomFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(RoomFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(RoomFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(RoomFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new RoomFrame(null).setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnJoinRoom;
    private javax.swing.JButton btnViewRoom;
    private javax.swing.JButton jButton1;
    private java.awt.List lstRoom;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/image/IconPlayer.jpg")));
    }

    @Override
    public void ReceiveMessage(KMessage msg) throws IOException {
        switch (msg.getType()) {
            case 20: // Lấy bàn cờ
            {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new Main(listenServer).setVisible(true);
                    }
                });
                this.dispose();
                break;
            }
            case 21: {
                // Cập nhật danh sách phòng
                lstRoom.removeAll();
                int[] arrRoom = (int[]) msg.getObject();
                String ct = "";
                for (int i = 0; arrRoom != null && i < arrRoom.length; i++) {
                    if (arrRoom[i] == 0) {
                        ct = "Đã dầy";
                    } else {
                        ct = "Còn " + arrRoom[i] + " chỗ trống.";
                    }
                    lstRoom.add("Phòng " + (i + 1) + ": " + ct);
                }
                lstRoom.select(0);
                break;
            }
            case 22: { //Room nay da full
                lstRoom.removeAll();
                int[] arrRoom = (int[]) msg.getObject();
                String ct = "";
                // cập nhật lại danh sách
                for (int i = 0; arrRoom != null && i < arrRoom.length; i++) {
                    if (arrRoom[i] == 0) {
                        ct = "Đã dầy";
                    } else {
                        ct = "Còn " + arrRoom[i] + " chỗ trống.";
                    }
                    lstRoom.add("Phòng " + (i + 1) + ": " + ct);
                }
                lstRoom.select(0);
                JOptionPane.showMessageDialog(null, "Phòng đã đầy", "Lỗi", 1);
                break;
            }
        }
    }
}
