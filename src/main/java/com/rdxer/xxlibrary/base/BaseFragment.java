package com.rdxer.xxlibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by LXF on 16/6/13.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public Context getContext() {
        return getActivity();
    }

}