package UTS.Mobile;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class NewsPortalDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_portal_dashboard);

        ArrayList<User> loggedInUsers = getIntent().getParcelableArrayListExtra("loggedInUsers");

        ListView userListView = findViewById(R.id.user_list_view);

        ArrayList<String> usernames = new ArrayList<>();
        if (loggedInUsers != null) {
            for (User user : loggedInUsers) {
                usernames.add(user.getFullName() + " (" + user.getEmail() + ")");
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);
        userListView.setAdapter(adapter);
    }
}

