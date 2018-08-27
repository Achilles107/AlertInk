package nnk.com.cwp11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Achilles on 8/6/2018.
 */
public class MyDatabaseClass extends SQLiteOpenHelper
{
    public static final String DBNAME="birthdays";
    public static final int VER=1;
    public static final String TABLE_NAME="reminders";
    public static final String KEY_ID="id";
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
        String query="create table " + TABLE_NAME + "("+ KEY_ID + " INTEGER PRIMARY KEY," + NAME + " TEXT," + PHONE + " TEXT," + MES + " TEXT," +DOB+ " TEXT)";
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

    public int addReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME,reminder.getRem_name());
        values.put(PHONE,reminder.getRem_phone());
        values.put(MES,reminder.getRem_message());
        values.put(DOB, reminder.getRem_birthday());
        long ID=db.insert(TABLE_NAME,null,values);
        db.close();
        return (int) ID;
    }

    //getAll reminders
    public List<Reminder> getAllReminders()
    {
        List<Reminder> reminderList=new ArrayList<Reminder>();
        //Select all query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do {
                int id=Integer.parseInt(cursor.getString(0));
                String rem_name=cursor.getString(1);
                String rem_number=cursor.getString(2);
                String rem_message=cursor.getString(3);
                String rem_birthday=cursor.getString(4);

                Reminder reminder=new Reminder(id,rem_name,rem_number,rem_birthday,rem_message);
                reminderList.add(reminder);
            }while (cursor.moveToNext());
        }
        return reminderList;
    }

    //get single reminder
    public Reminder getReminder(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME, new String[]{KEY_ID, NAME, PHONE, MES, DOB}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor!=null)
            cursor.moveToFirst();
        Reminder reminder=new Reminder(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        return  reminder;
    }

    public void deleteReminder(Reminder reminder)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID + "=?",new String[]{String.valueOf(reminder.getID())});
        db.close();
    }

    }
