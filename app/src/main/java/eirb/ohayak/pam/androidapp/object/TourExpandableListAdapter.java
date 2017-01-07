package eirb.ohayak.pam.androidapp.object;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import eirb.ohayak.pam.androidapp.R;
import eirb.ohayak.pam.androidapp.activity.MainActivity;
import eirb.ohayak.pam.androidapp.activity.MapsActivity;
import eirb.ohayak.pam.androidapp.helper.TourHelper;
import eirb.ohayak.pam.androidapp.object.Tour;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TourExpandableListAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "TourExpandableListAdapter";
    private Context context;
    private ArrayList<Tour> tourList;
    private TourHelper th = TourHelper.getInstance();

    public TourExpandableListAdapter(Context context, List<Tour> tours) {
        this.context = context;
        if (tours == null)
            tours = new ArrayList<Tour>(0);
        this.tourList = (ArrayList<Tour>) tours;
    }
    public ArrayList<Tour> getTourList() {
        return tourList;
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
                        tour.setActive(false);
                        tour.setEnd(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                        th.update(tour);
                        Intent intent = new Intent("refresh");
                        LocalBroadcastManager.getInstance(view.getContext()).sendBroadcast(intent);
                    }
                });
            } else {
                convertView = layoutInflater.inflate(R.layout.list_details, null);
                TextView endDate = (TextView) convertView.findViewById(R.id.txt_end_date);
                endDate.setText(TourHelper.getDate(Long.parseLong(tour.getEnd()),"dd/MM/yyyy hh:mm"));
            }
        }
        Button showTourButton = (Button) convertView.findViewById(R.id.btn_show);
        showTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tourIntent = new Intent(view.getContext(), MapsActivity.class);
                tourIntent.putExtra("tour", tour);
                view.getContext().startActivity(tourIntent);
            }
        });

        Button deleteTourButton = (Button) convertView.findViewById(R.id.btn_delete);
        deleteTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                th.delete(tour.getId());
                Intent intent = new Intent("refresh");
                LocalBroadcastManager.getInstance(view.getContext()).sendBroadcast(intent);
            }
        });

        TextView startDate = (TextView) convertView.findViewById(R.id.txt_start_date);
        startDate.setText(TourHelper.getDate(Long.parseLong(tour.getStart()),"dd/MM/yyyy hh:mm"));
        TextView distance = (TextView) convertView.findViewById(R.id.txt_distance);
        distance.setText(Float.toString(tour.getDistance()));
        TextView speed = (TextView) convertView.findViewById(R.id.txt_speed);
        speed.setText(Float.toString(tour.getSpeed()));
        TextView topspeed = (TextView) convertView.findViewById(R.id.txt_topspeed);
        topspeed.setText(Float.toString(tour.getTopspeed()));

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