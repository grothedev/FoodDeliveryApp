package org.grothedev.fooddelivery;

/**
 * Created by thomas on 04/01/15.
 */
public class DatabaseHandler {

    //currently not actually connecting to a database, just returning placeholder data

    public DatabaseHandler(){
        //set up stuff required to deal with database
    }

    public String getUserEmail(int id){

        return "email@email.com";
    }

    public boolean userIdExists(int id){
        return true;
    }

    public boolean userEmailExists(String email){
        return true;
    }
}
