package ace.infosolutions.tournyapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import ace.infosolutions.tournyapp.databinding.ProfileAlertdialogBinding;
import ace.infosolutions.tournyapp.model.ProfileModel;
import ace.infosolutions.tournyapp.viewmodel.ProfileDataViewModel;

public class CustomDialog extends Dialog {
    ProfileAlertdialogBinding binding;
    private int type;
    private static final String TAG = "CustomDialog";
    ProfileDataViewModel viewModel;
    LifecycleOwner lifecycleOwner;
    ProfileModel model;

    public CustomDialog(@NonNull Context context, int type, LifecycleOwner lifecycleOwner, ProfileDataViewModel viewModel) {
        super(context);
        this.type = type;
        this.lifecycleOwner = lifecycleOwner;
        this.viewModel = viewModel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ProfileAlertdialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel.getProfileData().observe(lifecycleOwner, new Observer<ProfileModel>() {
            @Override
            public void onChanged(ProfileModel profileModel) {
                model = profileModel;
            }
        });

        switch (type) {
            case 1:
                setUpUsernameDialog();
                break;
            case 2:
                setUpPersonalInfoDialog();
                break;
        }

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyET();
            }
        });
    }

    private void verifyET() {
        if (isEmpty(binding.firstET)) {
            binding.firstET.setError("Empty");
        } else {
            switch (type) {
                case 1:
                    viewModel.changeUserName(binding.firstET.getText().toString().trim()).observe(lifecycleOwner, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean status) {
                            if (status) {
                                dismiss();
                            } else {
                                binding.firstET.setError("Username already exists");
                            }
                        }
                    });

                    break;
            }
        }
    }

    private void setUpPersonalInfoDialog() {
        binding.title.setText("Edit Personal Info");
        binding.firstET.setHint(model.getDiscord_id());
    }

    private void setUpUsernameDialog() {
        binding.title.setText("Enter your new username");
        binding.firstET.setHint(model.getUsername());
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
