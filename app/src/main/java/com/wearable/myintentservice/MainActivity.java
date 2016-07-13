package com.wearable.myintentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    public final static String ACTION_BROADCAST_FOO =
            "com.wearable.myintentservice.ACTION_BROADCAST_FOO";
    public final static String ACTION_BROADCAST_BAZ =
            "com.wearable.myintentservice.ACTION_BROADCAST_BAZ";

    private boolean flag = false;

    MyReceiver receiver_foo, receiver_baz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter_foo = new IntentFilter(ACTION_BROADCAST_FOO);
        receiver_foo = new MyReceiver();
        registerReceiver(receiver_foo, filter_foo);

        IntentFilter filter_baz = new IntentFilter(ACTION_BROADCAST_BAZ);
        receiver_baz = new MyReceiver();
        registerReceiver(receiver_baz, filter_baz);

        Button startBtn = (Button) findViewById(R.id.start_btn);
        final EditText stringInput = (EditText) findViewById(R.id.editText);

        if (startBtn != null) {
            startBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String input = "Unknown";
                    if (stringInput != null && stringInput.getText() !=null) {
                        input = stringInput.getText().toString();
                    }

                    String input1 = input + "1";
                    String input2 = input + "2";
                    String input3 = input + "3";
                    String input4 = input + "4";

                    MyIntentService.startActionFoo(MainActivity.this, input1, input2);
                    MyIntentService.startActionBaz(MainActivity.this, input3, input4);

                }
            });
        }
    }


    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "OnReceive() called.");

            final String action = intent.getAction();
            if (ACTION_BROADCAST_FOO.equals(action)) {
                String foo_out_msg = intent.getStringExtra("foo_out");
                TextView fooTV = (TextView) findViewById(R.id.foo_tv);
                if (fooTV != null) fooTV.setText(foo_out_msg);

            } else if (ACTION_BROADCAST_BAZ.equals(action)) {
                String baz_out_msg = intent.getStringExtra("baz_out");
                TextView bazTV = (TextView) findViewById(R.id.baz_tv);
                if (bazTV != null) bazTV.setText(baz_out_msg);

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver_foo);
        unregisterReceiver(receiver_baz);
    }
}
