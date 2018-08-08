package nnk.com.cwp11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Achilles on 8/7/2018.
 */
public class ReminderAdapter extends BaseAdapter
{
    Context context;
    List<Reminder> reminders;
    public ReminderAdapter(Context context,List<Reminder> reminders)
    {
        this.context=context;
        this.reminders=reminders;
    }

    @Override
    public int getCount() {
        return reminders.size();
    }

    @Override
    public Reminder getItem(int position) {
        return reminders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view=convertView;
        if (view==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.reminders_content,null);
        }
        TextView rem_name=(TextView)view.findViewById(R.id.reminder_name);
        TextView rem_phone=(TextView)view.findViewById(R.id.reminder_number);
        TextView rem_birthday=(TextView)view.findViewById(R.id.reminder_birthday);
        TextView rem_message=(TextView)view.findViewById(R.id.reminder_message);
        Reminder reminder=reminders.get(position);
        rem_name.setText(reminder.getRem_name());
        rem_phone.setText(reminder.getRem_phone());
        rem_birthday.setText(reminder.getRem_birthday());
        rem_message.setText(reminder.getRem_message());
        return view;
    }
}
