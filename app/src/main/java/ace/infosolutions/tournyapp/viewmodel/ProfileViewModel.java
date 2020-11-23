package ace.infosolutions.tournyapp.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ace.infosolutions.tournyapp.repository.ProfileRepo;

public class ProfileViewModel extends AndroidViewModel {
    public LiveData<Bitmap> profile_url;
    public LiveData<Boolean> profile_upload_status;
    ProfileRepo repo;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repo = new ProfileRepo();
    }

    public void getProfileUrl() {
        profile_url = repo.getProfileUrl();
    }

    public void getProfileUploadStatus(Uri filePath) {
        profile_upload_status = repo.uploadProfile(filePath);
    }
}
