package com.blackbook.surveymh.PresurveyList;

import android.os.Bundle;
import androidx.core.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.blackbook.surveymh.BaseActivity;
import com.blackbook.surveymh.Constant.AppConstant;
import com.blackbook.surveymh.Constant.AppGlobal;
import com.blackbook.surveymh.R;
import com.blackbook.surveymh.Utils.IndexableListView;
import com.blackbook.surveymh.Utils.Utils;
import com.blackbook.surveymh.adapter.VendorAdapter;
import com.blackbook.surveymh.db.DatabaseHelper;

import java.util.List;

/**
 *
 * Created by jcaruso on 11/30/2017.
 *
 */
public class RateActivity extends BaseActivity {
    private DatabaseHelper db;
    private IndexableListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        setTitle("Rating");
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        db = new DatabaseHelper(RateActivity.this);
        db.openDataBase();

        mListView = (IndexableListView) findViewById(R.id.list);
        mListView.setTextFilterEnabled(true);

        List<String> ratings = populateRating();
        VendorAdapter adapter = new VendorAdapter(this,R.layout.row_vendor_item, ratings);
        mListView.setAdapter(adapter);
        mListView.setFastScrollEnabled(true);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppGlobal.setStringPreference(RateActivity.this, mListView.getItemAtPosition(position).toString(), AppConstant.Prefratetext);
                finish();
            }
        });
    }

    private List<String> populateRating()
    {
        List<String> rates;
        rates = db.GetAllRates();

        Log.i(Utils.TAG, rates.toString());

        return rates;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(RateActivity.this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
