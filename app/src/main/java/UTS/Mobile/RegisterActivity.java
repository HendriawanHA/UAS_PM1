package UTS.Mobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final ArrayList<User> userList = new ArrayList<>();


    public static ArrayList<User> getUserList() {
        return userList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.register_button);
        EditText fullNameEditText = findViewById(R.id.editText);
        EditText passwordEditText = findViewById(R.id.editText2);
        EditText emailEditText = findViewById(R.id.editText3);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateInputs();
            }
        };

        fullNameEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);
        emailEditText.addTextChangedListener(textWatcher);

        registerButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String email = emailEditText.getText().toString();

            if (fullName.isEmpty() || password.isEmpty() || email.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Isi terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d(TAG, "Register button clicked with: Full Name: " + fullName + ", Email: " + email);
            Toast.makeText(RegisterActivity.this, "Anda telah terdaftar", Toast.LENGTH_SHORT).show();

            User user = new User(fullName, email, password);
            userList.add(user);

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        TextView signInTextView = findViewById(R.id.textview7);
        signInTextView.setOnClickListener(v -> {
            Intent signInIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(signInIntent);
        });
    }

    private void validateInputs() {
        EditText fullNameEditText = findViewById(R.id.editText);
        EditText passwordEditText = findViewById(R.id.editText2);
        EditText emailEditText = findViewById(R.id.editText3);

        String fullName = fullNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setEnabled(!fullName.isEmpty() && !password.isEmpty() && !email.isEmpty());
    }
}

