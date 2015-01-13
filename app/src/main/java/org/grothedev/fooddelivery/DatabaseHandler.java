package org.grothedev.fooddelivery;

import android.util.Log;

import org.grothedev.fooddelivery.dbtasks.AddUserTask;
import org.grothedev.fooddelivery.dbtasks.IdOfEmailTask;
import org.grothedev.fooddelivery.dbtasks.NumUsersTask;

import java.util.concurrent.ExecutionException;

/**
 * Created by thomas on 04/01/15.
 */
public class DatabaseHandler {



    public static String getUserEmail(int id){

        return "grothe.tr@gmail.com";
    }

    public static boolean userIdExists(int id){



        return true;
    }


    public static void userEmailExists(String email){

        new IdOfEmailTask().execute(email);


    }

    public static int numUsers(){
        int users = -1;
        try {
            users = (Integer) new NumUsersTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return users;
    }

    //returns id
    public static void addUser(String email, String name){
        new AddUserTask().execute(email, name);
    }




}

