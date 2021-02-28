package com.example.myappointement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ChooseDoc extends AppCompatActivity {
    private RecyclerView mFiresotreList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private Button previous,next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doc);

        previous = (Button) findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseDoc.this,BookingActivity.class));
            }
        });
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseDoc.this,DateActivity.class));
            }
        });



        mFiresotreList = findViewById(R.id.firestore_liste);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String city=intent.getStringExtra("city");
        Toast.makeText(getBaseContext(),  city, Toast.LENGTH_SHORT).show();


        Query query= firebaseFirestore.collection("Doctors").document(city).collection("generaliste");
        FirestoreRecyclerOptions<Doctors> options = new FirestoreRecyclerOptions.Builder<Doctors>()
                .setQuery(query,Doctors.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Doctors, DoctorsViewHolder>(options) {
            @NonNull
            @Override
            public DoctorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);

                return new DoctorsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DoctorsViewHolder holder, int position, @NonNull Doctors model) {
                holder.list_name.setText(model.getName());
                holder.list_adr.setText(model.getAdresse());
                holder.list_tel.setText(model.getTel() + " ");

            }
        };
        mFiresotreList.setHasFixedSize(true);
        mFiresotreList.setLayoutManager(new LinearLayoutManager(this));
        mFiresotreList.setAdapter(adapter);

    }

    private class DoctorsViewHolder extends RecyclerView.ViewHolder{
        private TextView list_name;
        private TextView list_adr;
        private TextView list_tel;


        public DoctorsViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.name);
            list_adr = itemView.findViewById(R.id.adresse);
            list_tel = itemView.findViewById(R.id.tel);

        }

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
}