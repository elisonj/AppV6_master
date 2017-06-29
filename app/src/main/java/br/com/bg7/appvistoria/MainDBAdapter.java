package br.com.bg7.appvistoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.vo.Product;

/**
 * Class adapter to fill ListView of products in MainActivity
 *
 * Created by elison on 28/06/17.
 */

public class MainDBAdapter extends BaseAdapter {

    private List<Product> list = new ArrayList<Product>();
    private MainActivity activity;
    private static LayoutInflater inflater=null;

    public MainDBAdapter (MainActivity activity, List<Product> list) {
        this.list = list;
        this.activity = activity;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Product getItem(int index) {
        return list.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View vi = convertView;

        vi = inflater.inflate(R.layout.widget_main_db_list, null);
        holder = new Holder();

        holder.textTitle1 = (TextView) vi.findViewById(R.id.textView_title1);
        holder.textSub1 = (TextView) vi.findViewById(R.id.textView_sub1);
        holder.textSub2 = (TextView) vi.findViewById(R.id.textView_sub2);
        vi.setTag(holder);
        populateWidget(holder, position);

        return vi;
    }

    private void populateWidget(final Holder holder, final int position){
        try {

            Product item = getItem(position);

            if(item != null) {
                holder.textTitle1.setText(item.getDetailedDescCompl());
                holder.textSub1.setText(item.getShortDesc());
                holder.textSub2.setText(item.getProductYourRef());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Holder holder = null;

    static class Holder {
        TextView textTitle1;
        TextView textSub1;
        TextView textSub2;
    }


}
