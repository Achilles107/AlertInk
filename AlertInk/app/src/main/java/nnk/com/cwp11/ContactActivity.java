package nnk.com.cwp11;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Achilles on 8/3/2018.
 */
public class ContactActivity extends AppCompatActivity
{
    private static List<ContactModel> list=new ArrayList<ContactModel>();
    private static ListView listView;
    private static ContactAdapter contactAdapter;
    private static ProgressDialog progressDialog;
    private static AlertDialog.Builder builder;
    private static Button button;
    private static LayoutInflater layoutInflater1;
    private static AlertDialog alertDialog;
    private static EditText editText;
    int day,month,year;
    String name_db,phone_number_db,dob_db,message_db;
    Calendar calendar;
    private static TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);
        getSupportActionBar().setTitle("Select Contact");

        listView=(ListView)findViewById(R.id.list_contacts);

        new LoadContacts().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ContactModel model = list.get(position);
                builder = new AlertDialog.Builder(ContactActivity.this);
                layoutInflater1 = getLayoutInflater();
                View dview = layoutInflater1.inflate(R.layout.inside_dialog, null);
                builder.setTitle(model.getName());
                builder.setMessage(model.getPhone_number());
                builder.setView(dview);
                textView = (TextView) dview.findViewById(R.id.date);
                button = (Button) dview.findViewById(R.id.set_date);
                editText=(EditText)dview.findViewById(R.id.message);
                alertDialog = builder.create();
                textView.setText("dd/mm/yyyy");
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                showDate(year,month+1,day);
                //Dialog d=new Dialog(ContactActivity.this);
                //button=(Button)d.findViewById(R.id.set_date);
                /*button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                        Toast.makeText(getApplicationContext(),"Set",Toast.LENGTH_SHORT).show();
                    }
                });*/
                //builder.setMessage("Set Birth date");
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        name_db=model.getName().toString();
                        phone_number_db=model.getPhone_number().toString();
                        message_db=editText.getText().toString();
                        dob_db=textView.getText().toString();
                        try
                        {
                            MyDatabaseClass myDatabaseClass=new MyDatabaseClass(ContactActivity.this);
                            SQLiteDatabase database=myDatabaseClass.getWritableDatabase();
                            String query="insert into " + MyDatabaseClass.TABLE_NAME +" values ('" + name_db + "','" + phone_number_db + "','" + message_db + "','" + dob_db + "')";
                            database.execSQL(query);
                            Toast.makeText(ContactActivity.this,"USER_CREATED",Toast.LENGTH_SHORT).show();
                            myDatabaseClass.close();
                            startActivity(new Intent(ContactActivity.this,ReminderActivity.class));
                        }catch (Exception e)
                        {
                            Log.e("User Created",""+e);}
                        //Toast.makeText(getApplicationContext(),name_db+"",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        /*calendar = Calendar.getInstance();
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                        month = calendar.get(Calendar.MONTH);
                        year = calendar.get(Calendar.YEAR);*/
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                //Toast.makeText(getApplicationContext(),model.getName()+"",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private class LoadContacts extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            readContacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listView.setAdapter(new ContactAdapter(ContactActivity.this, list));
            if (progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=ProgressDialog.show(ContactActivity.this,"Loading Contacts","Please Wait...");

        }
    }

    private void readContacts()
    {
        //List<ContactModel> newList=list;
        Cursor phones=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone_number=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            ContactModel contactModel=new ContactModel(name,phone_number);
            list.add(contactModel);
        }
        phones.close();
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view)
    {
        showDialog(999);
        //Toast.makeText(ContactActivity.this, "HI", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id==999)
        {
            return new DatePickerDialog(ContactActivity.this,myDateListener,year,month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            showDate(year,monthOfYear+1,dayOfMonth);

        }
    };

    public void showDate(int year,int month,int day)
    {
        textView.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }
}
