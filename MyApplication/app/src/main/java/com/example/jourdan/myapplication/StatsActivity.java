package com.example.jourdan.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StatsActivity extends BaseActivity
{
    final String TAG = "StatsActivity.java";
    private AppData appData = null;
    Utility util = new Utility();
    LinearLayout topLevel = null;
    HorizontalScrollView scroll = null;
    LinearLayout subScroll = null;


    TextView editText = (TextView)findViewById(R.id.edit_message);
    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void afterTextChanged(Editable editable) {
            String added_number = editText.getText().toString();
            if (added_number.length() != 0) {
                int number  = Integer.parseInt(added_number);
                if (number > 1000)
                    editText.setText("666");
            }

        }
    };
    //editText.addTextChangedListener(tw);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        topLevel = new LinearLayout(this);
        topLevel.setOrientation(LinearLayout.HORIZONTAL);
        appData = ((MyApplication) this.getApplication()).getAppData();
        Log.d(TAG, "Initial AppData: "+appData.getSalaryString() +" "+ appData.getCities()+" "+appData.getAttributes());
        Log.d(TAG, TAG + " created successfully");
        if( appData.getCities().length() == 0)
        {
            TextView TV = new TextView(this);
            TV.setText("You haven't added any locations yet");
            TV.setTextSize(26);

            // Set the text view as the activity layout
            setContentView(TV);
        }
        else{
            createCityColumn();
            //Create scrolling view to hold attribute columns
            scroll = new HorizontalScrollView(this);
            subScroll = new LinearLayout(this);
            if(appData.getAttributes().length() == 0 )
            {
                TextView TV = new TextView(this);
                TV.setText("You didn't select any attributes to compare");
                TV.setTextSize(26);
                subScroll.addView(TV);
            }
            else
            {
                createAttributeColumns();
            }
            scroll.addView(subScroll);
            topLevel.addView(scroll);
            setContentView(topLevel);
        }
    }

    public void createCityColumn()
    {
        LinearLayout LL = new LinearLayout(this);
        LL.setOrientation(LinearLayout.VERTICAL);
        LL.setPadding(10, 15, 8, 10);
        TextView cityHeading = new TextView(this);
        cityHeading.setText("City");
        cityHeading.setTextSize(26);
        LL.addView(cityHeading);
        for(int i=0;i<appData.getCities().length(); i++)
        {
            try
            {
                JSONObject temp = appData.getCities().getJSONObject(i);
                String cityName = temp.getString("name");
                if( !cityName.equals("default") )
                {
                    TextView tempView = new TextView(this);
                    tempView.setText(cityName);
                    tempView.setPadding(10, 15, 8, 10);
                    LL.addView(tempView);
                    Log.d(TAG, "city: " + cityName);
                }
                else{
                    continue;
                }
            }
            catch (JSONException e) { e.printStackTrace(); }
        }
        topLevel.addView(LL);
    }
    public void createAttributeColumns() {
        JSONArray atts = appData.getAttributes();
        for (int i = 0; i < atts.length(); i++)
        {
            //Layout for column
            LinearLayout attrLL = new LinearLayout(this);
            attrLL.setOrientation(LinearLayout.VERTICAL);
            attrLL.setPadding(35,15,0,0);
            TextView attrHeading = new TextView(this);
            try
            {
                String attr = atts.getString(i);
                // Set Column Header
                attrHeading.setText(attr);
                attrHeading.setTextSize(26);
                attrLL.addView(attrHeading);
                if( attr.equals("schools") )
                {
                    createSchoolsColumn(attrLL);
                }
                if( attr.equals("historic") )
                {
                    createHistoricColumn(attrLL);
                }
                if( attr.equals("affordableHousing") )
                {
                    createAffordableHousingColumn(attrLL);
                }
                if( attr.equals("tax") )
                {
                    createTaxColumn(attrLL);
                }
            }
            catch (JSONException e) { e.printStackTrace(); }
        }
    }
    public void createSchoolsColumn(LinearLayout LL)
    {
        for(int i=0;i<appData.getCities().length(); i++)
        {
            try
            {
                JSONObject temp = appData.getCities().getJSONObject(i);
                String cityName = temp.getString("name");
                if( !cityName.equals("default") )
                {
                    String schools = util.getCitySchools(cityName);
                    TextView tempView = new TextView(this);
                    tempView.setText(schools);
                    tempView.setGravity(Gravity.CENTER);
                    tempView.setPadding(8, 15, 8, 8);
                    LL.addView(tempView);
                }
                else{
                    continue;
                }
            }
            catch (JSONException e) { e.printStackTrace(); }
        }
        subScroll.addView(LL);
    }
    public void createHistoricColumn(LinearLayout LL)
    {
        for(int i=0;i<appData.getCities().length(); i++)
        {
            try
            {
                JSONObject temp = appData.getCities().getJSONObject(i);
                String cityName = temp.getString("name");
                if( !cityName.equals("default") )
                {
                    String houses = util.getAffordableAssistedHousing(cityName);
                    TextView tempView = new TextView(this);
                    tempView.setText(houses);
                    tempView.setGravity(Gravity.CENTER);
                    tempView.setPadding(8, 15, 8, 8);
                    LL.addView(tempView);
                }
                else{
                    continue;
                }
            }
            catch (JSONException e) { e.printStackTrace(); }
        }

        subScroll.addView(LL);
    }

    public void createAffordableHousingColumn(LinearLayout LL)
    {
        for(int i=0;i<appData.getCities().length(); i++)
        {
            try
            {
                JSONObject temp = appData.getCities().getJSONObject(i);
                String cityName = temp.getString("name");
                if( !cityName.equals("default") )
                {
                    String schools = util.getCitySchools(cityName);
                    TextView tempView = new TextView(this);
                    tempView.setText(schools);
                    tempView.setGravity(Gravity.CENTER);
                    tempView.setPadding(8, 15, 8, 8);
                    LL.addView(tempView);
                }
                else{
                    continue;
                }
            }
            catch (JSONException e) { e.printStackTrace(); }
        }
        subScroll.addView(LL);
    }
    public void createTaxColumn(LinearLayout LL)
    {
        for(int i=0;i<appData.getCities().length(); i++)
        {
            try
            {
                JSONObject temp = appData.getCities().getJSONObject(i);
                String cityName = temp.getString("name");
                if( !cityName.equals("default") )
                {
                    JSONObject taxes = util.getCityTax(cityName);
                    String cityTax = taxes.getString("res");
                    TextView tempView = new TextView(this);
                    tempView.setText(cityTax);
                    tempView.setGravity(Gravity.CENTER);
                    tempView.setPadding(8, 15, 8, 8);
                    LL.addView(tempView);
                }
                else{
                    continue;
                }
            }
            catch (JSONException e) { e.printStackTrace(); }
        }
        subScroll.addView(LL);
    }
    /**
     * @return
     */
    public void populateTable(){
        TableLayout table = (TableLayout)findViewById(R.id.statsTable);
        table.removeAllViews();

        JSONArray citiesArray = appData.getCities();
        Log.d(TAG, "citiesArray: "+citiesArray);
        Utility util = new Utility();

        String lat = null;
        String lng = null;
        String cityName = null;
        JSONObject cityTax = null;

        for(int i=0; i<citiesArray.length(); i++) {
            try
            {
                lat = citiesArray.getJSONObject(i).getString("lat");
                lng = citiesArray.getJSONObject(i).getString("lng");
                if(Double.valueOf(lat) != 0.0 && 0.0 != Double.valueOf(lat) )
                {
                    Log.d(TAG,"Check city: "+lat+"-"+lng);
                    cityName = util.getCityFromCoord(lat, lng);
                    Log.d(TAG, "city: " + cityName);
                    cityTax = util.getCityTax(cityName);
                }
                else{
                    continue;
                }
            }
            catch (JSONException e) { e.printStackTrace(); }

            TableRow row = new TableRow(this);

            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for(int j=0; j<3; j++) {
                TextView view = new TextView(this);

                // City name
                if(j == 0){
                    view.setText(cityName);
                }

                // Residential income tax
                else if( j == 1){
                    try
                    {
                        view.setText(cityTax.getString("res"));
                    }
                    catch (JSONException e) { e.printStackTrace(); }

                }

                // Non-residential income tax
                else{
                    try
                    {
                        view.setText(cityTax.getString("nonres"));
                    }
                    catch (JSONException e) { e.printStackTrace(); }
                }

                view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                row.addView(view);
            }

            table.addView(row);
        }
    }
}