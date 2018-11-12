/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Users;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author NamLee
 */
public class updateDataInJson {
    static int i = 5;
    public static void main(String[] args) throws IOException {
        DataFunc df = new DataFunc();
//        ArrayList<Users> us = new ArrayList<Users>();
//        us = (ArrayList<Users>) df.getUserList();
        Connections con = new Connections();
//        con.writeJSON(us);
//            Test read json
        ArrayList<Users> us1 = new ArrayList<Users>();
        us1 = con.readJSON();
        System.out.println(us1.size());
        System.out.println(i++);
    }
}
