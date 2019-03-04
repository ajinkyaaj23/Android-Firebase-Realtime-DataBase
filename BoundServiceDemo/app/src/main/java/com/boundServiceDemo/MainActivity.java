package com.boundServiceDemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Bound service is directly connected or mutually dependent with component that initiates it
    // Allow other applications components such as activities to bind service which send request, recieve response,
    // two methods mainly -- bind service and unbind service -- one stops then other automatically stops
    // iBinder interface --- object can not be creates -- hence we use class Binder

    Button btnBind,btnUnBind,btnCallWebService;
    BoundServiceDemo boundServiceDemo;
    boolean isBind;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundServiceDemo.DemoBinder demoBinder = (BoundServiceDemo.DemoBinder)service;
            boundServiceDemo = demoBinder.getServiceInstane();
            isBind = true;
            boundServiceDemo.showMessage("Welcome to Bound Service");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBind = (Button) findViewById(R.id.boundService);
        btnUnBind = (Button) findViewById(R.id.unboundService);
        btnCallWebService = (Button) findViewById(R.id.callWebservice);
        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BoundServiceDemo.class);
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
            }
        });
        btnUnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBind){
                    unbindService(serviceConnection);
                    isBind = false;
                }
            }
        });
        btnCallWebService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (isBind) {
                 boundServiceDemo.webServiceCall();
             }else{
                 if (null == boundServiceDemo){
                     Toast.makeText(MainActivity.this,"null Service object",Toast.LENGTH_SHORT).show();
                 }else{
                     Toast.makeText(MainActivity.this,"isBind flag false",Toast.LENGTH_SHORT).show();
                 }
             }
            }
        });
    }
}
