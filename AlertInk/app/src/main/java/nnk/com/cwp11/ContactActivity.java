package nnk.com.cwp11;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);
        getSupportActionBar().setTitle("Select Contact");

        listView=(ListView)findViewById(R.id.list_contacts);
        //
        new LoadContacts().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ContactModel model=list.get(position);
                builder=new AlertDialog.Builder(ContactActivity.this);
                builder.setTitle(model.getName());
                builder.setMessage(model.getPhone_number());
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
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
}
