package ace.infosolutions.tournyapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ace.infosolutions.tournyapp.databinding.ActivitySplashBinding;
import ace.infosolutions.tournyapp.model.User;
import ace.infosolutions.tournyapp.viewmodel.SplashViewModel;

import static ace.infosolutions.tournyapp.utils.Constants.splashdelay;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    SplashViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewModel();
        checkIfUserIsAuthenticated();
        getSupportActionBar().hide();
    }

    private void checkIfUserIsAuthenticated() {
        viewmodel.init1();
        viewmodel.isUserAuth().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                if (!user.isAuthenticated) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                            overridePendingTransition(0, 0);

                        }
                    }, splashdelay);
                } else {
                    viewmodel.init2();
                    viewmodel.usernameexist().observe(SplashActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean) {
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        overridePendingTransition(0, 0);
                                    }
                                }, splashdelay);
                            } else {
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), CreateUname.class));
                                        overridePendingTransition(0, 0);
                                    }
                                }, splashdelay);
                            }
                        }
                    });

                }

            }
        });
    }

    private void initViewModel() {
        viewmodel = new ViewModelProvider(this).get(SplashViewModel.class);
    }
}
