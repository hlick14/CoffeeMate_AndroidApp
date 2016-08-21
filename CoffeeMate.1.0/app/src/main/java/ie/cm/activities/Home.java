package ie.cm.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ie.cm.R;
import ie.cm.api.CoffeeApi;
import ie.cm.models.Coffee;
public class Home extends Base {
    CoffeeAdapter adapter;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher1);
        View inc = (View) findViewById(R.id.inc);

        RelativeLayout outer = (RelativeLayout)inc.findViewById(R.id.outer);
         listView = (ListView) outer.findViewById(R.id.listOfCoffes);
        new GetAllTask(this).execute("/coffees");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Information " + coffeeList.size(), Snackbar.LENGTH_LONG)
                        .setAction("More Info...", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                openInfoDialog(Home.this);
                            }
                        }).show();
            }
        });
    }
////    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//        return inflater.inflate(R.layout.home, container, false);
//    }
    public void add(View v) {
        goToActivity(this, Add.class, null);
    }
    public void Search(View v) {
        goToActivity(this, Search.class, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            adapter = new CoffeeAdapter(context, app.coffees);
//            Log.v("Coffee444", app.coffees.toString());
//             adapter = new CoffeeAdapter(context, app.dbManager.getAll());

            listView.setAdapter(adapter);


//            listView.setOnItemClickListener(Home.this);
//            mSwipeRefreshLayout.setRefreshing(false);

            if (dialog.isShowing())
                dialog.dismiss();
        }
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
                        onCoffeeDelete(coffee);
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
    public void onCoffeeDelete(final Coffee coffee) {
        String stringId = coffee._id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Coffee?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Are you sure you want to Delete the \'Coffee with ID \' \n [ "
                + stringId + " ] ?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                new DeleteTask(Home.this).execute("/coffees", coffee._id);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private class DeleteTask extends AsyncTask<String, Void, String> {

        protected ProgressDialog dialog;
        protected Context context;

        public DeleteTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Deleting Donation");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                return (String) CoffeeApi.delete((String) params[0], (String) params[1]);
            } catch (Exception e) {
                Log.v("Coffee", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String s = result;
            Log.v("Coffee", "DELETE REQUEST : " + s);

            new GetAllTask(Home.this).execute("/coffees");

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }





}