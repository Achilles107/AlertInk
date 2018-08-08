package nnk.com.cwp11;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Achilles on 8/7/2018.
 */
public class ReminderActivity extends AppCompatActivity
{
    ListView listView;
    List<Reminder> list=new ArrayList<Reminder>();
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminders_layout);
        listView=(ListView)findViewById(R.id.list_reminders);
        new LoadReminders().execute();

    }

    private class LoadReminders extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            readReminders();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listView.setAdapter(new ReminderAdapter(ReminderActivity.this,list));
            if (dialog.isShowing())
            {
                dialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=ProgressDialog.show(ReminderActivity.this,"Loading Reminders","Please Wait...");
        }
    }

    private void readReminders()
    {
        String query="select * from "+MyDatabaseClass.TABLE_NAME;
        MyDatabaseClass myDatabaseClass=new MyDatabaseClass(ReminderActivity.this);
        SQLiteDatabase database=myDatabaseClass.getWritableDatabase();
        Cursor cursor=database.rawQuery(query,null);
        if (cursor.moveToFirst())
        {
            do {
                String rem_name=cursor.getString(0);
                String rem_number=cursor.getString(1);
                String rem_message=cursor.getString(2);
                String rem_birthday=cursor.getString(3);

                Reminder reminder=new Reminder(rem_name,rem_number,rem_birthday,rem_message);
                list.add(reminder);
            }while (cursor.moveToNext());
        }
    }
}
