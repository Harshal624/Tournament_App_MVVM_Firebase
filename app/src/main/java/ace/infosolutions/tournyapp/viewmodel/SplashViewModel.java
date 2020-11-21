package ace.infosolutions.tournyapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ace.infosolutions.tournyapp.model.User;
import ace.infosolutions.tournyapp.repository.SplashRepo;

public class SplashViewModel extends ViewModel {
    private SplashRepo repo;
    private MutableLiveData<User> isUserAuthenticatedLiveData;
    private MutableLiveData<Boolean> usernameexists;

    public void init1(){
        if(isUserAuthenticatedLiveData != null)
            return;
        repo = SplashRepo.getInstance();
        isUserAuthenticatedLiveData = repo.checkIfUserIsAuthenticatedInFirebase();
    }

    public void init2(){
        if(usernameexists != null)
            return;
        repo = SplashRepo.getInstance();
        usernameexists = repo.checkIfUserNameExists();
    }
    public LiveData<User> isUserAuth(){ return isUserAuthenticatedLiveData;}
    public LiveData<Boolean> usernameexist(){ return usernameexists;}
}
