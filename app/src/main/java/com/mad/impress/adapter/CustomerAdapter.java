package com.mad.impress.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.impress.R;
import com.mad.impress.model.Customer;
import com.mad.impress.ui.CustomerActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CustomerAdapter extends FirestoreRecyclerAdapter<Customer, CustomerAdapter.CustomerVH> {

    Context context;

    public CustomerAdapter(@NonNull FirestoreRecyclerOptions<Customer> options, Context context) {
        super(options);
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull CustomerVH holder, int position, @NonNull Customer model) {
        holder.bookName.setText("Book - "+model.getBookName());
        holder.customerId.setText("ID - "+model.getCustomerId());
        holder.customerName.setText("Name - "+model.getCustomerName());
        holder.customerMobile.setText("Mobile - "+model.getMobiles().get(0));

        if (model.getMobiles().size() == 2) {
            holder.customerMobile.setText("Mobile - "+model.getMobiles().get(0) +" , " + model.getMobiles().get(1));
        }


//        if(model.getId().isEmpty()){
//            holder.edit.setVisibility(View.GONE);
//            holder.delete.setVisibility(View.GONE);
//        }

//        if (model.getNote().toString().trim().isEmpty()) {
//            holder.note.setVisibility(View.GONE);
//        }
        holder.note.setText("Note - "+model.getNote().toString().trim());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50, 0, 50, 0)
                        .setContentHolder(new ViewHolder(R.layout.deletebox_customer))
                        .setExpanded(false)
                        .create();

                dialogPlus.show();

                View hView = (LinearLayout) dialogPlus.getHolderView();
                Button nobtn = hView.findViewById(R.id.nobtn);
                Button yesbtn = hView.findViewById(R.id.yesbtn);


                nobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogPlus.dismiss();
                    }
                });
                yesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseFirestore.getInstance().collection("Customer")
                                .document(model.getBookName() + "_" + model.getCustomerId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error deleting document", e);
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });


            }
        });


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50, 0, 50, 0)
                        .setContentHolder(new ViewHolder(R.layout.editbox_customer))
                        .setExpanded(false)
                        .create();

                dialogPlus.show();
                View hView = (ScrollView) dialogPlus.getHolderView();
                EditText edtbookName = hView.findViewById(R.id.bookName);
                EditText edtCusID = hView.findViewById(R.id.customerId);
                EditText edtCusName = hView.findViewById(R.id.customerName);
                EditText edtMobile1 = hView.findViewById(R.id.mobile1);
                EditText edtMobile2 = hView.findViewById(R.id.mobile2);
                EditText edtNote = hView.findViewById(R.id.note);

                Button cancel = hView.findViewById(R.id.btnCancelForm);
                Button edit = hView.findViewById(R.id.btnEditForm);

                edtbookName.setText(model.getBookName());
                edtCusID.setText(model.getCustomerId());
                edtbookName.setEnabled(false);
                edtCusID.setEnabled(false);
                edtCusName.setText(model.getCustomerName());

                if (model.getMobiles().size() == 1) {
                    edtMobile1.setText(model.getMobiles().get(0));

                } else if (model.getMobiles().size() == 2) {
                    edtMobile1.setText(model.getMobiles().get(0));
                    edtMobile2.setText(model.getMobiles().get(1));
                }

                edtNote.setText(model.getNote());


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogPlus.dismiss();
                        //Log.i("iamhererere","id is "+model.getId());
                    }
                });
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (edtbookName.getText().toString().trim().isEmpty()) {
                            Toast.makeText(context, "Enter Book Name", Toast.LENGTH_SHORT).show();
                        } else if (edtCusID.getText().toString().trim().isEmpty()) {
                            Toast.makeText(context, "Enter Customer Id", Toast.LENGTH_SHORT).show();
                        } else if (edtCusName.getText().toString().trim().isEmpty()) {
                            Toast.makeText(context, "Enter Customer Name", Toast.LENGTH_SHORT).show();
                        } else if (edtMobile1.getText().toString().trim().isEmpty()) {
                            Toast.makeText(context, "Enter Mobile", Toast.LENGTH_SHORT).show();
                        } else {


                            edit.setEnabled(false);
                            String id = edtbookName.getText().toString() + "_" + edtCusID.getText().toString();


                            List<String> mobiles = new ArrayList<>();
                            mobiles.add(edtMobile1.getText().toString().trim());

                            if (!edtMobile2.getText().toString().trim().isEmpty()) {
                                mobiles.add(edtMobile2.getText().toString().trim());
                            }

                            HashMap<String, Object> hp = new HashMap<>();

                            hp.put("id", id);
                            hp.put("bookName", edtbookName.getText().toString().trim());
                            hp.put("customerId", edtCusID.getText().toString().trim());
                            hp.put("customerName", edtCusName.getText().toString().trim());
                            hp.put("mobiles", mobiles);
                            hp.put("time", new Timestamp(new Date()));
                            hp.put("note", edtNote.getText().toString().trim() + "");


                            FirebaseFirestore.getInstance().collection("Customer")
                                    .document(id)
                                    .set(hp)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("TAG", "DocumentSnapshot written with ID: ");
                                            Toast.makeText(context, "Entry Updated", Toast.LENGTH_SHORT).show();
                                            edit.setEnabled(true);
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("TAG", "Error adding document", e);
                                            Toast.makeText(context, "Update Error", Toast.LENGTH_SHORT).show();
                                            edit.setEnabled(true);
                                            dialogPlus.dismiss();
                                        }
                                    });
                        }


                    }
                });


            }
        });
    }

    @NonNull
    @Override
    public CustomerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_customerdetail, parent, false);
        return new CustomerVH(view);
    }

    public class CustomerVH extends RecyclerView.ViewHolder {

        TextView bookName, customerId, customerName, customerMobile, note;
        ImageView edit, delete;

        public CustomerVH(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.cardBookName);
            customerId = itemView.findViewById(R.id.cardCusId);
            customerName = itemView.findViewById(R.id.cardCustomerName);
            customerMobile = itemView.findViewById(R.id.cardCustomerMobile);
            note = itemView.findViewById(R.id.cardNote);

            edit = itemView.findViewById(R.id.imgEdit);
            delete = itemView.findViewById(R.id.imgDelete);

        }
    }
}
