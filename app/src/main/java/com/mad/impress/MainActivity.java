package com.mad.impress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.impress.ui.CustomerActivity;
import com.mad.impress.ui.ListCustomerActivity;
import com.mad.impress.ui.ShowAllActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button newCustomer,searchCustomer,showall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newCustomer = findViewById(R.id.newCustomer);
        searchCustomer = findViewById(R.id.searchCustomer);
        showall = findViewById(R.id.showAll);

        newCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CustomerActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
            }
        });

        searchCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListCustomerActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
            }
        });

        showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShowAllActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
            }
        });

        findViewById(R.id.ggbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseFirestore.getInstance().collection("CustomerM")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                       // Log.d(TAG, document.getId() + " => " + document.getData());

                                        HashMap<String, Object> hp = new HashMap<>();

                                        ArrayList<String> arrayList = (ArrayList<String>) document.get("mobiles");
                                        String bid = document.getString("bookName");
                                        String cid = document.getString("customerId");
                                        hp.put("id",bid+"_"+cid);
                                        hp.put("bookName",bid);
                                        hp.put("customerId",cid);
                                        hp.put("customerName",document.getString("customerName"));
                                        hp.put("mobiles",arrayList );
                                        hp.put("time", document.getTimestamp("time"));
                                        hp.put("note",document.getString("note"));

                                        FirebaseFirestore.getInstance().collection("Customer")
                                                .document(bid+"_"+cid)
                                                .set(hp)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "DocumentSnapshot written with ID: " );

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                         }
                                                });

                                    }
                                } else {
                                    Log.d("vvvvv", "Error getting documents: ", task.getException());
                                }
                            }
                        });



            }
        });


    }
}