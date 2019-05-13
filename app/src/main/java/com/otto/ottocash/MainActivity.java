package com.otto.ottocash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.otto.sdk.IConfig;
import com.otto.sdk.view.activities.account.CheckPhoneNumberActivity;
import com.otto.sdk.view.activities.account.activation.ActivationActivity;
import com.otto.sdk.view.activities.account.registration.RegistrationActivity;
import com.otto.sdk.view.activities.features.DashboardActivity;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.support.util.CacheUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

/*1. MainActivity extends BaseActivity is android library in sdk*/
public class MainActivity extends BaseActivity {

    /*2. Initialize ID*/
    @BindView(R.id.tvSaldoOttoCash)
    TextView tvSaldoOttoCash;
    @BindView(R.id.lyWidgetSdk)
    LinearLayout lyWidgetSdk;


    /*3. Initialize Data Type*/
    private String emoney;
    private String KEY_EMONEY = "EMONEY";

    String package_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /*4. Passing Package_Name + Activity_Name  TO SDK*/
        package_name = (this.getPackageName() + ".MainActivity");

        /*5. Call Function Validate Phone Number*/
        isExisting();

        /*6. EmoneyBalance OttoCash from SDK*/
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            emoney = extras.getString(KEY_EMONEY);
            tvSaldoOttoCash.setText(emoney);
            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            });
        }
    }


    /*7. Function Validate Phone Number*/
    protected void isExisting() {
        boolean is_existing = Boolean.parseBoolean(String.valueOf(CacheUtil.getPreferenceBoolean(String.valueOf(
                IConfig.SESSION_CHECK_PHONE_NUMBER), MainActivity.this)));

        if (is_existing == true) {
            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ActivationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("PACKAGE_NAME", package_name);
                    MainActivity.this.startActivity(intent);
                }
            });
        } else if (is_existing == false) {
            lyWidgetSdk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("PACKAGE_NAME", package_name);
                    MainActivity.this.startActivity(intent);
                }
            });
        }
    }


    @OnClick(R.id.btnCheckOut)
    public void onCheckOut() {
        Intent intent = new Intent(MainActivity.this, CheckOutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MainActivity.this.startActivity(intent);
    }

    @OnClick(R.id.btnNumberPhoneHost)
    public void onNumberPhone() {
        Intent intent = new Intent(MainActivity.this, CheckPhoneNumberActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MainActivity.this.startActivity(intent);
    }

}