package in.esmartsolution.shree.proadmin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.esmartsolution.shree.proadmin.api.ApiRequestHelper;
import in.esmartsolution.shree.proadmin.model.UserData;
import in.esmartsolution.shree.proadmin.utils.ConnectionDetector;
import in.esmartsolution.shree.proadmin.utils.Utils;
import in.esmartsolution.shree.proadmin.widget.materialprogress.CustomProgressDialog;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_email)
    MaterialEditText etEmail;
    @BindView(R.id.et_password)
    MaterialEditText etPassword;
    @BindView(R.id.tv_web)
    TextView tvWeb;
    @BindView(R.id.tv_emailMobile)
    TextView tvEmailMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (app.getPreferences().isLoggedInUser()) {
            startActivity(new Intent(mContext, MapsActivity.class));
            finish();
        }
//        etEmail.setText("9975172266");
//        etPassword.setText("pvn123");
        SpannableString ss = new SpannableString("www.esmartsolution.in");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                openBrowser(mContext, "www.esmartsolution.in");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 0, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new UnderlineSpan(), 0, 21, 0);
        tvWeb.setText(ss);
        tvWeb.setMovementMethod(LinkMovementMethod.getInstance());
        tvWeb.setHighlightColor(Color.TRANSPARENT);
        tvWeb.setLinkTextColor(Color.WHITE);
        tvWeb.setPaintFlags(tvWeb.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        SpannableString ss1 = new SpannableString("admin@esmartsolution.in / 7058420909");
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                sendEmail("admin@esmartsolution.in");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                callPhone("7058420909");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss1.setSpan(clickableSpan1, 0, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(clickableSpan2, 26, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(new UnderlineSpan(), 0, 23, 0);
        ss1.setSpan(new UnderlineSpan(), 26, 36, 0);
        tvEmailMobile.setText(ss1);
        tvEmailMobile.setMovementMethod(LinkMovementMethod.getInstance());
        tvEmailMobile.setHighlightColor(Color.TRANSPARENT);
        tvEmailMobile.setLinkTextColor(Color.WHITE);
        tvEmailMobile.setPaintFlags(tvEmailMobile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Enter Mobile number or email");
            etEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Enter Password");
            etPassword.requestFocus();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("usrname", email);
        params.put("passwrd", password);
        login(params);
    }

    private void login(Map<String, String> params) {
        ConnectionDetector cd = new ConnectionDetector(mContext);
        if (cd.isConnectingToInternet()) {
            final CustomProgressDialog pd = new CustomProgressDialog(mContext);
            pd.setTitle("Loading...");
            pd.show();
            app.getApiRequestHelper().login(params, new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    pd.dismiss();
                    UserData response = (UserData) object;
                    if (response != null) {
                        if (response.getResponsecode() == 200) {
                            if (response.getMessage() != null && !TextUtils.isEmpty(response.getMessage()))
                                Utils.showShortToast(mContext, response.getMessage());
                            app.getPreferences().setLoggedInUser(response);
                            startActivity(new Intent(mContext, MapsActivity.class));
                            finish();
                        } else {
                            if (response.getMessage() != null && !TextUtils.isEmpty(response.getMessage()))
                                Utils.showLongToast(mContext, response.getMessage());
                        }
                    } else {
                        Utils.showLongToast(mContext, Utils.UNPROPER_RESPONSE);
                    }
                }

                @Override
                public void onFailure(String apiResponse) {
                    pd.dismiss();
                    Utils.showLongToast(mContext, apiResponse);
                }
            });
        } else {
            Utils.alert_dialog(mContext);
        }
    }

    private static final String HTTPS = "https://";
    private static final String HTTP = "http://";

    public static void openBrowser(final Context context, String url) {

        if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
            url = HTTP + url;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(Intent.createChooser(intent, "Choose browser"));// Choose browser is arbitrary :)

    }

    private void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
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
}
