package com.example.jourdan.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by timsloncz on 10/4/14.
 */
public class AppData {
    private Utility util = new Utility();
    private Double salary = 0.0;
    private JSONArray cities = new JSONArray();

    public void setSalary(String salary)
    {
        this.salary = Double.valueOf(salary);
    }
    public String getSalaryString()
    {
        return Double.toString(salary);
    }
    public Double getSalaryDouble()
    {
        return salary;
    }
    public boolean addCity(JSONObject location)
    {
        cities.put(location);
        return true;
    }
    public JSONArray getCities()
    {
        return cities;
    }
    public JSONArray getCityNames()
    {
        JSONArray cityNames = new JSONArray();
        for(int i=0; i< cities.length(); i++)
        {
            break;
        }
        return cityNames;
    }
}
