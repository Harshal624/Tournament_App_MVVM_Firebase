package ace.infosolutions.tournyapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import ace.infosolutions.tournyapp.BuildConfig;
import ace.infosolutions.tournyapp.model.User;
import ace.infosolutions.tournyapp.R;
import ace.infosolutions.tournyapp.viewmodel.AuthViewModel;
import ace.infosolutions.tournyapp.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 234;
    ActivityAuthBinding binding;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth auth;
    AuthViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        initViewModel();
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.WEB_CLIENT_ID)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, options);
        binding.signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }


    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }

    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        viewModel.signInWithGoogle(authCredential);
        viewModel.authenticatedUserLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(final User user) {
                if (user != null && user.unameexists) {
                    finish();
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                } else if (user != null && !user.unameexists) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), CreateUname.class));
                }
            }
        });
    }
}
