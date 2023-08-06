package com.mad.impress.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.impress.MainActivity;
import com.mad.impress.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    EditText bookName, customerId, customerName, mobile1,mobile2, note;
    Button submitbtn;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        bookName = findViewById(R.id.bookname);
        customerId = findViewById(R.id.customerId);
        customerName = findViewById(R.id.customerName);
        mobile1 = findViewById(R.id.mobile1);
        mobile2 = findViewById(R.id.mobile2);
        note = findViewById(R.id.note);
        db = FirebaseFirestore.getInstance();

        submitbtn = findViewById(R.id.submitbtn);



        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bookName.getText().toString().trim().isEmpty()){
                    Toast.makeText(CustomerActivity.this, "Enter Book Name", Toast.LENGTH_SHORT).show();
                } else if(customerId.getText().toString().trim().isEmpty()){
                    Toast.makeText(CustomerActivity.this, "Enter Customer Id", Toast.LENGTH_SHORT).show();
                } else if(customerName.getText().toString().trim().isEmpty()){
                    Toast.makeText(CustomerActivity.this, "Enter Customer Name", Toast.LENGTH_SHORT).show();
                } else if(mobile1.getText().toString().trim().isEmpty()){
                    Toast.makeText(CustomerActivity.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                }  else {
                    submitbtn.setEnabled(false);

                    String id = bookName.getText().toString().trim()+"_"+customerId.getText().toString().trim();

                    db.collection("Customer").document(id)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    submitbtn.setEnabled(true);
                                    Toast.makeText(CustomerActivity.this, "ID Exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("TAG", "No such document");

                                    List<String> mobiles = new ArrayList<>();
                                    mobiles.add(mobile1.getText().toString().trim());

                                    if(!mobile2.getText().toString().trim().isEmpty()){
                                        mobiles.add(mobile2.getText().toString().trim());
                                    }

                                    HashMap<String, Object> hp = new HashMap<>();

                                    hp.put("id",id);
                                    hp.put("bookName",bookName.getText().toString().trim());
                                    hp.put("customerId",customerId.getText().toString().trim());
                                    hp.put("customerName", customerName.getText().toString().trim());
                                    hp.put("mobiles",mobiles );
                                    hp.put("time", new Timestamp(new Date()));
                                    hp.put("note",note.getText().toString().trim()+"");


                                    db.collection("Customer")
                                            .document(id)
                                            .set(hp)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("TAG", "DocumentSnapshot written with ID: " );
                                                    Toast.makeText(CustomerActivity.this, "Entry Added", Toast.LENGTH_SHORT).show();
                                                    submitbtn.setEnabled(true);
                                                    Intent i = new Intent(CustomerActivity.this, CustomerActivity.class);
                                                    startActivity(i);
                                                    overridePendingTransition(0, 0);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("TAG", "Error adding document", e);
                                                    submitbtn.setEnabled(true);
                                                    Toast.makeText(CustomerActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            });




                                }
                            } else {
                                Log.d("TAG", "get failed with ", task.getException());
                            }
                        }
                    });




                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CustomerActivity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
    }
}