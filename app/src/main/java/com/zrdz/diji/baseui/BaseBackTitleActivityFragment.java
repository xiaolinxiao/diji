package com.zrdz.diji.baseui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zrdz.diji.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class BaseBackTitleActivityFragment extends Fragment {

    public BaseBackTitleActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_back_title, container, false);
    }
}
