package ie.cm.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import ie.cm.R;
import ie.cm.api.CoffeeApi;
import ie.cm.models.Coffee;

public class Search extends Base {
    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;
    ArrayList<String> temp = new ArrayList<>();

    // Search EditText
    EditText inputSearch;


    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        new GetAllTask(this).execute("/coffees");
        // Adding items to listview

    }
    class CoffeeAdapter extends ArrayAdapter<Coffee> {
        private Context context;
        public List<Coffee> coffees;

        public CoffeeAdapter(Context context, List<Coffee> coffees) {
            super(context, R.layout.content_add, coffeeList);
            this.context = context;
            this.coffees = coffees;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.content_add, parent, false);
            final Coffee coffee = coffees.get(position);
            TextView coffeePrice = (TextView) view.findViewById(R.id.row_price);
            TextView coffeeShop = (TextView) view.findViewById(R.id.row_shop);
            TextView coffeeName = (TextView) view.findViewById(R.id.row_name);
            RatingBar rating = (RatingBar) view.findViewById(R.id.ratingBar);
//            RatingBar ratingFav = (RatingBar) view.findViewById(R.id.ratingBarFav);
            ImageView delete = (ImageView) view.findViewById(R.id.imgDelete);
//            view.setTag(coffee._id); // setting the 'row' id to the id of the donation

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (v.getTag() instanceof Coffee) {
//                    onCoffeeDelete(coffee);
//                    }

                }
            });

//            if(coffee.favorite==true)
//            {
//                ratingFav.setRating(1);
//            }
//
//            ratingFav.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//                @Override
//                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                    coffee.favorite = true;
//                }
//            });


            rating.setRating(Float.valueOf(String.valueOf(coffee.rating)));
            rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    Toast.makeText(context, "rating" + rating, Toast.LENGTH_SHORT).show();
                }
            });
            coffeePrice.setText(String.valueOf("Price : " + coffee.coffePrice));
            coffeeShop.setText(String.valueOf("Shop : " + coffee.coffeeShop));
            coffeeName.setText(String.valueOf("Name : " + coffee.coffeName));
//            methodView.setText(String.valueOf(coffee.rating));

//            view.setId(Integer.valueOf(coffee._id)); // setting the 'row' id to the id of the donation

            return view;
        }

        @Override
        public int getCount() {
            return coffees.size();
        }
    }
    private class GetAllTask extends AsyncTask<String, Void, List<Coffee>> {

        protected ProgressDialog dialog;
        protected Context context;

        public GetAllTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving Donations List");
            this.dialog.show();
        }

        @Override
        protected List<Coffee> doInBackground(String... params) {

            try {
                return (List<Coffee>) CoffeeApi.getAll((String) params[0]);
            } catch (Exception e) {
                Log.v("ASYNC", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Coffee> result) {
            super.onPostExecute(result);
//            Log.v("Coffee", result.toString());
            app.coffees = result;
            Collections.sort(app.coffees, new Comparator<Coffee>() {
                public int compare(Coffee emp1, Coffee emp2) {
                    return emp1.getName().compareToIgnoreCase(emp2.getName());
                }
            });

            for(int i = 0 ; i < app.coffees.size();i++)
            {
                temp.add(app.coffees.get(i).getName());
            }

            adapter = new ArrayAdapter<String>(context, R.layout.content_add, R.id.row_name, temp);
            lv.setAdapter(adapter);
            inputSearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    // When user changed the Text
                    Search.this.adapter.getFilter().filter(cs);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                }
            });


            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

}
