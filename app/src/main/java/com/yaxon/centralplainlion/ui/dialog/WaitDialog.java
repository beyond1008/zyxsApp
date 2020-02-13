package com.yaxon.centralplainlion.ui.dialog;

import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.yaxon.centralplainlion.R;
import com.yaxon.centralplainlion.base.BaseDialog;
import com.yaxon.centralplainlion.base.BaseDialogFragment;

/**
 * Description:等待加载dialog
 * Created by kimiffy on 2020/1/13.
 */

public class WaitDialog {

    public static final class Builder extends BaseDialogFragment.Builder<Builder> {

        private final TextView mMessageView;

        public Builder(FragmentActivity activity) {
            super(activity);
            setContentView(R.layout.dialog_wait);
            setAnimStyle(BaseDialog.AnimStyle.TOAST);
            setBackgroundDimEnabled(false);
            setCancelable(false);

            mMessageView = findViewById(R.id.tv_wait_message);
        }

        public Builder setMessage(@StringRes int id) {
            return setMessage(getString(id));
        }

        public Builder setMessage(CharSequence text) {
            mMessageView.setText(text);
            mMessageView.setVisibility(text == null ? View.GONE : View.VISIBLE);
            return this;
        }
    }
}
