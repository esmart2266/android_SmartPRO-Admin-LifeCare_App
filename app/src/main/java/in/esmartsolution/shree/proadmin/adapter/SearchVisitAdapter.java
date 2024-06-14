package in.esmartsolution.shree.proadmin.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.esmartsolution.shree.proadmin.App;
import in.esmartsolution.shree.proadmin.R;
import in.esmartsolution.shree.proadmin.SearchVisitsActivity;
import in.esmartsolution.shree.proadmin.model.Visit;
import in.esmartsolution.shree.proadmin.utils.Utils;

public class SearchVisitAdapter extends RecyclerView.Adapter<SearchVisitAdapter.RecyclerViewHolder> {
    public List<Visit> visitList;
    List<Visit> list_search = new ArrayList<>();
    Context mContext;
    App app;

    public SearchVisitAdapter(Context mContext, List<Visit> visitList) {
        this.visitList = visitList;
        this.mContext = mContext;
        list_search.addAll(visitList);
        app = (App) mContext.getApplicationContext();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_visit, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Visit visit = visitList.get(position);
        holder.tvDoctorName.setText(visit.getDoctorName());
        holder.tvSpeciality.setText(visit.getSpeciality());
        if (visit.getMobile() != null) {
            holder.tvMobile.setText(visit.getMobile());
            holder.ivMobile.setVisibility(View.VISIBLE);
        } else {
            holder.ivMobile.setVisibility(View.GONE);
        }
        holder.tvLandline.setText(visit.getLandline());
        holder.tvArea.setText(visit.getArea());
        if (visit.getBirthDate() != null && !visit.getBirthDate().equals("0000-00-00"))
            holder.tvBirthdate.setText(Utils.ymdTodmy(visit.getBirthDate()));
        holder.tvCity.setText(visit.getCity());
        holder.tvRemark.setText(visit.getRemarks());
        holder.tvDateTime.setText(Utils.ymdHmsTodmyHms(visit.getVisitDateTime()));
        holder.tvGeoAddress.setText(visit.getGeoAddress());
        holder.tvMobile.setPaintFlags(holder.tvMobile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.tvLandline.setPaintFlags(holder.tvLandline.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.tvMobile.setOnClickListener(v -> callPhone(visitList.get(holder.getAdapterPosition()).getMobile()));
        holder.ivMobile.setOnClickListener(v -> sendsms(visitList.get(holder.getAdapterPosition()).getMobile()));
        holder.tvLandline.setOnClickListener(v -> callPhone(visitList.get(holder.getAdapterPosition()).getLandline()));
        holder.iv_check.setVisibility(visit.isSelected() ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(v -> ((SearchVisitsActivity) mContext).multi_select(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return visitList != null && visitList.size() > 0 ? visitList.size() : 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_doctorName)
        TextView tvDoctorName;
        @BindView(R.id.tv_speciality)
        TextView tvSpeciality;
        @BindView(R.id.tv_mobile)
        TextView tvMobile;
        @BindView(R.id.iv_mobile)
        ImageView ivMobile;
        @BindView(R.id.tv_landline)
        TextView tvLandline;
        @BindView(R.id.tv_birthdate)
        TextView tvBirthdate;
        @BindView(R.id.tv_area)
        TextView tvArea;
        @BindView(R.id.tv_city)
        TextView tvCity;
        @BindView(R.id.tv_remark)
        TextView tvRemark;
        @BindView(R.id.tv_dateTime)
        TextView tvDateTime;
        @BindView(R.id.tv_geoAddress)
        TextView tvGeoAddress;
        @BindView(R.id.iv_check)
        ImageView iv_check;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
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