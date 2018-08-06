package nnk.com.cwp11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Achilles on 8/6/2018.
 */
public class MyDatabaseClass extends SQLiteOpenHelper
{
    public static final String DBNAME="birthdays";
    public static final int VER=1;
    public static final String TABLE_NAME="reminders";
    public static final String NAME="name";
    public static final String PHONE="phone";
    public static final String MES="message";
    public static final String DOB="date_of_birth";
    Context context;
    public MyDatabaseClass(Context context)
    {
        super(context,DBNAME,null,VER);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query="create table " + TABLE_NAME + "(" + NAME + " TEXT, " + PHONE + " TEXT," + MES + " TEXT," +DOB+ " TEXT)";
        try
        {
            db.execSQL(query);
            Toast.makeText(context,"TABLE CREATED",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("Table Faulty",""+e);}

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
