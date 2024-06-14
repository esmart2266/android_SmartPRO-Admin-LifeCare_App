package in.esmartsolution.shree.proadmin.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import in.esmartsolution.shree.proadmin.R;
import in.esmartsolution.shree.proadmin.model.Visit;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<Visit> expandableListTitle;
    private HashMap<Visit, List<Visit>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<Visit> expandableListTitle, HashMap<Visit, List<Visit>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Visit visit = (Visit) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            ButterKnife.bind(this, convertView);
        }
        TextView tvDoctorName = convertView.findViewById(R.id.tv_doctorName);
        TextView tvSpeciality = convertView.findViewById(R.id.tv_speciality);
        TextView tvMobile = convertView.findViewById(R.id.tv_mobile);
        TextView tvLandline = convertView.findViewById(R.id.tv_landline);
        TextView tvArea = convertView.findViewById(R.id.tv_area);
        TextView tvCity = convertView.findViewById(R.id.tv_city);
        TextView tvRemark = convertView.findViewById(R.id.tv_remark);
        TextView tvDateTime = convertView.findViewById(R.id.tv_dateTime);
        tvDoctorName.setText(visit.getDoctorName());
        tvSpeciality.setText(visit.getSpeciality());
        tvMobile.setText(visit.getMobile());
        tvLandline.setText(visit.getLandline());
        tvArea.setText(visit.getArea());
        tvCity.setText(visit.getCity());
        tvRemark.setText(visit.getRemarks());
        tvDateTime.setText(visit.getVisitDateTime());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Visit listTitle = (Visit) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        ImageView iv_arrow = convertView.findViewById(R.id.iv_arrow);
        if (isExpanded) {
            iv_arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        } else {
            iv_arrow.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);
        }
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle.getFirstName() + " " + listTitle.getLastName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}