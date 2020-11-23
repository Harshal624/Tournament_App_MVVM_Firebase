package ace.infosolutions.tournyapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ace.infosolutions.tournyapp.repository.CreateUserNameRepo;

public class CreateUserNameViewModel extends AndroidViewModel {
    public LiveData<Boolean> userexists;
    public LiveData<Boolean> isusernameupdated;
    CreateUserNameRepo repo;

    public CreateUserNameViewModel(@NonNull Application application) {
        super(application);
        repo = new CreateUserNameRepo();
    }

    public void checkifUsernameExists(String uName) {
        userexists = repo.checkIfUsernameexists(uName);
    }

    public void getUsernameIsUpdated(String uName) {
        isusernameupdated = repo.checkIfUsernameIsUpdated(uName);
    }
}
