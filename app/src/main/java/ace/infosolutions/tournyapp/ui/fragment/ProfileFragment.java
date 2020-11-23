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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import ace.infosolutions.tournyapp.R;
import ace.infosolutions.tournyapp.databinding.FragmentProfileBinding;
import ace.infosolutions.tournyapp.ui.activity.AuthActivity;
import ace.infosolutions.tournyapp.viewmodel.ProfileViewModel;


public class ProfileFragment extends Fragment {
    private final int PICK_IMAGE_REQUEST = 231;
    FragmentProfileBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Bitmap bitmap;
    Uri filePath;
    ProfileViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        setUpFragmentToolbar(binding.getRoot());
        initViewModel();
        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadProfilePic();
        binding.signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                getActivity().finishAffinity();
                startActivity(new Intent(getContext(), AuthActivity.class));
                Toast.makeText(getContext(), "Signed out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        binding.editprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editUsernameFragment);
                SelectImage();
            }
        });
        binding.uploadprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });
        setUpDummyTeams(view);
    }

    public void loadProfilePic() {
        viewModel.initprofileUrl();
        viewModel.getProfileurl().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String profile_url) {
                try {
                    Picasso.get().load(profile_url).into(binding.circleImageView);
                } catch (IllegalArgumentException e) {
                    //load dummy photo here
                    Toast.makeText(getContext(), "Failed to load the profile picture, please upload another one", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UploadImage() {
        try {
            if (filePath != null) {
                final ProgressDialog progressDialog
                        = new ProgressDialog(getContext());
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
              /*  db.collection("PROFILE").document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            final String username = task.getResult().getString("username");
                            StorageReference ref = storageReference.child("profilepics/" + username);
                            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    long kbytes = taskSnapshot.getTotalByteCount() / 1024;
                                    Toast.makeText(getContext(), kbytes + "kb uploaded", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    storageReference.child("profilepics/" + username).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            db.collection("PROFILE").document(auth.getUid()).update("profile_url", uri.toString());
                                            binding.editprofilepic.setVisibility(View.VISIBLE);
                                            binding.uploadprofilepic.setVisibility(View.GONE);
                                        }
                                    });
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
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed to upload the image!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
                viewModel.inituploadprofile(filePath);
                viewModel.getUploadStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean status) {
                        if (status) {
                            binding.editprofilepic.setVisibility(View.VISIBLE);
                            binding.uploadprofilepic.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Successfully uploaded", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed to upload the profile pic", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Failed to upload the image", Toast.LENGTH_SHORT).show();
        }
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
            filePath = data.getData();
            binding.editprofilepic.setVisibility(View.GONE);
            binding.uploadprofilepic.setVisibility(View.VISIBLE);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                binding.circleImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


