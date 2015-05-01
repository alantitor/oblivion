package ntou.cs.lab505.oblivion.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ntou.cs.lab505.oblivion.Parameters.Record;
import ntou.cs.lab505.oblivion.Parameters.type.GainAdd;

/**
 * Created by alan on 4/30/15.
 */
public class GSAdapter {

    Context mCtx;
    DBHelper mDbHelper;
    SQLiteDatabase mDb;

    public GSAdapter(Context context) {
        this.mCtx = context;
    }

    public DBHelper open() {
        this.mDbHelper = new DBHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this.mDbHelper;
    }

    public void close() {
        this.mDbHelper.close();
    }

    public void saveData(ArrayList<GainAdd> list) {
        String[] projectin = {TableContract.T_GAIN_USERID,
                                TableContract.T_GAIN_GROUPID,
                                TableContract.T_GAIN_L40,
                                TableContract.T_GAIN_L60,
                                TableContract.T_GAIN_L80,
                                TableContract.T_GAIN_r40,
                                TableContract.T_GAIN_r40,
                                TableContract.T_GAIN_r60,
                                TableContract.T_GAIN_r80,
                                TableContract.T_GAIN_STATE};
        String selection = TableContract.T_BAND_USERID + " = ? ";
        String[] selectionArgs = {String.valueOf(Record.USERID)};
        String soetOrder = "";
        Cursor c = mDb.query(TableContract.TABLE_GAIN, projectin, selection, selectionArgs, null, null,soetOrder);
        c.moveToFirst();

        // delete old data in db.
        if (c.getCount() > 0) {
            mDb.delete(TableContract.TABLE_GAIN, TableContract.T_GAIN_USERID + " = ? ", new String[]{String.valueOf(Record.USERID)});
        }

        for(int i= 0; i < list.size(); i++) {
            GainAdd ga = list.get(i);  // get object from list.
            ContentValues insertValues = new ContentValues();
            insertValues.put(TableContract.T_GAIN_USERID, Record.USERID);
            insertValues.put(TableContract.T_GAIN_GROUPID, i);
            insertValues.put(TableContract.T_GAIN_L40, ga.getL40());
            insertValues.put(TableContract.T_GAIN_L60, ga.getL60());
            insertValues.put(TableContract.T_GAIN_L80, ga.getL80());
            insertValues.put(TableContract.T_GAIN_r40, ga.getR40());
            insertValues.put(TableContract.T_GAIN_r60, ga.getR60());
            insertValues.put(TableContract.T_GAIN_r80, ga.getR80());
            insertValues.put(TableContract.T_GAIN_STATE, 1);
            mDb.insert(TableContract.TABLE_GAIN, null, insertValues);
        }
    }

    //public ArrayList<GainAdd> getData() {

    //}
}
