package ace.infosolutions.tournyapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;

import ace.infosolutions.tournyapp.databinding.ProfileAlertdialogBinding;

public class CustomDialog extends Dialog {
    ProfileAlertdialogBinding binding;
    private int type;
    private String string1, string2, string3;

    public CustomDialog(@NonNull Context context, int type) {
        super(context);
        this.type = type;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ProfileAlertdialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        if (type == 2) {
            if (isEmpty(binding.firstET) && isEmpty(binding.secondET) && isEmpty(binding.thirdET)) {
                binding.firstET.setError("Empty");
                binding.secondET.setError("Empty");
                binding.thirdET.setError("Empty");
            } else {
                dismiss();
            }
        } else {
            if (isEmpty(binding.firstET)) {
                binding.firstET.setError("Empty");
            } else {
                dismiss();
            }
        }
    }

    private void setUpPersonalInfoDialog() {
        binding.title.setText("Edit Personal Info");
        binding.secondET.setVisibility(View.VISIBLE);
        binding.thirdET.setVisibility(View.VISIBLE);
        binding.firstET.setHint("Enter full name");
        binding.secondET.setHint("Enter DOB");
        binding.thirdET.setHint("Enter location");
    }

    private void setUpUsernameDialog() {
        binding.title.setText("Edit Username");
        binding.secondET.setVisibility(View.GONE);
        binding.thirdET.setVisibility(View.GONE);
        binding.firstET.setHint("Enter username");
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
