package ie.cm.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBDesigner extends SQLiteOpenHelper {
	public static final String 	TABLE_COFFEE= "table_coffee";
	public static final String 	COLUMN_ID = "id";
	public static final String 	COLUMN_PRICE = "price";
	public static final String 	COLUMN_SHOP = "shop";
	public static final String 	COLUMN_NAME = "name";
	public static final String 	COLUMN_RATING = "rating";
	public static final boolean COLUMN_FAVOURITE = false;
	private static final String DATABASE_NAME = "coffeematedb";
	private static final int 	DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE_TABLE_COFFEE = "create table "
			+ TABLE_COFFEE + "( " + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_PRICE + " integer not null,"
			+ COLUMN_NAME + " text not null);,"
			+ COLUMN_SHOP + " text not null);,"
            + COLUMN_RATING + " text not null);,"
            + COLUMN_FAVOURITE + " boolean not null);";
		
	public DBDesigner(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_TABLE_COFFEE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBDesigner.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COFFEE);
		onCreate(db);
	}
}