package com.mad.impress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mad.impress.ui.CustomerActivity;
import com.mad.impress.ui.ListCustomerActivity;
import com.mad.impress.ui.ShowAllActivity;

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


    }
}