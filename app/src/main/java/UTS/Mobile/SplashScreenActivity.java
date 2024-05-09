package UTS.Mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Button registerButton = findViewById(R.id.register_button);
        Button signInButton = findViewById(R.id.sign_in_button);

        registerButton.setOnClickListener(v -> {
            Intent registerIntent = new Intent(SplashScreenActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        signInButton.setOnClickListener(v -> {
            Intent signInIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(signInIntent);
        });
    }
}
