package nl.stefandv.level_4_assignment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addItem extends AppCompatActivity {

    static String title;
    static String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);



        Button addPortalButton = (Button) findViewById(R.id.addItemButton);
        final EditText titleEditText = (EditText) findViewById(R.id.title_input);
        final EditText descriptionEditText = (EditText) findViewById(R.id.description_input);


        addPortalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title = titleEditText.getText().toString();
                description = descriptionEditText.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("title", title);
                returnIntent.putExtra("description", description);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }


        });

    }
}
