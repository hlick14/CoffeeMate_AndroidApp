package ie.cm.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import ie.cm.R;
import ie.cm.api.CoffeeApi;
import ie.cm.models.Coffee;

public class Add extends Base implements
		OnClickListener {

	private String 		coffeeName, coffeeShop;
	private double 		coffeePrice, ratingValue;
	private EditText 	name, shop, price;
	private RatingBar 	ratingBar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);

		Button saveButton = (Button) findViewById(R.id.saveCoffeeBtn);
		name = (EditText) findViewById(R.id.nameEditText);
		shop = (EditText) findViewById(R.id.shopEditText);
		price = (EditText) findViewById(R.id.priceEditText);
		ratingBar = (RatingBar) findViewById(R.id.coffeeRatingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingValue=rating;
                Toast.makeText(getApplicationContext(),"rating value is "+ ratingValue,Toast.LENGTH_SHORT).show();
            }
        });
		saveButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		coffeeName = name.getText().toString();
		coffeeShop = shop.getText().toString();
		try {
			coffeePrice = Double.parseDouble(price.getText().toString());
		} catch (NumberFormatException e) {
			coffeePrice = 0.0;
		}

//		ratingValue = ratingBar.getRating();

		if ((coffeeName.length() > 0) && (coffeeShop.length() > 0)
				&& (price.length() > 0)) {
			Coffee c = new Coffee(coffeePrice, coffeeName,coffeeShop,ratingValue,
					 false);


			coffeeList.add(c);
			new InsertTask(this).execute("/coffees", new Coffee(coffeePrice, coffeeName, coffeeShop,ratingValue,false));

			goToActivity(this,Home.class, null);
		} else
			Toast.makeText(
					this,
					"You must Enter Something for "
							+ "\'Name\', \'Shop\' and \'Price\'",
					Toast.LENGTH_SHORT).show();
	}
	private class InsertTask extends AsyncTask<Object, Void, String> {

		protected ProgressDialog dialog;
		protected Context context;

		public InsertTask(Context context)
		{
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.dialog = new ProgressDialog(context, 1);
			this.dialog.setMessage("Saving Donation....");
			this.dialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {

			String res = null;
			try {
				Log.v("donate", "Coffee Mate App Inserting");
				res  = CoffeeApi.insert((String) params[0], (Coffee) params[1]);

			}

			catch(Exception e)
			{
				Log.v("donate","ERROR : " + e);
				e.printStackTrace();
			}
			return res;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			String s  = result;
			Log.v("CoffeeInsert", "INSERT REQUEST : " + s);
//			new GetAllTask(Add.this).execute("/coffees");

			if (dialog.isShowing())
				dialog.dismiss();



		}
	}

}
