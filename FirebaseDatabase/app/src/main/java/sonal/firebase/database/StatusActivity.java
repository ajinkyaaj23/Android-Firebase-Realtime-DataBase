package sonal.firebase.database;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class StatusActivity extends AppCompatActivity {

    TextView txtRole;

    private DatabaseReference mDatabase;
    private String dbData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        txtRole = (TextView) findViewById(R.id.userRole);

        final Intent in = getIntent();
        txtRole.setText(in.getStringExtra("role"));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Sonal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbData = dataSnapshot.getValue().toString();

                try{

                    JSONArray jsonArray = new JSONArray(dbData);

                    for (int i=0; i < jsonArray.length(); i++ ){

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);


                        if (in.getStringExtra("email").equalsIgnoreCase(jsonObject.getString("userName"))
                                && in.getStringExtra("password").equalsIgnoreCase(jsonObject.getString("password"))){
                           txtRole.setText(jsonObject.getString("Role"));

                        }

                    }



                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(StatusActivity.this,"Error in retrieving firebase database.",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
