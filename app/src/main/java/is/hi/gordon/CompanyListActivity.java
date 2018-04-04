package is.hi.gordon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Created by brynj on 04/04/2018.
 *
 * A class that shows companies enrolled in the program, making it a possibility for the admin to
 * add a company, edit company and delete company
 *
 */

public class CompanyListActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        ((Button) findViewById(R.id.goBackBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyListActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //when button is clicked admin can add another company to the list
        ((Button) findViewById(R.id.addBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyListActivity.this, AddCompanyActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //when button is clicked the admin can change the company info
        ((Button) findViewById(R.id.changeBtn)).setOnClickListener(new View.OnClickListener() {
            //virkni kemur síðar sem leyfir admin að breyta upplýsingum um fyrirtæki
        });

        //when button is clicked the admin can delete the company from the list
        ((Button) findViewById(R.id.deleteBtn)).setOnClickListener(new View.OnClickListener() {
            //virkni kemur síðar sem leyfir admin að eyða fyrirtæki af listanum
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate the menu, this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.activity_quest, menu);
        return true;
    }
}
