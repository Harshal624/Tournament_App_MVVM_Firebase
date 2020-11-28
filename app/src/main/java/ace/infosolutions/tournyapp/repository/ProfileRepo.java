package ace.infosolutions.tournyapp.repository;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import ace.infosolutions.tournyapp.model.ProfileModel;

import static ace.infosolutions.tournyapp.utils.Constants.PROFILE;

public class ProfileRepo {
    private static final String TAG = "ProfileRepo";
    private static ProfileRepo instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public static ProfileRepo getInstance() {
        if (instance == null)
            instance = new ProfileRepo();
        return instance;
    }

    public MutableLiveData<ProfileModel> getProfileData() {

        final MutableLiveData<ProfileModel> profileModelMutableLiveData = new MutableLiveData<>();
        Log.e(TAG, "getProfileData: ");
        db.collection(PROFILE).document(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                final ProfileModel model = snapshot.toObject(ProfileModel.class);
                model.setSuccess(true);
                Log.e(TAG, "ProfileURL: " + model.getProfile_url());

                /*Glide.with(this).asBitmap().into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });*/
                profileModelMutableLiveData.setValue(model);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ProfileModel model = new ProfileModel();
                model.setSuccess(false);
                profileModelMutableLiveData.setValue(model);
            }
        });
        return profileModelMutableLiveData;
    }


    public MutableLiveData<Boolean> uploadProfilePic(Uri filePath) {
        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();

        StorageReference ref = storageReference.child("profilepics/" + auth.getUid());
        ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child("profilepics/" + auth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        db.collection("PROFILE").document(auth.getUid()).update("profile_url", uri.toString());
                        mutableLiveData.setValue(true);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mutableLiveData.setValue(false);
                Log.e(TAG, "onFailure: ", e);
            }
        });

        return mutableLiveData;

    }

    public MutableLiveData<String> getProfileURL() {
        Log.d(TAG, "getProfileURL: ");
        final MutableLiveData<String> getProfileURLMLData = new MutableLiveData<>();

        db.collection(PROFILE).document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String prof_url = task.getResult().getString("profile_url");
                    getProfileURLMLData.setValue(prof_url);
                }
            }
        });
        return getProfileURLMLData;
    }
}
