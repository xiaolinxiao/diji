package com.zrdz.diji.baseui;


import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }

    /**
     * 吐司
     *
     * @param message
     */
    public void showToast(String message) {
        Toast.makeText(getActivity().getApplication(), message, Toast.LENGTH_SHORT).show();
    }
}
