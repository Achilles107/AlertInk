package nnk.com.cwp11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Achilles on 8/3/2018.
 */
public class ContactAdapter extends BaseAdapter
{
    Context context;
    private List<ContactModel> list;
    public ContactAdapter(Context context,List<ContactModel>list)
    {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ContactModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View viewHoder=convertView;
        if (viewHoder==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHoder=inflater.inflate(R.layout.contact_content,null);

        }
        TextView name=(TextView)viewHoder.findViewById(R.id.contact_name);
        TextView phone_number=(TextView)viewHoder.findViewById(R.id.contact_number);
        ContactModel contactModel=list.get(position);

        name.setText(contactModel.getName());
        phone_number.setText(contactModel.getPhone_number());
        return viewHoder;
    }
}
