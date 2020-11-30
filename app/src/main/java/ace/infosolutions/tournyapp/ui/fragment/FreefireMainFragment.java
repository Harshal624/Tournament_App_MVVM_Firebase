package ace.infosolutions.tournyapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;

import ace.infosolutions.tournyapp.R;
import ace.infosolutions.tournyapp.databinding.FragmentFreefiremainBinding;
import ace.infosolutions.tournyapp.ui.adapter.PagerAdapter;


public class FreefireMainFragment extends Fragment {
    FragmentFreefiremainBinding binding;


    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if (isEnabled() && binding.tablayout.getSelectedTabPosition() == 0) {
                setEnabled(false);
                requireActivity().onBackPressed();
            } else {
                setEnabled(false);
                binding.viewpager.setCurrentItem(0);
            }
        }
    };
    PagerAdapter pagerAdapter;

    private void setUpFragmentToolbar(View view) {
        TextView toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Freefire");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFreefiremainBinding.inflate(inflater, container, false);
        setUpFragmentToolbar(binding.getRoot());
        pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, binding.tablayout.getTabCount());
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout));
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Handling onbackpressed in tab layout
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }
}
