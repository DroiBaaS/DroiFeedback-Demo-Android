package com.droi.sdk.droifeedbackdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.FeedbackCoreHelper;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.feedback.DroiFeedbackError;
import com.droi.sdk.feedback.DroiFeedbackInfo;
import com.droi.sdk.feedback.DroiFeedbackReplyListener;
import com.droi.sdk.feedback.DroiFeedbackSendListener;
import com.droi.sdk.internal.DroiLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    TextView userIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.droi_example_activity_main);
        Log.i(TAG, FeedbackCoreHelper.getDeviceId());
        TextView appId = (TextView) findViewById(R.id.app_id);
        appId.setText(FeedbackCoreHelper.getAppId());
        Button feedback = (Button) findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DroiFeedback.callFeedback(MainActivity.this);
            }
        });
        Button userDefined = (Button) findViewById(R.id.user_defined);
        userDefined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DroiFeedback.setTitleBarColor(0xFFFFC125);
                DroiFeedback.setSendButtonColor(0xFFEEB422, 0xFFFFC125);
            }
        });
        Button setDefault = (Button) findViewById(R.id.set_default);
        setDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DroiFeedback.setTitleBarColor(0);
                DroiFeedback.setSendButtonColor(0, 0);
            }
        });
        userIdTextView = (TextView) findViewById(R.id.user_id);
        Button feedbackAfterSetUserId = (Button) findViewById(R.id.feedback_set_userid);
        feedbackAfterSetUserId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        Button sendButton = (Button) findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<File> fileList = new ArrayList<>();
                // 修改为您自己的图片地址
                fileList.add(new File("/storage/emulated/0/DCIM/.thumbnails/1467103627184.jpg"));
                fileList.add(new File("/storage/emulated/0/DCIM/.thumbnails/1482126861117.jpg"));
                DroiFeedback.submitFeedbackInBackground("13333333333", "内容", fileList, new DroiFeedbackSendListener() {
                    @Override
                    public void onReturned(DroiError droiError) {
                        switch (droiError.getCode()) {
                            case DroiFeedbackError.OK:
                                Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
                                break;
                            case DroiFeedbackError.FEEDBACK_NOT_FOUND:
                                Toast.makeText(getApplicationContext(), "联系方式或反馈内容为空", Toast.LENGTH_SHORT).show();
                                break;
                            case DroiFeedbackError.CONTACT_INVALID:
                                Toast.makeText(getApplicationContext(), "联系方式不合法", Toast.LENGTH_SHORT).show();
                                break;
                            case DroiFeedbackError.UPLOAD_IMAGE_FAILED:
                                Toast.makeText(getApplicationContext(), "文件上传失败", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "发生错误", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, droiError.toString());
                                break;
                        }
                    }
                });
            }
        });

        Button getButton = (Button) findViewById(R.id.get);
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DroiFeedback.getReplyInBackground(new DroiFeedbackReplyListener() {
                    @Override
                    public void onResult(DroiError droiError, List<DroiFeedbackInfo> list) {
                        if (droiError.isOk()) {
                            if (list != null && list.size() != 0) {
                                for (DroiFeedbackInfo info : list) {
                                    String image = "";
                                    List<String> imageList = info.getImageList();
                                    for (String imageString : imageList) {
                                        image += imageString;
                                    }
                                    Log.i(TAG, "image:" + image);
                                }
                            }
                        } else {
                            Log.i(TAG, droiError.toString());
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        userIdTextView.setText(DroiFeedback.getUserId());
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.feedback_set_userid_text));
        builder.setMessage(getString(R.string.feedback_set_userid_text));
        final EditText edittext = new EditText(this);
        builder.setView(edittext);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String userId = edittext.getText().toString();
                if (!userId.equals("")) {
                    DroiFeedback.setUserId(userId);
                }
                userIdTextView.setText(DroiFeedback.getUserId());
            }
        });
        builder.create().show();
    }
}
