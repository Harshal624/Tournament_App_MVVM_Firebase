package ace.infosolutions.tournyapp.repository;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ace.infosolutions.tournyapp.model.ProfileModel;
import ace.infosolutions.tournyapp.model.Username;
import ace.infosolutions.tournyapp.utils.Constants;

import static ace.infosolutions.tournyapp.utils.Constants.PROFILE;
import static ace.infosolutions.tournyapp.utils.Constants.USERNAMES;

public class ProfileDataRepo {
    private static final String TAG = "ProfileDataRepo";
    private static ProfileDataRepo instance;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private MutableLiveData<ProfileModel> profileModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Bitmap> profilePictureMutableLiveData = new MutableLiveData<>();

    public static synchronized ProfileDataRepo getInstance() {
        if (instance == null)
            instance = new ProfileDataRepo();
        return instance;
    }

    public void getProfileData() {
        Log.d(TAG, "getProfileData: ");
        db.collection(PROFILE).document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    profileModelMutableLiveData.setValue(task.getResult().toObject(ProfileModel.class));
                }
            }
        });
    }

    public void getProfilePicture() {
        db.collection(PROFILE).document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Picasso.get().load(task.getResult().getString("profile_url")).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            profilePictureMutableLiveData.setValue(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
                }
            }
        });
    }


    public LiveData<ProfileModel> getProfile() {
        return profileModelMutableLiveData;
    }


    public LiveData<Boolean> changeUserName(final String username) {
        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        db.collection(USERNAMES).document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if (snapshot.exists()) {
                    mutableLiveData.setValue(false);
                } else {
                    db.collection(PROFILE).document(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot snapshot) {
                            String current_username = snapshot.getString("username");
                            WriteBatch batch = db.batch();
                            DocumentReference oldusernameRef = db.collection(USERNAMES).document(current_username);
                            DocumentReference usernameRef = db.collection(USERNAMES).document(username);
                            DocumentReference profileRef = db.collection(PROFILE).document(auth.getUid());
                            Username model = new Username(true);
                            batch.set(usernameRef, model);
                            batch.delete(oldusernameRef);
                            batch.update(profileRef, Constants.username, username);
                            batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mutableLiveData.setValue(true);
                                    getProfileData();
                                }
                            });
                        }
                    });
                }
            }
        });

        return mutableLiveData;
    }

    public LiveData<Bitmap> getProfilePic() {
        return profilePictureMutableLiveData;
    }

    public void changeProfilePicture(Uri filePath) {

        StorageReference ref = storageReference.child("profilepics/" + auth.getUid());
        ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child("profilepics/" + auth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        db.collection("PROFILE").document(auth.getUid()).update("profile_url", uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                getProfilePicture();
                            }
                        });
                    }
                });
            }
        });
    }
}
