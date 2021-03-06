package nnk.com.cwp11;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    ListView listView;
    List<Reminder> list=new ArrayList<Reminder>();
    ProgressDialog dialog;
    AlertDialog.Builder alertDialog;
    TextView no_rem;
    String[] items={"Send Wishes Now","Delete"};
    public boolean exit=false;
    public ReminderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        listView=(ListView)findViewById(R.id.list_reminders);
        new LoadReminders().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                final Reminder rem=list.get(position);
                alertDialog=new AlertDialog.Builder(MainActivity.this);
                alertDialog.setIcon(android.R.drawable.stat_notify_more);
                alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: {
                                String phoneNo=rem.getRem_phone();
                                String name_of_person=rem.getRem_name();
                                String msg_to_send=rem.getRem_message();
                                Intent intent=new Intent(MainActivity.this,SendActivity.class);
                                intent.putExtra("name",name_of_person);
                                intent.putExtra("phone",phoneNo);
                                intent.putExtra("message",msg_to_send);
                                startActivity(intent);
                                //Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case 1: {
                                int id1=rem.getID();
                                MyDatabaseClass mdb=new MyDatabaseClass(MainActivity.this);
                                Reminder r=mdb.getReminder(id1);
                                mdb.deleteReminder(r);
                                new AlarmReceiver().cancelAlarm(getApplicationContext(), id1);
                                //Refresh the Activity.
                                finish();
                                startActivity(getIntent());
                                Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(),items[which],Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }
                });
                alertDialog.setCancelable(true);
                alertDialog.show();
            }
        });
        /*if (listView==null)
            Toast.makeText(MainActivity.this,"No Reminders",Toast.LENGTH_SHORT).show();*/
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(MainActivity.this,ContactActivity.class);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),"Add",Toast.LENGTH_SHORT).show();
            }
        });
        
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
            adapter=new ReminderAdapter(MainActivity.this,list);
            listView.setAdapter(adapter);
            if (dialog.isShowing())
            {
                dialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=ProgressDialog.show(MainActivity.this,"Loading Reminders","Please Wait...");
        }
    }

    private void readReminders()
    {
        MyDatabaseClass myDatabaseClass=new MyDatabaseClass(MainActivity.this);
        list=myDatabaseClass.getAllReminders();
        /*String query="select * from "+MyDatabaseClass.TABLE_NAME;
        MyDatabaseClass myDatabaseClass=new MyDatabaseClass(MainActivity.this);
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
        }*/
        /*else
        {
            no_rem=(TextView)findViewById(R.id.no_reminders);
            no_rem.setText("No reminders");
            //Toast.makeText(MainActivity.this,"No reminders",Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finishAffinity(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }


    }






