package io.jiantao.utils.android.helper;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import java.io.Serializable;

/**
 * @author jiantao
 */
public final class BundleHelper {
    private Bundle mBundle;

    public static BundleHelper newInstance() {
        return new BundleHelper();
    }

    private BundleHelper() {
        mBundle = new Bundle();
    }

    public BundleHelper put(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    public BundleHelper put(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public BundleHelper put(String key, long value) {
        mBundle.putLong(key, value);
        return this;
    }

    public BundleHelper put(String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public BundleHelper put(String key, Serializable value) {
        mBundle.putSerializable(key, value);
        return this;
    }

    public BundleHelper put(String key, Bundle value) {
        mBundle.putBundle(key, value);
        return this;
    }

    public BundleHelper putParcelable(String key, Parcelable data) {
        mBundle.putParcelable(key, data);
        return this;
    }

    public <T extends Fragment> T into(T fragment) {
        fragment.setArguments(build());
        return fragment;
    }

    public <T extends android.app.Fragment> T into(T fragment) {
        fragment.setArguments(build());
        return fragment;
    }

    public Bundle build() {
        return mBundle;
    }
}