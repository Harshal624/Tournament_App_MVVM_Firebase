package ace.infosolutions.tournyapp.model;

import com.google.firebase.firestore.Exclude;

public class User {
    public String uid;
    public String name;
    @SuppressWarnings("WeakerAccess")
    public String email;
    public String username;
    @Exclude
    public boolean isAuthenticated;
    @Exclude
    public boolean unameexists;



    public User(){}

    public User(String uid, String name, String email,boolean unameexists,String username) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.unameexists = unameexists;
        this.username = username;
    }

    public User(String uid, String name, String email, boolean unameexists) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.unameexists = unameexists;
    }
}
