package ie.cm.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ie.cm.models.Coffee;

/**
 * Created by Kuba Pieczonka on 20/04/2016.
 */
public class CoffeeApi {



        //////////////////////////////////////////////////////////////////////////////////
        public static List<Coffee> getAll(String call) {
            Gson gson = new Gson();
            String json = Rest.get(call);
            Log.v("Coffee", "JSON RESULT in API: " + json);
            Type collectionType = new TypeToken<List<Coffee>>(){}.getType();
            List<Coffee> temp = gson.fromJson(json, collectionType);
            Log.v("Coffee", "Look HERE: " + temp);
            return temp;
        }
        //////////////////////////////////////////////////////////////////////////////////
        public static Coffee get(String call,String id) {
            String json = Rest.get(call + "/" + id);
            Log.v("Coffee", "JSON RESULT : " + json);
            Type objType = new TypeToken<Coffee>(){}.getType();

            return new Gson().fromJson(json, objType);
        }
        //////////////////////////////////////////////////////////////////////////////////
        public static String deleteAll(String call) {
            return Rest.delete(call);
        }
        //////////////////////////////////////////////////////////////////////////////////
        public static String delete(String call, String id) {
            return Rest.delete(call + "/" + id);
        }
        //////////////////////////////////////////////////////////////////////////////////
        public static String insert(String call,Coffee coffee) {
            Type objType = new TypeToken<Coffee>(){}.getType();
            String json = new Gson().toJson(coffee, objType);

            return Rest.post(call,json);
        }
    }


