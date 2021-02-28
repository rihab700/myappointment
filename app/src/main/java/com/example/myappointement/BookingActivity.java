package com.example.myappointement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity  {
    private Button previous, next;
    private Spinner spinner;
    private String chosen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        previous = (Button) findViewById(R.id.back);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookingActivity.this, profileActivity.class));
            }
        });

        next = (Button) findViewById(R.id.next1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(BookingActivity.this,ChooseDoc.class));
                Intent i = new Intent(BookingActivity.this, ChooseDoc.class);
                i.putExtra("city",chosen);
                startActivity(i);

            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

       List<String> cities = new ArrayList<>();
       cities.add("chose a city");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cities);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  spinnerValue= parent.getItemAtPosition(position).toString();

                Toast.makeText(getBaseContext(),  spinnerValue, Toast.LENGTH_SHORT).show();
                chosen= spinner.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.Cities.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Doctors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null){
                    Toast.makeText(BookingActivity.this,"something went wrong! Try again", Toast.LENGTH_LONG).show();
                }
                for(DocumentSnapshot doc :value){
                    if(doc.get("name")!= null){
                        cities.add(doc.getString("name"));
                    }
                }

            }
        });


    }
}


