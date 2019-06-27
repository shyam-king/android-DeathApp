import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainScreenAdapter extends FragmentPagerAdapter {
    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return null;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }

    public MainScreenAdapter(FragmentManager fm) {
        super(fm);
    }
}
