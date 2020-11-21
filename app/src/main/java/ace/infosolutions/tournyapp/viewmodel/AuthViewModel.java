package ace.infosolutions.tournyapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;

import ace.infosolutions.tournyapp.model.User;
import ace.infosolutions.tournyapp.repository.AuthRepo;

public class AuthViewModel extends AndroidViewModel {
    public LiveData<User> authenticatedUserLiveData;
    private AuthRepo repo;


    public AuthViewModel(@NonNull Application application) {
        super(application);
        repo = new AuthRepo();
    }

    public void signInWithGoogle(AuthCredential credential) {
        authenticatedUserLiveData = repo.firebaseSignInWithGoogle(credential);
    }
}
