package nnk.com.cwp11;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Achilles on 8/28/2018.
 */
public class SendActivity extends AppCompatActivity
{
    public String phoneNo="";
    public String msg="";
    public String name_of_person="";
    public String TAG=".....";
    TextView textView;
    EditText editText;
    public static final String EXTRA_REMINDER_ID="Reminder_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message_layout);
        textView=(TextView)findViewById(R.id.name_birthday);
        editText=(EditText)findViewById(R.id.msg_text);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        phoneNo=bundle.getString("phone");
        msg=bundle.getString("message");
        name_of_person=bundle.getString("name");
        textView.setText(name_of_person);
        editText.setText(msg);
    }

    public void sendTextMsg(View view)
    {
        if (!editText.getText().toString().equals(""))
        {
            msg=editText.getText().toString();
            sendMessage(phoneNo, msg);
            Toast.makeText(getApplicationContext(),"Message sent",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No messages to wish",Toast.LENGTH_SHORT).show();
        }

    }
    public void sendMessage(String phone,String msgs)
    {
        try
        {
            SmsManager manager=SmsManager.getDefault();
            manager.sendTextMessage(phone,null,msgs,null,null);
        }catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    public void openWhatsapp(View view)
    {
        if (!editText.getText().toString().equals(""))
        {
            msg=editText.getText().toString();
            PackageManager manager=getPackageManager();
            try {
                String message=msg;
                String number=phoneNo;
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+number +"&text="+message));
                startActivity(intent);
            }catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"No such application found",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No messages to wish",Toast.LENGTH_SHORT).show();
        }
    }

    public void callThem(View view)
    {
        try
        {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED)
            {
                Log.d(TAG, "ACCESS_FINE_LOCATION permission OK");
            } else {
                Log.d(TAG, "ACCESS_FINE_LOCATION permission NG");
                return;
            }
            Intent intent=new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNo));
            startActivity(intent);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
