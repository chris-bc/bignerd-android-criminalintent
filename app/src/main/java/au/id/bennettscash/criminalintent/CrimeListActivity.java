package au.id.bennettscash.criminalintent;


import android.support.v4.app.Fragment;

/**
 * Created by chris on 15/02/15.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
