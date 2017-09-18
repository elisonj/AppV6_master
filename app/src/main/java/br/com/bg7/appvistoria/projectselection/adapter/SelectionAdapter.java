package br.com.bg7.appvistoria.projectselection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.bg7.appvistoria.R;

/**
 * Created by: luciolucio
 * Date: 2017-09-16
 */

public abstract class SelectionAdapter<T> extends BaseAdapter {

    private List<T> items;
    private Context context;

    SelectionAdapter(List<T> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.projectselection_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(T item, ViewHolder holder) {
        holder.title.setText(getText(item));
    }

    abstract String getText(T item);

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    private class ViewHolder {

        TextView title;

        private ViewHolder(View view) {
            title = view.findViewById(R.id.title);
        }
    }
}
