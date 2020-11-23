package ace.infosolutions.tournyapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ace.infosolutions.tournyapp.model.User;

import static ace.infosolutions.tournyapp.utils.Constants.PROFILE;
import static ace.infosolutions.tournyapp.utils.Constants.username;

public class AuthRepo {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public MutableLiveData<User> firebaseSignInWithGoogle(AuthCredential credential) {
        final MutableLiveData<User> authenticatedUserMutableLiveData = new MutableLiveData<>();
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if (firebaseUser != null) {
                        final String uid = firebaseUser.getUid();
                        final String name = firebaseUser.getDisplayName();
                        final String email = firebaseUser.getEmail();
                        db.collection(PROFILE).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    // if The account has been already created in the past
                                    if (task.getResult().exists()) {
                                        String uname = task.getResult().getString(username);
                                        //username is empty
                                        if (uname.equals("")) {
                                            User user = new User(uid, name, email, false, "", "");
                                            authenticatedUserMutableLiveData.setValue(user);
                                        }
                                        //username already exists
                                        else {
                                            User user = new User(uid, name, email, true);
                                            authenticatedUserMutableLiveData.setValue(user);
                                        }
                                    }
                                    //new account
                                    else {
                                        User user = new User(uid, name, email, false, "", "");
                                        db.collection(PROFILE).document(uid).set(user);
                                        authenticatedUserMutableLiveData.setValue(user);
                                    }
                                }
                            }
                        });

                    }
                } else {
                }
            }
        });
        return authenticatedUserMutableLiveData;
    }


}
