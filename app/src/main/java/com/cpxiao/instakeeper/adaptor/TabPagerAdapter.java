package com.cpxiao.instakeeper.adaptor;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.cpxiao.instakeeper.fragment.BaseFragment;
import com.cpxiao.instakeeper.fragment.DownloadFragment;
import com.cpxiao.instakeeper.fragment.HowToUseFragment;

import java.lang.ref.WeakReference;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray<>();


    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return DownloadFragment.newInstance();
            case 1:
                return HowToUseFragment.newInstance("BaseFragment");
            case 2:
                return BaseFragment.newInstance("BaseFragment");
            case 3:
                return BaseFragment.newInstance("BaseFragment");
            default:
                return BaseFragment.newInstance("AAA");
        }
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        Fragment fragment = getFragment(position);
        if (null != fragment) {
            return fragment.toString();
        }

        if (position == 0) {
            return "Download";
        } else if (position == 1) {
            return "How to use";
        } else if (position == 2) {
            return "a";
        } else if (position == 3) {
            return "Download";
        } else {
            return "History";
        }
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        instantiatedFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        instantiatedFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Nullable
    public Fragment getFragment(final int position) {
        final WeakReference<Fragment> wr = instantiatedFragments.get(position);
        if (wr != null) {
            return wr.get();
        } else {
            return null;
        }
    }

}
