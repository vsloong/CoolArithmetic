package com.cooloongwu.coolarithmetic.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooloongwu.coolarithmetic.R;
import com.cooloongwu.coolarithmetic.base.BaseActivity;
import com.cooloongwu.coolarithmetic.utils.StartActivityUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_clear_phone;
    private ImageView img_clear_password;

    private EditText edit_phone;
    private EditText edit_password;

    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    @Override
    protected void initViews() {
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_password = (EditText) findViewById(R.id.edit_password);

        img_clear_phone = (ImageView) findViewById(R.id.img_clear_phone);
        img_clear_password = (ImageView) findViewById(R.id.img_clear_password);
        ImageView img_eye_password = (ImageView) findViewById(R.id.img_eye_password);

        btn_login = (Button) findViewById(R.id.btn_login);

        TextView text_forget_password = (TextView) findViewById(R.id.text_forget_password);

        edit_phone.addTextChangedListener(new PhoneTextWatcher());
        edit_password.addTextChangedListener(new PasswordTextWatcher());

        btn_login.setOnClickListener(this);
        text_forget_password.setOnClickListener(this);
        img_eye_password.setOnClickListener(this);
        img_clear_phone.setOnClickListener(this);
        img_clear_password.setOnClickListener(this);
    }

    private class PhoneTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            img_clear_phone.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);

            if (s.length() == 11 && edit_password.getText().toString().trim().length() > 0) {
                btn_login.setEnabled(true);
            }
        }
    }

    private class PasswordTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            img_clear_password.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
            if (s.length() > 0 && edit_phone.getText().toString().trim().length() == 11) {
                btn_login.setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Log.e("登录按钮", "点击事件");
                StartActivityUtils.startMainActivity(LoginActivity.this);
                finish();
                break;
            case R.id.img_clear_phone:
                edit_phone.setText("");
                break;
            case R.id.img_clear_password:
                edit_password.setText("");
                break;
            default:
                break;
        }
    }
}
