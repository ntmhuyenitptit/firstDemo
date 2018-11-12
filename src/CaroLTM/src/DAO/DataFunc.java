package DAO;

import Model.Users;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nam Lee
 */
public class DataFunc {

    //Tao mot connect
//    Connection con = Connections.Newconnect();
    Connections con = new Connections();

    //Lay danh sach user
    public List<Users> getUserList() {

        try {
            return con.readJSON();
        } catch (FileNotFoundException ex) {
            System.out.println("Loi doc ghi");
            return null;
        }
    }

    public Users checkLogin(String username, String password) {

        try {
            ArrayList<Users> usl = new ArrayList<Users>();
            usl = con.readJSON();
            for (Users users : usl) {
                if (users.getUsername().trim().equalsIgnoreCase(username) && users.getPassword().trim().equalsIgnoreCase(password)) {
                    System.out.println("Dang nhap thanh cong!");
                    return users;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Loi doc ghi");
            return null;
        }
        return null;
    }

    public boolean register(String username, String password) {
        ArrayList<Users> usl = new ArrayList<Users>();

        try {
            usl = con.readJSON();
            int id = 0;
            int max = 0;
            for (Users users : usl) {
                if (users.getId() > max) {
                    max = users.getId();
                }
            }
            id = max + 1;
            usl.add(new Users(id, username, password));
            con.writeJSON(usl);
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            return false;
        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean checkAva(int id) {
        //Kiem tra id da co hay chua?
        ArrayList<Users> usl = new ArrayList<Users>();

        try {
            usl = con.readJSON();
            for (Users users : usl) {
                if (users.getId() == id) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        return false;
    }

    public boolean updateUser(Users user) throws SQLException {
        //cap nhat user
        ArrayList<Users> usl = new ArrayList<>();

        try {
            usl = con.readJSON();
            int i = 0;
            for (Users users : usl) {
                if (users.getId() == user.getId()) {
                    usl.set(i, user);
                    break;
                }
                i++;
            }
            con.writeJSON(usl);
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            return false;
        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean updateWin(int id, int win) throws SQLException {
        //Cap nhat ti so chien thang

        //kiem tra id co ton tai?
        if (checkAva(id) == false) {
            return false;
        }
        // cap nhat bang user -> cai dat win -> tai Id duoc xac dinh
        ArrayList<Users> usl = new ArrayList<Users>();

        try {
            usl = con.readJSON();
            for (Users users : usl) {
                if (users.getId() == id) {
                    users.setWin(win);
                    break;
                }
            }
            con.writeJSON(usl);
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            return false;
        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean updateLose(int id, int lose) throws SQLException {

        if (checkAva(id) == false) {
            return false;
        }

        //cap nhat ti so thua
        ArrayList<Users> usl = new ArrayList<Users>();

        try {
            usl = con.readJSON();
            for (Users users : usl) {
                if (users.getId() == id) {
                    users.setWin(lose);
                    break;
                }
            }
            con.writeJSON(usl);
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            return false;
        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public int getId(String username) {

        // tim id tu username
        ArrayList<Users> usl = new ArrayList<Users>();

        try {
            usl = con.readJSON();
            for (Users users : usl) {
                if (users.getUsername().equalsIgnoreCase(username)) {
                    return users.getId();
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return -1;
    }

    public Users getUser(String username) {

        //lay du lieu 1 user tu username
        ArrayList<Users> usl = new ArrayList<Users>();

        try {
            usl = con.readJSON();
            for (Users users : usl) {
                if (users.getUsername() == username) {
                    return users;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return null;
    }

    public boolean DeleteUser(int Id) {

//        xoa user voi id
        ArrayList<Users> usl = new ArrayList<>();

        try {
            usl = Connections.readJSON();

            for (Users users : usl) {
                if (users.getId() == Id) {
                    usl.remove(users);
                    Connections.writeJSON(usl);
                    return true;
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return false;
    }

}
