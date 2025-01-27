package UTS.Mobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private AdapterList myAdapter;
    private List<ItemList> itemLists;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.rcvNews);
        floatingActionButton = findViewById(R.id.floatAddNews);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemLists = new ArrayList<>();
        myAdapter = new AdapterList(itemLists);
        recyclerView.setAdapter(myAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddPage = new Intent(MainActivity.this, NewsAdd.class);
                startActivity(toAddPage);
            }
        });

        myAdapter.setOnItemClickListener(new AdapterList.OnItemClickListener() {
            @Override
            public void onItemClick(ItemList item) {
                Intent intent = new Intent(MainActivity.this, NewsDetail.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("title", item.getJudul());
                intent.putExtra("desc", item.getSubJudul());
                intent.putExtra("imageURL", item.getImageUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {
        progressDialog.show();
        db.collection("news")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            itemLists.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ItemList item = new ItemList(
                                        document.getString("title"),
                                        document.getString("desc"),
                                        document.getString("imageURL")
                                );
                                item.setId(document.getId());
                                itemLists.add(item);
                                Log.d("data", document.getId() + " => " + document.getData());
                            }
                            myAdapter.notifyDataSetChanged();
                        } else {
                            Log.w("data", "Error getting documents.", task.getException());
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            mAuth.signOut();
            Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
