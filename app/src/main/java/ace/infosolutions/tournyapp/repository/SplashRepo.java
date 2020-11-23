package ace.infosolutions.tournyapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ace.infosolutions.tournyapp.model.User;

import static ace.infosolutions.tournyapp.utils.Constants.PROFILE;
import static ace.infosolutions.tournyapp.utils.Constants.username;

public class SplashRepo {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    User user = new User();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private static volatile SplashRepo instance;


    public static SplashRepo getInstance(){
        //implementing double check locking to ensure thread safety
        if(instance == null){
            synchronized (SplashRepo.class){
                if(instance == null){
                    instance = new SplashRepo();
                }
            }
        }
        return instance;
    }

    public MutableLiveData<User> checkIfUserIsAuthenticatedInFirebase() {
        MutableLiveData<User> isUserAuthenticateInFirebaseMutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            user.isAuthenticated = false;
            isUserAuthenticateInFirebaseMutableLiveData.setValue(user);
        } else {
            user.uid = firebaseUser.getUid();
            user.isAuthenticated = true;
            isUserAuthenticateInFirebaseMutableLiveData.setValue(user);
        }
        return isUserAuthenticateInFirebaseMutableLiveData;
    }

    public MutableLiveData<Boolean> checkIfUserNameExists() {
        final MutableLiveData<Boolean> unameexists = new MutableLiveData<>();
        try {
            db.collection(PROFILE).document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        String uName = task.getResult().getString(username);
                        try {
                            if (uName.equals("") || uName.equals(null)) {
                                unameexists.setValue(false);
                            } else {
                                unameexists.setValue(true);
                            }
                        } catch (Exception e) {
                            firebaseAuth.signOut();
                        }
                    } else {
                        //failed
                        return;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return unameexists;
    }
}
