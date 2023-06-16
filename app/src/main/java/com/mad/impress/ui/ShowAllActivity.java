package com.mad.impress.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.api.Distribution;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mad.impress.MainActivity;
import com.mad.impress.R;
import com.mad.impress.adapter.CustomerAdapter;
import com.mad.impress.model.Customer;

public class ShowAllActivity extends AppCompatActivity {

    RecyclerView rv;
    LinearLayoutManager manager1;
    CustomerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        rv = findViewById(R.id.rv2);

        manager1 = new LinearLayoutManager(this);
        manager1.setReverseLayout(true);
        manager1.setStackFromEnd(true);
        rv.setLayoutManager(manager1);

        Query query = FirebaseFirestore.getInstance().collection("CustomerMaster").orderBy("time");
        FirestoreRecyclerOptions<Customer> options1 = new FirestoreRecyclerOptions.Builder<Customer>().setQuery(query, Customer.class).build();

        adapter = new CustomerAdapter(options1, ShowAllActivity.this);
        adapter.notifyDataSetChanged();
        adapter.startListening();
        rv.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ShowAllActivity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
    }
}