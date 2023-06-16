package com.mad.impress.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.mad.impress.R;
import com.mad.impress.model.Customer;

public class CustomerAdapter extends FirestoreRecyclerAdapter<Customer, CustomerAdapter.CustomerVH> {

    Context context;
    public CustomerAdapter(@NonNull FirestoreRecyclerOptions<Customer> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull CustomerVH holder, int position, @NonNull Customer model) {
        holder.bookName.setText(model.getBookName());
        holder.customerId.setText(model.getCustomerId());
        holder.customerName.setText(model.getCustomerName());
        holder.customerMobile.setText(model.getMobiles().toString());

        if(model.getNote().toString().trim().isEmpty()){
            holder.note.setVisibility(View.GONE);
        }
        holder.note.setText(model.getNote().toString().trim());
    }

    @NonNull
    @Override
    public CustomerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_customerdetail, parent, false);
        return new CustomerVH(view);
    }

    public class CustomerVH extends RecyclerView.ViewHolder{

        TextView bookName, customerId, customerName, customerMobile, note;

        public CustomerVH(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.cardBookName);
            customerId = itemView.findViewById(R.id.cardCusId);
            customerName = itemView.findViewById(R.id.cardCustomerName);
            customerMobile = itemView.findViewById(R.id.cardCustomerMobile);
            note = itemView.findViewById(R.id.cardNote);

        }
    }
}
