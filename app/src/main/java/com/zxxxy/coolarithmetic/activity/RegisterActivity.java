package com.zxxxy.coolarithmetic.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.base.Api;
import com.zxxxy.coolarithmetic.base.AppConfig;
import com.zxxxy.coolarithmetic.base.BaseActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit_name;
    private EditText edit_phone;
    private EditText edit_password;

    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
    }

    @Override
    protected void initViews() {
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_password = (EditText) findViewById(R.id.edit_password);

        edit_name.addTextChangedListener(nameWatcher);
        edit_phone.addTextChangedListener(phoneWatcher);
        edit_password.addTextChangedListener(passwordWatcher);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    public void register() {
        String phone = edit_phone.getText().toString().trim();
        String name = edit_name.getText().toString().trim();
        String password = edit_password.getText().toString().trim();
        Api.register(RegisterActivity.this, phone, name, password, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("注册事件", response.toString());
                try {
                    int code = response.getInt("code");
                    switch (code) {
                        case 414:
                            Toast.makeText(RegisterActivity.this, "该手机号已被注册", Toast.LENGTH_SHORT).show();
                            break;
                        case 200:
                            JSONObject userInfo = response.getJSONObject("info");
                            String name = userInfo.getString("name");
                            String token = userInfo.getString("token");
                            String accid = userInfo.getString("accid");

                            AppConfig.setUserName(RegisterActivity.this, name);
                            AppConfig.setUserToken(RegisterActivity.this, token);
                            AppConfig.setUserAccid(RegisterActivity.this, accid);

                            Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                            RegisterActivity.this.finish();
                            break;
                        default:
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "数据解析错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("注册失败", "" + statusCode);
            }
        });
    }

    private TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0 && edit_phone.getText().toString().trim().length() > 0 && edit_password.getText().toString().trim().length() > 5) {
                btn_register.setEnabled(true);
            }
        }
    };

    private TextWatcher phoneWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0 && edit_name.getText().toString().trim().length() > 0 && edit_password.getText().toString().trim().length() > 5) {
                btn_register.setEnabled(true);
            }
        }
    };

    private TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 5 && edit_name.getText().toString().trim().length() > 0 && edit_phone.getText().toString().trim().length() > 0) {
                btn_register.setEnabled(true);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
            default:
                break;
        }
    }
}
