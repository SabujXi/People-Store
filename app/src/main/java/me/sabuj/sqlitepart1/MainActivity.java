package me.sabuj.sqlitepart1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MyDbHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saveBtn = (Button) findViewById(R.id.save);
        Button searchBtn = (Button) findViewById(R.id.searchByName);

        final EditText editName = (EditText) findViewById(R.id.editName);
        final EditText editEmail = (EditText) findViewById(R.id.editEmail);
        final EditText editProfession = (EditText) findViewById(R.id.editProfession);
        final TextView infoView = (TextView) findViewById(R.id.textInfo);


        // Preparing the database
        myDbHelper = new MyDbHelper(this);

        // Setting up click listeners
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = myDbHelper.save(
                        editName.getText().toString(),
                        editEmail.getText().toString(),
                        editProfession.getText().toString()
                );
                infoView.setText(res);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = myDbHelper.search(editEmail.getText().toString());
                infoView.setText(res);
            }
        });
    }

}
