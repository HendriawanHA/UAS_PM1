package UTS.Mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userList = RegisterActivity.getUserList();

        EditText usernameEditText = findViewById(R.id.editText);
        EditText passwordEditText = findViewById(R.id.editText2);
        Button loginButton = findViewById(R.id.register_button);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            boolean userFound = false;
            for (User user : userList) {
                if (user.getFullName().equals(username) && user.getPassword().equals(password)) {
                    userFound = true;
                    break;
                }
            }

            if (userFound) {
                Intent intent = new Intent(LoginActivity.this, NewsPortalDashboardActivity.class);
                intent.putParcelableArrayListExtra("loggedInUsers", (ArrayList<? extends Parcelable>)userList);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
            }
        });

        TextView registerTextView = findViewById(R.id.textview7);
        registerTextView.setOnClickListener(v -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });
    }
}
