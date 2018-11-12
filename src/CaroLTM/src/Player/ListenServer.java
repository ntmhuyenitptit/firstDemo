/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import Model.KMessage;
import Model.Users;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Nam Lee
 */
// tạo luồng lắng nghe từ server
class ListenServer extends Thread {

    // Khởi tạo một vài luồng gửi và nhận
//     Phục vụ cho viec truyen dan du lieu
    Socket socket;
    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;
    InputStream inputStream;
    ObjectInputStream objectInputStream;

//      Thiết lập tai khoan
    public Users user;
//      Xu ly tin nhan nhan ve
    public inReceiveMessage receive;

//      Khởi tạo Class với socket truyền vào
    ListenServer(Socket socket) throws IOException {
        this.socket = socket;
//          Nối socket với luồng vào ra
        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);

    }
    static boolean execute = true;

    @Override
    public void run() {
        while (execute) {
            try {
                Object o = objectInputStream.readObject();
                if (o != null && receive != null) {
                    // Xử lý dữ liệu truyền vào
                    receive.ReceiveMessage((KMessage) o);
                }

            } catch (IOException e) {
                System.out.println(e);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }
        }
        closeLS();
    }

    public void closeLS() {
        //dong luong
        try {
            execute = false;
            this.socket.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void SendMessage(int type, Object obj) throws IOException {
        // Gửi dữ liệu lên server
        KMessage temp = new KMessage(type, obj);
        SendMessage(temp);
    }

    public void SendMessage(KMessage msg) throws IOException {
        objectOutputStream.reset();
        objectOutputStream.writeObject(msg);
    }
}
