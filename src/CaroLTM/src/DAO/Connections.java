package DAO;

import Model.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nam lee Tao ket noi toi co so du lieu
 */
public class Connections {

    //Url database 
    static String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=caro;user=sa;password=123";
    static String linkJson = "C:\\Users\\Laptop TVT\\Documents\\NetBeansProjects\\CaroLTM\\src\\DAO\\data.json";

    public static Connection Newconnect() {
        Connection con = null;
        try {
            String url = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            Class.forName(url);
            con = DriverManager.getConnection(dbUrl);
        } catch (Exception ex) {
            System.out.println("Loi ket noi!");
            System.out.println(ex);
        }
        return con;
    }

    public static void writeJSON(ArrayList<Users> st) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FileWriter writer
                = new FileWriter(linkJson);
        writer.write(gson.toJson(st));
        writer.close();
        System.out.println("ghi thành công");
    }

    public static ArrayList<Users> readJSON() throws FileNotFoundException, IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(linkJson));
        Type collectionType;
        collectionType = new TypeToken<ArrayList<Users>>() {
        }.getType();
        ArrayList<Users> users = gson.fromJson(bufferedReader, collectionType);
        bufferedReader.close();
        return users;
    }
}
