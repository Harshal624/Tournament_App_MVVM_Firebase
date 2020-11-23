package ace.infosolutions.tournyapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ace.infosolutions.tournyapp.repository.ProfileRepo;

public class ProfileViewModel extends ViewModel {

    ProfileRepo repo;
    private MutableLiveData<String> profile_url;
    private MutableLiveData<Boolean> profile_upload_status;

    public void initprofileUrl() {
        if (profile_url != null)
            return;
        repo = ProfileRepo.getInstance();
        profile_url = repo.getProfileUrl();
    }


    public void inituploadprofile() {
        if (profile_upload_status != null)
            return;
        repo = ProfileRepo.getInstance();
        // profile_upload_status = repo.uploadProfile();
    }

    public LiveData<String> getProfileurl() {
        return profile_url;
    }

    public LiveData<Boolean> getUploadStatus() {
        return profile_upload_status;
    }
}
