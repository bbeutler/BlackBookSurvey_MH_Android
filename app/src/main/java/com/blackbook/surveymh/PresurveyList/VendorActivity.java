package com.blackbook.surveymh.PresurveyList;

import android.os.Bundle;
import androidx.core.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.blackbook.surveymh.BaseActivity;
import com.blackbook.surveymh.Constant.AppConstant;
import com.blackbook.surveymh.Constant.AppGlobal;
import com.blackbook.surveymh.R;
import com.blackbook.surveymh.Utils.IndexableListView;
import com.blackbook.surveymh.adapter.VendorAdapter;
import com.blackbook.surveymh.db.DatabaseHelper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VendorActivity extends BaseActivity
{
    private DatabaseHelper db;
    private IndexableListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        setTitle("Select Vendor");
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        db = new DatabaseHelper(VendorActivity.this);
        db.openDataBase();

        mListView = (IndexableListView) findViewById(R.id.list);
        mListView.setTextFilterEnabled(true);

        List<String> countries = populateCountries();
        VendorAdapter adapter = new VendorAdapter(this,R.layout.row_vendor_item, countries);
        mListView.setAdapter(adapter);
        mListView.setFastScrollEnabled(true);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppGlobal.setStringPreference(VendorActivity.this, mListView.getItemAtPosition(position).toString(), AppConstant.Prefvendorstext);
                finish();
            }
        });
    }

    private List<String> populateCountries()
    {
        List<String> countries;
        countries = db.GetAllVendors();
        Collections.sort(countries, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
        return countries;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(VendorActivity.this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}