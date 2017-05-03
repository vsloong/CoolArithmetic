package com.zxxxy.coolarithmetic.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.friend.model.AddFriendData;
import com.zxxxy.coolarithmetic.R;
import com.zxxxy.coolarithmetic.base.AppConfig;
import com.zxxxy.coolarithmetic.base.BaseActivity;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit_accid;
    private TextView text_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
    }

    @Override
    protected void initViews() {
        Button btn_back = (Button) findViewById(R.id.btn_back);

        edit_accid = (EditText) findViewById(R.id.edit_accid);
        TextView text_ok = (TextView) findViewById(R.id.text_ok);
        text_result = (TextView) findViewById(R.id.text_result);


        text_ok.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.text_ok:
                goSearch();
                break;
            default:
                break;
        }
    }

    private void goSearch() {
        String accid = edit_accid.getText().toString().trim();

        if (TextUtils.isEmpty(accid)) {
            Toast.makeText(SearchActivity.this, "请输入对方账户", Toast.LENGTH_SHORT).show();
        } else {
            final VerifyType verifyType = VerifyType.VERIFY_REQUEST; // 发起好友验证请求
            String msg = AppConfig.getUserName(SearchActivity.this);
            NIMClient.getService(FriendService.class).addFriend(new AddFriendData(accid, verifyType, msg))
                    .setCallback(new RequestCallback<Void>() {
                        @Override
                        public void onSuccess(Void param) {
                            Log.e("添加好友请求", "成功！");
                            text_result.setText("添加好友请求成功！");
                        }

                        @Override
                        public void onFailed(int code) {
                            Log.e("添加好友请求", "失败！" + code);
                            text_result.setText("该用户不存在！");
                        }

                        @Override
                        public void onException(Throwable exception) {
                            Log.e("添加好友请求", "错误！" + exception.toString());
                            text_result.setText("添加好友失败，请稍后再试！");
                        }
                    });
        }
    }
}
