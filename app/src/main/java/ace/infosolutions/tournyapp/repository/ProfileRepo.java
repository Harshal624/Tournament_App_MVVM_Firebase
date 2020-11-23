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

import ace.infosolutions.tournyapp.utils.Constants;

import static ace.infosolutions.tournyapp.utils.Constants.PROFILE;

public class ProfileRepo {
    private static final String TAG = "ProfileRepo";
    private static volatile ProfileRepo instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> uploadMutableLiveData = new MutableLiveData<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public static ProfileRepo getInstance() {
        //implementing double check locking to ensure thread safety
        if (instance == null) {
            synchronized (ProfileRepo.class) {
                if (instance == null) {
                    instance = new ProfileRepo();
                }
            }
        }
        return instance;
    }

    public MutableLiveData<String> getProfileUrl() {
        db.collection(PROFILE).document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String profile_url = task.getResult().getString(Constants.profile_url);
                    mutableLiveData.setValue(profile_url);
                }
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<Boolean> uploadProfile(final Uri filePath) {

        db.collection(PROFILE).document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    final String username = task.getResult().getString("username");
                    StorageReference ref = storageReference.child("profilepics/" + username);
                    ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.child("profilepics/" + username).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    db.collection("PROFILE").document(auth.getUid()).update("profile_url", uri.toString());
                                    uploadMutableLiveData.setValue(true);
                                    getProfileUrl();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            uploadMutableLiveData.setValue(false);
                            Log.e(TAG, "onFailure: ", e);
                        }
                    });

                } else {
                    uploadMutableLiveData.setValue(false);
                }
            }
        });

        return uploadMutableLiveData;
    }
}
