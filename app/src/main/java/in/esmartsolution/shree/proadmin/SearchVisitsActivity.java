package in.esmartsolution.shree.proadmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.esmartsolution.shree.proadmin.adapter.SearchVisitAdapter;
import in.esmartsolution.shree.proadmin.model.Visit;
import in.esmartsolution.shree.proadmin.utils.Utils;

public class SearchVisitsActivity extends BaseActivity {
    @BindView(R.id.rv_visits)
    RecyclerView rv_visits;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_searchCity)
    EditText etSearchCity;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    private SearchVisitAdapter searchVisitAdapter;
    List<Visit> multiselect_list = new ArrayList<>();
    private List<Visit> visitList;
    private MenuItem action_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        visitList = getIntent().getParcelableArrayListExtra("visitList");
        visitList = app.getPreferences().getVisitList();
        rv_visits.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//        rv_visits.addOnItemTouchListener(new RecyclerItemClickListener(mContext, (view, position) -> ));
        searchVisitAdapter = new SearchVisitAdapter(mContext, visitList);
        rv_visits.setAdapter(searchVisitAdapter);
        etSearchCity.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    searchVisitAdapter.filter(s.toString());
                } else {
                    searchVisitAdapter.filter("");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        etSearchCity.requestFocus();
        try {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_search_visits;
    }

    public void multi_select(int position) {
        if (multiselect_list.contains(visitList.get(position))) {
            String id = searchVisitAdapter.visitList.get(position).getVisitId();
            for (int i = 0; i < visitList.size(); i++) {
                if (visitList.get(i).getVisitId().equals(id)) {
                    visitList.get(i).setSelected(false);
                    multiselect_list.remove(visitList.get(i));
                }
            }
        } else {
            String id = searchVisitAdapter.visitList.get(position).getVisitId();
            for (int i = 0; i < visitList.size(); i++) {
                if (visitList.get(i).getVisitId().equals(id)) {
                    visitList.get(i).setSelected(true);
                    multiselect_list.add(visitList.get(i));
                }
            }
        }
        if (multiselect_list.size() > 0 && action_message != null) {
            action_message.setVisible(true);
        } else if (action_message != null) {
            action_message.setVisible(false);
        }
        searchVisitAdapter.notifyDataSetChanged();
    }

    private void sendsms(String mobile) {
        Uri uri = Uri.parse("smsto:" + mobile);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", "");
        mContext.startActivity(it);
    }

    @OnClick({R.id.iv_back, R.id.iv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_message:
                if (multiselect_list.size() > 0) {
                    List<String> mobileList = new ArrayList<>();
                    for (int i = 0; i < multiselect_list.size(); i++) {
                        if (multiselect_list.get(i).getMobile() != null && !TextUtils.isEmpty(multiselect_list.get(i).getMobile()))
                            mobileList.add(multiselect_list.get(i).getMobile());
                    }
                    if (mobileList.size() > 0) {
                        String replace = mobileList.toString().replace("[", "").replace("]", "").replace(" ", "").replace(",", ";");
                        sendsms(replace);
                    }
                } else {
                    Utils.showLongToast(mContext, "Select record to send sms");
                }
                break;
        }
    }
}
