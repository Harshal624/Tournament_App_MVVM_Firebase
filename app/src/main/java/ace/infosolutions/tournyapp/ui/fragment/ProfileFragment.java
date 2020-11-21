package ace.infosolutions.tournyapp.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import ace.infosolutions.tournyapp.BuildConfig;
import ace.infosolutions.tournyapp.R;
import ace.infosolutions.tournyapp.databinding.FragmentProfileBinding;
import ace.infosolutions.tournyapp.ui.activity.AuthActivity;

import static ace.infosolutions.tournyapp.utils.Constants.PROFILE;


public class ProfileFragment extends Fragment {
    private final int PICK_IMAGE_REQUEST = 231;
    FragmentProfileBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        setUpFragmentToolbar(binding.getRoot());
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                getActivity().finishAffinity();
                startActivity(new Intent(getContext(), AuthActivity.class));
                Toast.makeText(getContext(), "Signed out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        binding.edituname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editUsernameFragment);
                SelectImage();
            }
        });
        setUpDummyTeams(view);
    }

    private void setUpFragmentToolbar(View view) {
        TextView toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("My Profile");
    }

    private void setUpDummyTeams(View view) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View team_view = inflater.inflate(R.layout.my_team, (ViewGroup) view, false);
        View team_view2 = inflater.inflate(R.layout.my_team, (ViewGroup) view, false);
        View team_view3 = inflater.inflate(R.layout.my_team, (ViewGroup) view, false);
        TextView team_title = team_view2.findViewById(R.id.team_title);
        ImageView team_pic = team_view2.findViewById(R.id.team_pic);
        TextView team_type = team_view2.findViewById(R.id.team_type);
        TextView team_title2 = team_view3.findViewById(R.id.team_title);
        ImageView team_pic2 = team_view3.findViewById(R.id.team_pic);
        TextView team_type2 = team_view3.findViewById(R.id.team_type);
        TextView team_title3 = team_view3.findViewById(R.id.team_title);
        ImageView team_pic3 = team_view3.findViewById(R.id.team_pic);
        TextView team_type3 = team_view3.findViewById(R.id.team_type);
        team_title.setText("Dragons");
        team_type.setText("Duo");
        team_pic.setImageResource(R.drawable.dragonslogo);
        team_title2.setText("Dragons");
        team_type2.setText("Squad");
        team_pic2.setImageResource(R.drawable.harvesterteamlogo);
        team_title3.setText("Crow Gaming");
        team_type3.setText("Squad");
        team_pic3.setImageResource(R.drawable.crowgamingloogo);
        binding.scrollLinearlayout.addView(team_view);
        binding.scrollLinearlayout.addView(team_view2);
        binding.scrollLinearlayout.addView(team_view3);
    }

    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK &&
                data != null && data.getData() != null) {
            final Uri filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                binding.circleImageView.setImageBitmap(bitmap);

                if (filePath != null) {
                    final ProgressDialog progressDialog
                            = new ProgressDialog(getContext());
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    StorageReference ref = storageReference.child("profilepics/" + UUID.randomUUID().toString());
                    ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            long kbytes = taskSnapshot.getTotalByteCount() / 1024;
                            Toast.makeText(getContext(), kbytes + "kb uploaded", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress
                                    = (100.0
                                    * taskSnapshot.getBytesTransferred()
                                    / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage(
                                    "Uploaded "
                                            + (int) progress + "%");
                        }
                    });


                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                Toast.makeText(getContext(), "Failed to upload the image", Toast.LENGTH_SHORT).show();
            }

        }
    }
}


