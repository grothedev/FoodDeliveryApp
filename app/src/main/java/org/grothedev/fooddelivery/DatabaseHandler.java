package org.grothedev.fooddelivery;

import org.grothedev.fooddelivery.dbtasks.AddUserTask;
import org.grothedev.fooddelivery.dbtasks.NumUsersTask;

/**
 * Created by thomas on 04/01/15.
 */
public class DatabaseHandler {



    public static String getUserEmail(int id){

        return "email@email.com";
    }

    public static boolean userIdExists(int id){



        return true;
    }


    //returns id of that email or -1
    public static int userEmailExists(String email){

        int users = numUsers();
        for (int i = 1; i <= users; i++){
            if (getUserEmail(i).equals(email)){
                return i;
            }
        }

        return -1;
    }

    public static int numUsers(){
        new NumUsersTask().execute();
        return 0;
    }

    //returns id
    public static void addUser(String email, String name){
        new AddUserTask().execute(email, name);
    }




}

