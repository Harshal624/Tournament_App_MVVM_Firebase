package ace.infosolutions.tournyapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import ace.infosolutions.tournyapp.R;
import ace.infosolutions.tournyapp.databinding.FragmentHomeBinding;
import ace.infosolutions.tournyapp.ui.callbacks.MyItemClickListener;


public class HomeFragment extends Fragment{
    FragmentHomeBinding binding;

    private void setUpFragmentToolbar(View view) {
        TextView toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Home");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setUpFragmentToolbar(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadImages();
    }

    private void loadImages() {
    /*    Picasso.get().load(R.drawable.codmobilepic).fit().into(binding.codmobilepic);
        Picasso.get().load(R.drawable.pubgmobilepic).fit().into(binding.pubgmobilepic);
        Picasso.get().load(R.drawable.valorantpic).fit().into(binding.valorantpic);
        Picasso.get().load(R.drawable.freefirepic).fit().into(binding.freefirepic);*/
    }


}
