package ace.infosolutions.tournyapp.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ace.infosolutions.tournyapp.R;
import ace.infosolutions.tournyapp.databinding.FragmentParticipateBinding;

public class ParticipateFragment extends Fragment {
    FragmentParticipateBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentParticipateBinding.inflate(inflater,container,false);
        setUpFragmentToolbar(binding.getRoot());
        return binding.getRoot();
    }
    private void setUpFragmentToolbar(View view) {
        TextView toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Participate");
    }
}
