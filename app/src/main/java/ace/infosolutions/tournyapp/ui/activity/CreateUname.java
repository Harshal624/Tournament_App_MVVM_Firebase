package ace.infosolutions.tournyapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import ace.infosolutions.tournyapp.databinding.ActivityCreateUnameBinding;
import ace.infosolutions.tournyapp.viewmodel.CreateUserNameViewModel;

public class CreateUname extends AppCompatActivity {
    ActivityCreateUnameBinding binding;
    EditText username;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    CreateUserNameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateUnameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        username = binding.enterUsername;
        initViewModel();

        binding.createuname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (username.getText().toString().trim().equals("") || username.getText().toString().trim().equals(null))
                    username.setError("Cannot be empty");
                else {
                    final String uName = username.getText().toString().trim();
                    if (uName.contains("/") || uName.contains(";"))
                        username.setError("Invalid username");
                    else {
                        viewModel.checkifUsernameExists(uName);
                        viewModel.userexists.observe(CreateUname.this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean aBoolean) {
                                if(aBoolean == true){
                                    username.setError("Username is already taken");
                                }
                                else{
                                    viewModel.getUsernameIsUpdated(uName);
                                    viewModel.isusernameupdated.observe(CreateUname.this, new Observer<Boolean>() {
                                        @Override
                                        public void onChanged(Boolean isupdated) {
                                            if(isupdated){
                                                Toast.makeText(CreateUname.this, "Username successfully added", Toast.LENGTH_SHORT).show();
                                                finishAffinity();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }
                                        }
                                    });
                                }
                            }
                        });

                    }

                }
            }
        });
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                finishAffinity();
                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
            }
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CreateUserNameViewModel.class);
    }
}
