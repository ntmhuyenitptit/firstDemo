/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author NamLee
 */
public class time extends Thread {

    private JLabel jl;
    private JTextArea jt;
    private int t;
    private boolean dieuKien;
    private ListenServer listenServer;

    public time() {
    }

    public time(JLabel jl, ListenServer listenServer, int t) {
        this.jl = jl;
        this.jt = jt;
        this.t = t;
        this.listenServer = listenServer;
        dieuKien = false;
        start();
    }

    public void setT(int t) {
        this.t = t;
    }

    public void setDieuKien(boolean dieuKien) {
        this.dieuKien = dieuKien;
    }

    @Override
    public void run() {
        String str = "";
        int phut = 0;
        int giay = 0;
        for (; t >= 0;) {
            if (dieuKien == true) {
                phut = t / 60;
                giay = t % 60;
                str = "";
                if (giay == 0 && phut > 0) {
                    giay = 59;
                }
                if (phut >= 10) {
                    str += phut + " : ";
                } else {
                    str += "0" + phut + " : ";
                }
                if (giay >= 10) {
                    str += giay;
                } else {
                    str += "0" + giay;
                }
                jl.setText(str);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                t--;
            }
        }
        try {
            listenServer.SendMessage(2, null);
        } catch (IOException ex) {
            Logger.getLogger(time.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
