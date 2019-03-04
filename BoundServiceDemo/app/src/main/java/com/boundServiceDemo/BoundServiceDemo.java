package com.boundServiceDemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class BoundServiceDemo extends Service {

    IBinder iBinder = new DemoBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(BoundServiceDemo.this,"Service bind",Toast.LENGTH_SHORT).show();
        return iBinder;
    }

    class DemoBinder extends Binder{
        public BoundServiceDemo getServiceInstane(){
            return BoundServiceDemo.this;
        }
    }

    public void showMessage(String message){
        Toast.makeText(BoundServiceDemo.this,"Message: "+message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(BoundServiceDemo.this,"Service unBind",Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    public void webServiceCall(){
        Toast.makeText(BoundServiceDemo.this,"Calling Webservice",Toast.LENGTH_SHORT).show();
    }
}
