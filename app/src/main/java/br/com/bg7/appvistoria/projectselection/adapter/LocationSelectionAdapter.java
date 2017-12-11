package br.com.bg7.appvistoria.projectselection.adapter;

import android.content.Context;

import java.util.List;

import br.com.bg7.appvistoria.projectselection.vo.Location;

/**
 * Created by: elison
 * Date: 2017-09-16
 */
public class LocationSelectionAdapter extends SelectionAdapter<Location> {

    public LocationSelectionAdapter(List<Location> items, Context context) {
        super(items, context);
    }

    @Override
    String getText(Location item) {
        return item.getAddress();
    }
}
