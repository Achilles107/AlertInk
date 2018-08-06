package nnk.com.cwp11;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Achilles on 8/2/2018.
 */
public class FlashActivity extends Activity
{
    private static int TIMEOUT=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(FlashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },TIMEOUT);
    }
}
