package com.rdxer.xxlibrary.HTTPUtils.listener;

import com.rdxer.xxlibrary.HTTPUtils.error.ProcessingError;

/**
 * Created by LXF on 16/6/8.
 */

public interface FailedListener {
    void onFailed(ProcessingError error);
}