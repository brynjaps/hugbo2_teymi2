package is.hi.gordon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by brynja og karítas on 11/04/2018.
 */

public class newUserActivity extends Activity {

    EditText mEmailInput;

    EditText mCompanyInput;

    EditText mDepartmentInput;

    String[] newUser = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);


        ((Button) findViewById(R.id.skra_btn)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mEmailInput = (EditText)findViewById(R.id.emailfillin);
                mCompanyInput = (EditText)findViewById(R.id.company);
                mDepartmentInput = (EditText)findViewById(R.id.department);

                CharSequence emailText = mEmailInput.getText();
                CharSequence companyText = mCompanyInput.getText();
                CharSequence departmentText = mDepartmentInput.getText();

                String email = emailText.toString();
                String company = companyText.toString();
                String department = departmentText.toString();

                newUser[0] = email;
                newUser[1] = company;
                newUser[2] = department;

                Intent intent = new Intent(newUserActivity.this, QuestActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArray("newUser", newUser);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate the menu, this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.activity_quest, menu);
        return true;
    }

}
