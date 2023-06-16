package com.mad.impress.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.impress.MainActivity;
import com.mad.impress.R;
import com.mad.impress.adapter.CustomerAdapter;
import com.mad.impress.model.Customer;
import com.mad.impress.views.TextInputAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class ListCustomerActivity extends AppCompatActivity {


    CustomerAdapter adapter;
    RecyclerView rv;
    LinearLayoutManager manager1;

    // EditText searchcus,
    EditText searchmob;

    List<String> list_customerNames;
    ArrayAdapter arrayAdapter;
    TextInputAutoCompleteTextView searchcus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_customer);


        searchcus = findViewById(R.id.searchcus);
        list_customerNames = new ArrayList<>();


        FirebaseFirestore.getInstance().collection("CustomerMaster").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        if (doc.get("customerName") != null) {
                            list_customerNames.add(doc.getString("customerName"));
                        }
                    }
                }
                arrayAdapter = new ArrayAdapter(ListCustomerActivity.this, android.R.layout.simple_list_item_1, list_customerNames);


//                        siteadapter = new ArrayAdapter(AllUserActivity.this, android.R.layout.simple_spinner_item, sitenamesaraylist);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                searchcus.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        });


        searchcus = findViewById(R.id.searchcus);
        searchmob = findViewById(R.id.searchmob);
        rv = findViewById(R.id.rv);

        manager1 = new LinearLayoutManager(this);
        manager1.setReverseLayout(true);
        manager1.setStackFromEnd(true);
        rv.setLayoutManager(manager1);

        Query query = FirebaseFirestore.getInstance().collection("CustomerMaster");
        FirestoreRecyclerOptions<Customer> options1 = new FirestoreRecyclerOptions.Builder<Customer>().setQuery(query, Customer.class).build();

        adapter = new CustomerAdapter(options1, ListCustomerActivity.this);
        adapter.notifyDataSetChanged();

        adapter.startListening();
        rv.setAdapter(adapter);


        searchcus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!searchcus.getText().toString().trim().isEmpty()) {

                    Query query = FirebaseFirestore.getInstance().collection("CustomerMaster").whereEqualTo("customerName", searchcus.getText().toString().trim());
                    FirestoreRecyclerOptions<Customer> options1 = new FirestoreRecyclerOptions.Builder<Customer>().setQuery(query, Customer.class).build();

                    adapter = new CustomerAdapter(options1, ListCustomerActivity.this);
                    adapter.notifyDataSetChanged();

                    adapter.startListening();
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchmob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!searchmob.getText().toString().trim().isEmpty()) {


                    Query query = FirebaseFirestore.getInstance().collection("CustomerMaster").whereArrayContains("mobiles", searchmob.getText().toString().trim());
                    FirestoreRecyclerOptions<Customer> options1 = new FirestoreRecyclerOptions.Builder<Customer>().setQuery(query, Customer.class).build();

                    adapter = new CustomerAdapter(options1, ListCustomerActivity.this);
                    adapter.notifyDataSetChanged();

                    adapter.startListening();
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ListCustomerActivity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
    }
}