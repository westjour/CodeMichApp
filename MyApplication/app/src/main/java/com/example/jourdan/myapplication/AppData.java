package com.example.jourdan.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by timsloncz on 10/4/14.
 */
public class AppData {
    private Double salary = 0.0;
    private JSONArray cities = new JSONArray();
    private JSONArray attributes = new JSONArray();

    public void setSalary(String salary)
    {
        if(salary.equals("") || Double.valueOf(salary) < 0 )
        {
            this.salary = 0.0;
        }
        else
        {
            this.salary = Double.valueOf(salary);
        }
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

    /**
     *
     * @return
     */
    public JSONArray getCityNames()
    {
        JSONArray cityNames = new JSONArray();
        for(int i=0; i< cities.length(); i++)
        {
            break;
        }
        return cityNames;
    }

    /**
     *
     * @param attribute
     * @return
     */
    public boolean addAttribute(String attribute)
    {
        attributes.put(attribute);
        return true;
    }
    /**
     *
     * @param attribute
     * @return
     */
    public boolean removeAttribute(String attribute)
    {
        JSONArray temp = new JSONArray();
        for(int i=0;i<attributes.length();i++)
        {
            String test = "";
            try {
                test = attributes.getString(i);
                if(!test.equals(attribute))
                {
                    temp.put(test);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        attributes = temp;
        return true;
    }

    /**
     *
     * @return
     */
    public JSONArray getAttributes()
    {
        return attributes;
    }
}
