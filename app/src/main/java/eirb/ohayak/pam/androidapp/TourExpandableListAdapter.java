package eirb.ohayak.pam.androidapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TourExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Tour> tourList;

    public TourExpandableListAdapter(Context context, List<Tour> tours) {
        this.context = context;
        if (tours == null)
            tours = new ArrayList<Tour>(0);
        this.tourList = tours;
    }

    public void addTour (Tour t) {
        tourList.add(t);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return tourList.get(listPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return 0;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Tour tour = (Tour) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (tour.isActive()) {
                convertView = layoutInflater.inflate(R.layout.list_details2, null);
                Button saveTourButton = (Button) convertView.findViewById(R.id.btn_save);
                saveTourButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO
                    }
                });
            } else {
                convertView = layoutInflater.inflate(R.layout.list_details, null);
            }
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.txt_date);
        expandedListTextView.setText(tour.getStart());
        Button showTourButton = (Button) convertView.findViewById(R.id.btn_show);
        showTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tourIntent = new Intent(view.getContext(), MapsActivity.class);
                tourIntent.putExtra("tour", tour);
                context.startActivity(tourIntent);
            }
        });

        Button deleteTourButton = (Button) convertView.findViewById(R.id.btn_delete);
        deleteTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //TODO
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int listPosition) {
        return tourList.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return tourList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Tour tour = (Tour) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_tour, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.txt_name);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(tour.getName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return false;
    }
}