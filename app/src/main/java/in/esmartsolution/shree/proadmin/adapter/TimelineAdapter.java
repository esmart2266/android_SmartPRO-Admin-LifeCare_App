package in.esmartsolution.shree.proadmin.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.esmartsolution.shree.proadmin.App;
import in.esmartsolution.shree.proadmin.R;
import in.esmartsolution.shree.proadmin.model.Visit;
import in.esmartsolution.shree.proadmin.utils.Utils;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.RecyclerViewHolder> {
    public List<Visit> visitList;
    List<Visit> list_search = new ArrayList<>();
    Context mContext;
    App app;

    public TimelineAdapter(Context mContext, List<Visit> visitList) {
        this.visitList = visitList;
        this.mContext = mContext;
        list_search.addAll(visitList);
        app = (App) mContext.getApplicationContext();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_timeline, parent, false);
        return new RecyclerViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Visit visit = visitList.get(position);
        holder.tvAddress.setText(visit.getGeoAddress());
        holder.tvDistance.setText(visit.getDistance());
        holder.tvTime.setText(Utils.ymdHmsTodmyHma(visit.getVisitDateTime()));
        holder.tvDistance.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return visitList != null && visitList.size() > 0 ? visitList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvDistance)
        TextView tvDistance;
        @BindView(R.id.tvTime)
        TextView tvTime;
        public TimelineView mTimelineView;

        public RecyclerViewHolder(View itemView, int viewType) {
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.timeline);
            mTimelineView.initLine(viewType);
            ButterKnife.bind(this, itemView);
        }
    }

    public void callPhone(String phoneNo) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        } else {
            final MaterialDialog ok = new MaterialDialog.Builder(mContext)
                    .content("Do you want to call this phone number?")
                    .positiveText("Yes")
                    .negativeText("No")
                    .show();
            ok.getActionButton(DialogAction.POSITIVE).setOnClickListener(view -> {
                ok.dismiss();
                mContext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNo)));
            });
            ok.getActionButton(DialogAction.NEGATIVE).setOnClickListener(view -> ok.dismiss());
        }
    }

    private void sendsms(String mobile) {
        Uri uri = Uri.parse("smsto:" + mobile);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", "");
        mContext.startActivity(it);
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        visitList.clear();
        if (charText.length() == 0) {
            visitList.addAll(list_search);
        } else {
            for (int i = 0; i < list_search.size(); i++) {
                Visit g = list_search.get(i);
                if (g.getDoctorName().toLowerCase().contains(charText) ||
                        g.getCity().toLowerCase().contains(charText) ||
                        g.getSpeciality().toLowerCase().contains(charText) ||
                        g.getMobile().toLowerCase().contains(charText) ||
                        g.getGeoAddress().toLowerCase().contains(charText) ||
                        (g.getLandline() != null && !TextUtils.isEmpty(g.getLandline())
                                && g.getLandline().toLowerCase().contains(charText)) ||
                        g.getArea().toLowerCase().contains(charText)) {
                    visitList.add(list_search.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }
}