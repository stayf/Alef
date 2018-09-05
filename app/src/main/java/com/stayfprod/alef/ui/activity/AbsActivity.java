package com.stayfprod.alef.ui.activity;

import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stayfprod.alef.R;
import com.stayfprod.alef.data.entity.DataState;
import com.stayfprod.alef.util.Objects;

public abstract class AbsActivity extends AppCompatActivity {

    private Snackbar mSnackbar;

    protected void showSnackBar(View root, String msg) {
        showSnackBar(root, msg, null, null, false);
    }

    protected void hideSnackBar() {
        if (mSnackbar != null)
            mSnackbar.dismiss();
    }

    protected void showSnackBar(View root, String msg, View.OnClickListener action, String actionButton, boolean isIndefinite) {
        if (Objects.isNotBlank(msg)) {
            mSnackbar = Snackbar.make(root, msg, isIndefinite ? Snackbar.LENGTH_INDEFINITE : Snackbar.LENGTH_LONG);

            View view = mSnackbar.getView();
            TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setMaxLines(3);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            mSnackbar.setAction(actionButton, action).show();
        }
    }

    protected void processErrorWithAction(View view, DataState dataState, View.OnClickListener action) {
        showSnackBar(view, getErrorMsg(dataState), action, getText(R.string.btn_repeat).toString(), true);
    }

    protected void processError(DataState dataState) {
        showToast(getErrorMsg(dataState));
    }

    protected void processError(View view, DataState dataState) {
        showSnackBar(view, getErrorMsg(dataState));
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    protected void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void showToastLong(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private String getErrorMsg(DataState dataState) {
        String msg = dataState.getMsg();

        if (!isNetworkAvailable())
            msg = getText(R.string.error_no_internet).toString();

        return msg;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
