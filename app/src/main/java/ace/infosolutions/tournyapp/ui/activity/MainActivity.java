package ace.infosolutions.tournyapp.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ace.infosolutions.tournyapp.R;
import ace.infosolutions.tournyapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    public ActivityMainBinding binding;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Bottomnav
        BottomNavigationView bottomNavigationView = binding.bottomnavigationview;
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController controller = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, controller);

    }

}
