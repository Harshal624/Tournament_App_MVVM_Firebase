package ace.infosolutions.tournyapp.viewmodel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ace.infosolutions.tournyapp.model.ProfileModel;
import ace.infosolutions.tournyapp.repository.ProfileRepo;

public class ProfileViewModel extends ViewModel {
    ProfileRepo repo;
    private MutableLiveData<ProfileModel> profileData;
    private MutableLiveData<Boolean> uploadStatus;


    public void init() {
        if (profileData != null)
            return;
        repo = ProfileRepo.getInstance();
        profileData = repo.getProfileData();
    }

    public void init2(Uri filePath) {
        if (uploadStatus != null)
            return;
        repo = ProfileRepo.getInstance();
        uploadStatus = repo.uploadProfilePic(filePath);
    }


    public LiveData<ProfileModel> getProfileData() {
        return profileData;
    }

    public LiveData<Boolean> getUploadStatus() {
        return uploadStatus;
    }


}
