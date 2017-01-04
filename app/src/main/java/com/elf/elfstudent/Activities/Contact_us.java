package com.elf.elfstudent.Activities;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.elf.elfstudent.R;

/**
 * Created by Kri on 23-12-2016.
 */

public class Contact_us extends AppCompatActivity {

    TextView elf_link;
    Toolbar contactus_toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

        elf_link=(TextView)findViewById(R.id.hyperlink);
        elf_link.setClickable(true);
        elf_link.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://www.elfanalysis.com'> www.elfanalysis.com </a>";
        elf_link.setText(Html.fromHtml(text));

        contactus_toolbar = (Toolbar)findViewById(R.id.contct_us_tlb);






    }
}
