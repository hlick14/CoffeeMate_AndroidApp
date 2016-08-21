package ie.cm.main;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ie.cm.database.DBManager;
import ie.cm.models.Coffee;

public class CoffeeApp extends Application
{


    public List <Coffee> coffees    = new ArrayList<Coffee>();
    public ie.cm.database.DBManager dbManager = new DBManager(this);

    public void newCoffee(Coffee coffee)
    {
        coffees.add(coffee);
    }


//    public boolean newDonation(Coffee coffee)
//    {
//        boolean targetAchieved = totalDonated > target;
//        if (!targetAchieved)
//        {
//            coffee.add(cofee);
//            totalDonated += coffee.amount;
//        }
//        else
//        {
//            Toast.makeText(this, "Target Exceeded!", Toast.LENGTH_SHORT).show();
//        }
//        return targetAchieved;
//    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("Donate", "Coffee App Started");
        coffees = new ArrayList<Coffee>();



    }



}