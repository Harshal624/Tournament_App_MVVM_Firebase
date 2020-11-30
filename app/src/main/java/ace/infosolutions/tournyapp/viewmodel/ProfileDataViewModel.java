package ace.infosolutions.tournyapp.viewmodel;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import ace.infosolutions.tournyapp.model.ProfileModel;
import ace.infosolutions.tournyapp.repository.ProfileDataRepo;

public class ProfileDataViewModel extends ViewModel {

    private ProfileDataRepo repo;
    private LiveData<ProfileModel> profileModelLiveData;
    private LiveData<Bitmap> profilePicture;

    public ProfileDataViewModel() {
        repo = ProfileDataRepo.getInstance();
        if (profileModelLiveData == null)
            repo.getProfileData();
        if (profilePicture == null)
            repo.getProfilePicture();
        profileModelLiveData = repo.getProfile();
        profilePicture = repo.getProfilePic();
    }

    public LiveData<ProfileModel> getProfileData() {
        return profileModelLiveData;
    }

    public LiveData<Boolean> changeUserName(String username) {
        return repo.changeUserName(username);
    }

    public LiveData<Bitmap> getProfilePic() {
        return profilePicture;
    }

    public void changeProfilePicture(Uri filePath) {
        repo.changeProfilePicture(filePath);
    }

}
