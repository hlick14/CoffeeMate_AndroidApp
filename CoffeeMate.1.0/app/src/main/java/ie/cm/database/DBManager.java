package ie.cm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ie.cm.models.Coffee;

public class DBManager {

	private SQLiteDatabase database;
	private ie.cm.database.DBDesigner dbHelper;

	public DBManager(Context context) {
		dbHelper = new ie.cm.database.DBDesigner(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public void add(Coffee d) {
		ContentValues values = new ContentValues();

		values.put(ie.cm.database.DBDesigner.COLUMN_NAME, d.coffeName);
		values.put(ie.cm.database.DBDesigner.COLUMN_SHOP, d.coffeeShop);
		values.put(ie.cm.database.DBDesigner.COLUMN_PRICE,d.coffePrice);
		values.put(String.valueOf(DBDesigner.COLUMN_RATING), d.rating);
		values.put(String.valueOf(DBDesigner.COLUMN_FAVOURITE),d.favorite);

		database.insert(ie.cm.database.DBDesigner.TABLE_COFFEE, null, values);
	}

	public List<Coffee> getAll() {
		List<Coffee> coffees = new ArrayList<Coffee>();
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ ie.cm.database.DBDesigner.TABLE_COFFEE, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Coffee d = toCoffee(cursor);
			coffees.add(d);
			cursor.moveToNext();
		}
		cursor.close();
		return coffees;
	}

	private Coffee toCoffee(Cursor cursor) {

		Coffee pojo = new Coffee();
        pojo._id = cursor.getString(0);
        Log.v("DBMANAGER 0 ", cursor.getString(0));
        pojo.coffePrice = cursor.getDouble(1);
        Log.v("DBMANAGER 1 ", cursor.getString(1));
		pojo.coffeName = cursor.getString(2);
        Log.v("DBMANAGER 2", cursor.getString(2));
        pojo.coffeeShop = cursor.getString(3);
        pojo.rating = cursor.getDouble(4);

		return pojo;
	}

//	public void setTotalDonated(Base base) {
//		Cursor c = database.rawQuery("SELECT SUM(" + ie.cm.database.DBDesigner.COLUMN_PRICE + ") FROM "
//									+ ie.cm.database.DBDesigner.TABLE_DONATION, null);
//		c.moveToFirst();
//		if (!c.isAfterLast())
////			base.totalDonated = c.getInt(0);
//	}

	public void reset() {
		database.delete(DBDesigner.TABLE_COFFEE, null, null);
	}
}
