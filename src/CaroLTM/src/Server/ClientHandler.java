/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Model.GPos;
import Model.KMessage;
import Model.Room;
import Model.Users;
import DAO.DataFunc;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Nam Lee
 */
//Xu ly nguoi choi theo luong
public class ClientHandler extends Thread {

    public Room room = null;

    private Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;

    public Users user;

    //Mac dinh thi hanh = true
    Boolean execute = true;

    ClientHandler(Socket socket) throws IOException {
        // Tao 1 luong su ly khach hang voi socket truyen vao

        this.socket = socket;
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        execute = true;
    }

    void ReceiveMessage(KMessage msg) throws IOException {
        //Nhan tin nhan

        //Kiem tra kieu tin nhan
        switch (msg.getType()) {
            case 0: {
                // Login

                Users temp = (Users) msg.getObject();
                DataFunc df = new DataFunc();
                user = df.checkLogin(temp.getUsername(), temp.getPassword());
                if (user != null) {
                    Boolean flag = true;
                    // Kiem tra coi da co ai dang nhap hay chua
                    for (ClientHandler cli : Main.lstClient) {
                        if (cli != this && cli.user != null && cli.user.getUsername().equalsIgnoreCase(user.getUsername())) {
                            user = null;
                            break;
                        }
                    }
                    if (user != null) {
                        System.out.println("Server: Xin chào " + user.getUsername());
                    }
                }

                // Gui kieu 0 va User
                SendMessage(0, user);
                break;
            }
            case 1: {
                //Kieu 1 dang ki user

                Users temp = (Users) msg.getObject();
                DataFunc df = new DataFunc();
                boolean succ;
                succ = df.checkAva(df.getId(temp.getUsername()));
                if (succ == true) {
                    SendMessage(1, temp.getUsername() + " Đã tồn tại!");
                    return;
                }

                //Neu chua ton tai thi dang ki
                succ = df.register(temp.getUsername(), temp.getPassword());
                if (succ == true) {
                    SendMessage(1, "Đăng kí thành công");
                }

                break;
            }
            case 2: {
                Users u = (Users) msg.getObject();
                if (room.client1.user.equals(u)) {
                    room.client1.SendMessage(35, "lose");
                    room.client2.SendMessage(35, "win");
                } else if (room.client2.user.equals(u)) {
                    room.client1.SendMessage(35, "win");
                    room.client2.SendMessage(35, "lose");
                }
                break;
            }
            case 10: {
                System.out.println(msg.getObject().toString());
                break;
            }
            //Room
            case 20: // Vao phong
            {
                room = Main.lstRoom.get(Integer.parseInt(msg.getObject().toString()));
                if (room.add(this) == false) //full
                {
                    int[] arrRoom = new int[Main.lstRoom.size()];
                    for (int i = 0; i < Main.lstRoom.size(); i++) {
                        arrRoom[i] = Main.lstRoom.get(i).countAvailable();
                    }
                    SendMessage(22, arrRoom);
                } else {
                    SendMessage(20, null);
                }

                break;
            }
            case 21: //Lay toan bo danh sach phong.
            {
                int[] arrRoom = new int[Main.lstRoom.size()];
                for (int i = 0; i < Main.lstRoom.size(); i++) {
                    arrRoom[i] = Main.lstRoom.get(i).countAvailable();
                }
                SendMessage(21, arrRoom);
                break;
            }
            case 29: {
                if (room.client1 != null && room.client2 == null || room.client1 == null && room.client2 != null) {
                    room.client1.SendMessage(101, "yes");
                } else {
                    room.client2.SendMessage(101, "no");
                }
                break;
            }
            case 28: {
                // lay danh sach?
                if (room.client1 != null && room.client2 != null) {
                    Users[] arrUser = new Users[2];
                    arrUser[0] = room.client1.user;
                    arrUser[1] = room.client2.user;
                    room.client1.SendMessage(34, arrUser);
                    room.client1.SendMessage(37, null);
                    room.client2.SendMessage(34, arrUser);
                    room.client2.SendMessage(36, null);
                }
                break;
            }
            case 30: // Lay ban co
            {
                GPos gPos = (GPos) msg.getObject();
                if (gPos != null) {
                    //in len man hinh vi tri danh
                    room.put(this, gPos);
                }

                if (room != null) {
                    for (ClientHandler cli : room.lstClientView) {
                        cli.SendMessage(30, room.pieceses);
                    }
                }

                break;
            }
            case 39: //Exit room
            {
                Users users = (Users) msg.getObject();
                if (room != null) {
                    if (room.client1.user == users) {
                        room.clientExit(room.client1);
                        room.client2.SendMessage(101, "yes");
                    } else {
                        room.clientExit(room.client2);
                        room.client1.SendMessage(101, "yes");
                    }
                }
                break;
            }
            case 40: //Chat
            {
                if (room != null) {
                    // Gui cho 2 client
                    if (room.client1 != this) {
                        room.client1.SendMessage(msg);
                    }
                    if (room.client2 != this) {
                        room.client2.SendMessage(msg);
                    }

                    for (ClientHandler cli : room.lstClientView) {
                        if (cli != this) {
                            cli.SendMessage(msg);
                        }
                    }
                }
                break;
            }
            case 41: //View
            {
                room = Main.lstRoom.get(Integer.parseInt(msg.getObject().toString()));
                room.lstClientView.add(this);
                SendMessage(20, null);
                break;
            }
            case 100: {
                try {
                    Users users = (Users) msg.getObject();
                    if (room.client1.user == users) {
                        room.client1.closeClient();
                    } else if (room.client2.user == users) {
                        room.client2.closeClient();
                    }
                    System.out.println("đóng thành công");
                } catch (Throwable ex) {
                    System.out.println(ex);
                }
                break;
            }
            case 103: {
                room.client2.SendMessage(38, null);
                room.client1.SendMessage(38, null);
                break;
            }
            case 104: {
                room.NewGame();
                room.client1.SendMessage(32, null);
                room.client2.SendMessage(32, null);
                room.client1.SendMessage(36, null);
                room.client2.SendMessage(36, null);
                break;
            }
        }
    }

    public void SendMessage(int type, Object obj) throws IOException {
//        Tao ra 1 tin nhan voi kieu va object truyen vao
        KMessage temp = new KMessage(type, obj);
//        gui tin nhan
        SendMessage(temp);
    }

    public void SendMessage(KMessage msg) throws IOException {
        //Tra ve dau luong va gui tin nhan qua socket
        outputStream.reset();
        outputStream.writeObject(msg);
        outputStream.flush();
    }

    public Boolean closeClient() throws Throwable {

// Thoat khoi phong
// Phong van con nguoi
        if (room != null) // Thong bao thoat room
        {
            try {
                room.lstClientView.remove(this);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        room.clientExit(this);
        Main.lstClient.remove(this);
        try {
            this.socket.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        System.out.println("Đã đóng client");
        return true;
    }

    @Override
    public void run() {

        while (execute == true) {
            try {
                Object o = inputStream.readObject();
                if (o != null) {
                    ReceiveMessage((KMessage) o);
                }
                //Guilai();
            } catch (IOException e) {
                try {
                    System.out.println("Có sự cô. Thử đóng");
                    sleep(5000);
                    execute = false;
                    System.out.println("Đã đóng");
                } catch (Throwable ex) {
                    System.out.println(ex);
                }
                System.out.println(e);
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

        }

    }

}
