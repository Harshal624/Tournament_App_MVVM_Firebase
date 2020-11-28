package ace.infosolutions.tournyapp.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

import ace.infosolutions.tournyapp.R;
import ace.infosolutions.tournyapp.databinding.FragmentProfileBinding;
import ace.infosolutions.tournyapp.ui.activity.AuthActivity;
import ace.infosolutions.tournyapp.utils.CustomDialog;


public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private final int PICK_IMAGE_REQUEST = 231;
    FragmentProfileBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Bitmap bitmap;
    Uri filePath;
    // ProfileViewModel viewModel;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        // viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

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

        binding.editprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        binding.edituname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog dialog = new CustomDialog(getContext(), 1);
                dialog.show();
            }
        });

        binding.editpersprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog dialog = new CustomDialog(getContext(), 2);
                dialog.show();
            }
        });
        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_settingsFragment);
            }
        });
    }


    private void UploadImage() {
        try {
            if (filePath != null) {
                Log.e(TAG, "Filepath after upload click: " + filePath);
                final ProgressDialog progressDialog
                        = new ProgressDialog(getContext());
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

              /*  viewModel.init2(filePath);
                viewModel.getUploadStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isuploaded) {
                        if(isuploaded){
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            }
        } catch (Exception e) {
            Log.e(TAG, "UploadImage: " + e.getMessage());

        }
    }


    private void setUpFragmentToolbar(View view) {
        TextView toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("My Profile");
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
            Log.e(TAG, "Filepath before upload click: " + filePath);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     /*   viewModel.getProfileData().observe(getViewLifecycleOwner(), new Observer<ProfileModel>() {
            @Override
            public void onChanged(ProfileModel profileModel) {
                if (profileModel.isSuccess()) {
                    Log.d(TAG, "onChanged: Success" + profileModel.getUsername());
                    binding.username.setText(profileModel.getUsername());
                    binding.fullName.setText(profileModel.getFull_name());
                    binding.dob.setText(profileModel.getDob());
                    binding.location.setText(profileModel.getLocation());
                    binding.freefireId.setText(profileModel.getFreefire_id());
                    binding.valorantId.setText(profileModel.getValorant_id());
                    binding.codmobileId.setText(profileModel.getCodmobile_id());
                    binding.pubgId.setText(profileModel.getPubg_id());
                    binding.twitterId.setText(profileModel.getTwitter_id());
                    binding.discordId.setText(profileModel.getDiscord_id());
                    binding.facebookId.setText(profileModel.getFacebook_id());
                    binding.whatsappId.setText(profileModel.getWhatsapp_id());
                } else {
                    Log.d(TAG, "onChanged:Failed ");
                }
            }
        });*/
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


