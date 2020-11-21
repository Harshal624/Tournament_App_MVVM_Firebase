package ace.infosolutions.tournyapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import ace.infosolutions.tournyapp.model.User;
import ace.infosolutions.tournyapp.model.Username;

import static ace.infosolutions.tournyapp.utils.Constants.PROFILE;
import static ace.infosolutions.tournyapp.utils.Constants.USERNAMES;
import static ace.infosolutions.tournyapp.utils.Constants.username;

public class CreateUserNameRepo {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();



    public MutableLiveData<Boolean> checkIfUsernameexists(String uName) {
        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();

        db.collection(USERNAMES).document(uName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        mutableLiveData.setValue(true);
                    }
                    else{
                        mutableLiveData.setValue(false);
                    }
                }
            }
        });
        return mutableLiveData;
    }

    public LiveData<Boolean> checkIfUsernameIsUpdated(String uName) {
        Username uNamePOJO = new Username(true);
        WriteBatch batch = db.batch();
        DocumentReference profileRef = db.collection(PROFILE).document(auth.getUid());
        DocumentReference unameRef = db.collection(USERNAMES).document(uName);
        batch.update(profileRef,username,uName);
        batch.set(unameRef,uNamePOJO);
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mutableLiveData.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mutableLiveData.setValue(false);
            }
        });

        return mutableLiveData;
    }
}
