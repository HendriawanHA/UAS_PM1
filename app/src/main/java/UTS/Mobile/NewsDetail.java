package UTS.Mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewsDetail extends AppCompatActivity {
    TextView newsTitle, newsSubtitle;
    ImageView newsImage;
    Button edit, hapus;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_news_detail);

        db = FirebaseFirestore.getInstance();


        newsTitle = findViewById(R.id.newsTitle);
        newsSubtitle = findViewById(R.id.newsSubtitle);
        newsImage = findViewById(R.id.newsImage);
        edit = findViewById(R.id.editButton);
        hapus = findViewById(R.id.deleteButton);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String subtitle = intent.getStringExtra("title");
        String imageUrl = intent.getStringExtra("title");

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsDetail.this, NewsAdd.class);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("desc", subtitle);
                intent.putExtra("imageURL", imageUrl);
                startActivity(intent);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("news").document(id)
                        .delete()
                        .addOnSuccessListener(aVoid ->{
                            Toast.makeText(NewsDetail.this, "News deleted successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewsDetail.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(NewsDetail.this, "Error deleting news: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            Log.w("NewsDetail", "Error deleting document", e);
                        });

            }
        });

        newsTitle.setText(title);
        newsSubtitle.setText(subtitle);
        Glide.with(this).load(imageUrl).into(newsImage);
    }
}
