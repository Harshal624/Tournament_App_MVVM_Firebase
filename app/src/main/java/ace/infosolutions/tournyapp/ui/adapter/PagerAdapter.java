package ace.infosolutions.tournyapp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ace.infosolutions.tournyapp.ui.fragment.CompletedTournyFragment;
import ace.infosolutions.tournyapp.ui.fragment.OnGoingTournyFragment;
import ace.infosolutions.tournyapp.ui.fragment.UpComingTournyFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private int tabsNum;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int tabsNum) {
        super(fm, behavior);
        this.tabsNum = tabsNum;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UpComingTournyFragment();
            case 1:
                return new OnGoingTournyFragment();
            case 2:
                return new CompletedTournyFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNum;
    }
}
