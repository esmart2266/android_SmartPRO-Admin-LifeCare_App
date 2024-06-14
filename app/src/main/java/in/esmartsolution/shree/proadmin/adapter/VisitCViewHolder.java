package in.esmartsolution.shree.proadmin.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.esmartsolution.shree.proadmin.R;
import in.esmartsolution.shree.proadmin.model.Visit;
import in.esmartsolution.shree.proadmin.utils.Utils;
import in.esmartsolution.shree.proadmin.widget.expandablerecyclerview.ChildViewHolder;

public class VisitCViewHolder extends ChildViewHolder {
    View view;
    @BindView(R.id.tv_doctorName)
    TextView tvDoctorName;
    @BindView(R.id.tv_speciality)
    TextView tvSpeciality;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
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
    @BindView(R.id.iv_mobile)
    ImageView ivMobile;
    Context mContext;

    public VisitCViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        this.view = view;
        this.mContext = mContext;
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull final Visit visit) {
        tvDoctorName.setText(visit.getDoctorName());
        tvSpeciality.setText(visit.getSpeciality());
        if (visit.getMobile() != null) {
            tvMobile.setText(visit.getMobile());
            ivMobile.setVisibility(View.VISIBLE);
        } else {
            ivMobile.setVisibility(View.GONE);
        }
        tvLandline.setText(visit.getLandline());
        if (visit.getBirthDate() != null && !visit.getBirthDate().equals("0000-00-00"))
            tvBirthdate.setText(Utils.ymdTodmy(visit.getBirthDate()));
        tvArea.setText(visit.getArea());
        tvCity.setText(visit.getCity());
        tvRemark.setText(visit.getRemarks());
        tvDateTime.setText(Utils.ymdHmsTodmyHms(visit.getVisitDateTime()));
        tvGeoAddress.setText(visit.getGeoAddress());
        tvMobile.setPaintFlags(tvMobile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvLandline.setPaintFlags(tvLandline.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvMobile.setOnClickListener(v -> callPhone(visit.getMobile()));
        ivMobile.setOnClickListener(v -> sendsms(visit.getMobile()));
        tvLandline.setOnClickListener(v -> callPhone(visit.getLandline()));
    }

    public View getView() {
        return view;
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
}