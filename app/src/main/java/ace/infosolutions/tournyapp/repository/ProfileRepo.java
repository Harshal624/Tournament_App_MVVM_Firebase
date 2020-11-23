package ace.infosolutions.tournyapp.repository;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ace.infosolutions.tournyapp.utils.Constants;

import static ace.infosolutions.tournyapp.utils.Constants.PROFILE;

public class ProfileRepo {
    private static final String TAG = "ProfileRepo";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MutableLiveData<Bitmap> mutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> uploadMutableLiveData = new MutableLiveData<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public MutableLiveData<Bitmap> getProfileUrl() {
        db.collection(PROFILE).document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String profile_url = task.getResult().getString(Constants.profile_url);
                    Picasso.get().load(profile_url).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            mutableLiveData.setValue(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            Log.e(TAG, "onBitmapFailed: ", e);
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
                }
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<Boolean> uploadProfile(final Uri filePath) {
        StorageReference ref = storageReference.child("profilepics/" + auth.getUid());
        ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child("profilepics/" + auth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        return uploadMutableLiveData;
    }
}
