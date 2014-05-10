package com.matrix.mym.controller.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.matrix.mym.model.CompanyShare;

public class CompanyShareDB {
	public static final String TABLE_TABLE = "company_share";
	public static final String COL_ID = "_id";
	public static final String COL_NAME = "name";
	public static final String COL_PRICE = "price";
	public static final String COL_LAST_PRICE = "last_price";
	public static final String COL_INDUSTRY = "industry";
	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_TABLE + "(" + COL_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME
			+ " VARCHAR(128) NOT NULL, " + COL_PRICE + " REAL NOT NULL, "
			+ COL_LAST_PRICE + " REAL NOT NULL, " + COL_INDUSTRY
			+ " VARCHAR(128) NOT NULL);";

	private DatabaseHelper mDatabaseHelper;

	public CompanyShareDB(DatabaseHelper databaseHelper) {
		mDatabaseHelper = databaseHelper;
	}

	synchronized public boolean updatePriceAndLastPrice(
			CompanyShare companyShare) {
		long result;
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COL_PRICE, companyShare.getPrice());
			contentValues
					.put(COL_LAST_PRICE, companyShare.getLastPriceChange());
			SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
			result = db.update(TABLE_TABLE, contentValues, COL_ID + "=? ",
					new String[] { companyShare.getId() + "" });
			db.close();
		} catch (SQLException e) {
			result = 0;
		}
		return result != 0;
	}

	synchronized public ArrayList<CompanyShare> getCompanyShares() {
		ArrayList<CompanyShare> companyShares = new ArrayList<CompanyShare>();
		try {
			SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
			Cursor cursor = db.query(TABLE_TABLE, new String[] { COL_ID,
					COL_NAME, COL_PRICE, COL_LAST_PRICE, COL_INDUSTRY }, null,
					null, null, null, COL_ID);
			while (cursor.moveToNext()) {
				long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
				String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
				float price = cursor.getFloat(cursor.getColumnIndex(COL_PRICE));
				float lastPrice = cursor.getFloat(cursor
						.getColumnIndex(COL_LAST_PRICE));
				String industry = cursor.getString(cursor
						.getColumnIndex(COL_INDUSTRY));
				companyShares.add(new CompanyShare(id, name, price, lastPrice,
						industry));
			}
			cursor.close();
			db.close();
		} catch (IllegalStateException ex) {
		}
		return companyShares;
	}

	public static void setUpTable(SQLiteDatabase db) {
		db.execSQL(CompanyShareDB.CREATE_TABLE);
		inserCompanyShare(db, "Axis Bank", 100, "Banking");
		inserCompanyShare(db, "Cipla", 50, "Pharmaceuticals");
		inserCompanyShare(db, "Bharat Heavy Electricals", 37.50f,
				"Electrical equipment");
		inserCompanyShare(db, "State Bank Of India", 536, "Banking");
		inserCompanyShare(db, "HDFC Bank", 300, "Banking");
		inserCompanyShare(db, "Hero Motocorp", 200, "Automotive");
		inserCompanyShare(db, "Infosys", 995.5f, "Information Technology");
		inserCompanyShare(db, "Oil and Natural Gas Corporation", 57,
				"Oil and gas");
		inserCompanyShare(db, "Reliance Industries", 122, "Oil and gas");
		inserCompanyShare(db, "Tata Power", 512, "Power");
		inserCompanyShare(db, "Hindalco Industries", 700, "Metals and Mining");
		inserCompanyShare(db, "Tata Steel", 30, "Steel");
		inserCompanyShare(db, "Larsen & Toubro", 233, "Conglomerate");
		inserCompanyShare(db, "Mahindra & Mahindra", 55, "Automotive");
		inserCompanyShare(db, "Tata Motors", 654, "Automotive");
		inserCompanyShare(db, "Hindustan Unilever", 111, "Consumer goods");
		inserCompanyShare(db, "ITC", 234, "Conglomerate");
		inserCompanyShare(db, "Sesa Sterlite Ltd", 675, "Iron and Steel");
		inserCompanyShare(db, "Wipro", 93, "Information Technology");
		inserCompanyShare(db, "Sun Pharmaceutical", 56, "Pharmaceuticals");
		inserCompanyShare(db, "GAIL", 90, "Oil and gas");
		inserCompanyShare(db, "ICICI Bank", 100, "Banking");
		inserCompanyShare(db, "Housing Development Fianance Corporation	", 33,
				"Housing Finance");
		inserCompanyShare(db, "Bharti Airtel", 45, "Telecommunication");
		inserCompanyShare(db, "Maruti Suzuki", 54, "Automotive");
		inserCompanyShare(db, "Tata Consultancy Services", 23,
				"Information Technology");
		inserCompanyShare(db, "NTPC", 56, "Power");
		inserCompanyShare(db, "Dr. Reddy's", 865, "Pharmaceuticals");
		inserCompanyShare(db, "Bajaj Auto", 432, "Automotive");
		inserCompanyShare(db, "Coal India", 321, "Metals and Mining");
	}

	private static void inserCompanyShare(SQLiteDatabase db, String name,
			float price, String industry) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(COL_NAME, name);
		contentValues.put(COL_PRICE, price);
		contentValues.put(COL_LAST_PRICE, price);
		contentValues.put(COL_INDUSTRY, industry);
		db.insert(TABLE_TABLE, COL_ID, contentValues);
	}
}