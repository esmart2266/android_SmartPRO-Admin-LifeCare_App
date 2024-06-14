package in.esmartsolution.shree.proadmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import in.esmartsolution.shree.proadmin.adapter.VisitExpandableAdapter;
import in.esmartsolution.shree.proadmin.api.ApiRequestHelper;
import in.esmartsolution.shree.proadmin.model.Visit;
import in.esmartsolution.shree.proadmin.model.VisitData;
import in.esmartsolution.shree.proadmin.model.VisitMain;
import in.esmartsolution.shree.proadmin.utils.ConnectionDetector;
import in.esmartsolution.shree.proadmin.utils.Utils;
import in.esmartsolution.shree.proadmin.widget.materialprogress.CustomProgressDialog;

public class VisitsActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_error)
    TextView tv_error;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @BindView(R.id.rv_visits)
    RecyclerView rvVisits;
    DatePickerDialog dpd;
    private MenuItem action_search;
    private ArrayList<Visit> visitList;
    private VisitExpandableAdapter visitExpandableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("Visits");
        rvVisits.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        allvisits();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_visits;
    }

    private void allvisits() {
        ConnectionDetector cd = new ConnectionDetector(mContext);
        if (cd.isConnectingToInternet()) {
            final CustomProgressDialog pd = new CustomProgressDialog(mContext);
            pd.setTitle("Loading...");
            pd.show();
            app.getApiRequestHelper().allvisits(new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    pd.dismiss();
                    try {
                        VisitData response = (VisitData) object;
                        if (response != null) {
                            if (response.getResponsecode() == 200) {
                                if (response.getVisits() != null && response.getVisits().size() > 0) {
                                    visitList = response.getVisits();
                                    Collections.sort(visitList, Collections.reverseOrder());
                                    Set<String> stringSet = new HashSet<>();
                                    for (int i = 0; i < visitList.size(); i++) {
                                        stringSet.add(visitList.get(i).getUserId());
                                    }
                                    List<String> strings = new ArrayList<>(stringSet);
                                    List<VisitMain> visitMainList = new ArrayList<>();
                                    for (int i = 0; i < strings.size(); i++) {
                                        ArrayList<Visit> visits = new ArrayList<>();
                                        VisitMain v = new VisitMain();
                                        for (int j = 0; j < visitList.size(); j++) {
                                            if (strings.get(i).matches(visitList.get(j).getUserId())) {
                                                Visit visit = visitList.get(j);
                                                v.setFirstName(visit.getFirstName());
                                                v.setLastName(visit.getLastName());
                                                v.setUserId(visit.getUserId());
                                                visits.add(visit);
                                            }
                                        }
                                        Collections.sort(visits, Collections.reverseOrder());
                                        v.setVisitList(visits);
                                        v.setMaxVisitDateTime(visits.get(0).getVisitDateTime());
                                        visitMainList.add(v);
                                    }
                                    String maxDateTime = visitList.get(0).getVisitDateTime(); //2017-02-05 23:44:33
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date maxDate = null;
                                    try {
                                        maxDate = format.parse(maxDateTime);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Calendar maxCalendar = Calendar.getInstance();
                                    maxCalendar.setTime(maxDate);

                                    String minDateTime = visitList.get(visitList.size() - 1).getVisitDateTime();
                                    Date minDate = null;
                                    try {
                                        minDate = format.parse(minDateTime);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Calendar minCalendar = Calendar.getInstance();
                                    minCalendar.setTime(minDate);
                                    dpd = DatePickerDialog.newInstance(
                                            new DatePickerDialog.OnDateSetListener() {
                                                @Override
                                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
                                                    String selectedMinDate = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
                                                    String selectedMaxDate = yearEnd + "- " + (++monthOfYearEnd) + "-" + dayOfMonthEnd;
                                                    try {
                                                        List<VisitMain> newList = getNewList(visitList, selectedMinDate, selectedMaxDate);
                                                        Collections.sort(newList, Collections.reverseOrder());
                                                        visitExpandableAdapter = new VisitExpandableAdapter(mContext, newList);
                                                        rvVisits.setAdapter(visitExpandableAdapter);
                                                        if (newList.size() > 0)
                                                            tv_error.setVisibility(View.GONE);
                                                        else {
                                                            tv_error.setText("No Records available.");
                                                            tv_error.setVisibility(View.VISIBLE);
                                                        }
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            },
                                            minCalendar.get(Calendar.YEAR), minCalendar.get(Calendar.MONTH),
                                            minCalendar.get(Calendar.DAY_OF_MONTH), maxCalendar.get(Calendar.YEAR),
                                            maxCalendar.get(Calendar.MONTH), maxCalendar.get(Calendar.DAY_OF_MONTH));
                                    dpd.setThemeDark(false);
                                    dpd.setMaxDate(maxCalendar);
                                    dpd.setMinDate(minCalendar);
                                    visitExpandableAdapter = new VisitExpandableAdapter(mContext, visitMainList);
                                    rvVisits.setAdapter(visitExpandableAdapter);
                                    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                                    showCurrentDayData(currentDate, visitList);
                                    rvVisits.setVisibility(View.VISIBLE);
                                    action_search.setVisible(true);
                                } else {
                                    tv_error.setText("No Records available.");
                                    tv_error.setVisibility(View.VISIBLE);
                                    rvVisits.setVisibility(View.GONE);
                                }
                            } else {
                                if (response.getMessage() != null && !TextUtils.isEmpty(response.getMessage()))
                                    Utils.showLongToast(mContext, response.getMessage());
                                rvVisits.setVisibility(View.GONE);
                                tv_error.setText("No Records available.");
                                tv_error.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Utils.showLongToast(mContext, Utils.UNPROPER_RESPONSE);
                            rvVisits.setVisibility(View.GONE);
                            tv_error.setText("No Records available.");
                            tv_error.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        Utils.showLongToast(mContext,e.getLocalizedMessage());
                    }

                }

                @Override
                public void onFailure(String apiResponse) {
                    pd.dismiss();
                    Utils.showLongToast(mContext, apiResponse);
                    tv_error.setText("No Records available.");
                    tv_error.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Utils.alert_dialog(mContext);
        }
    }

    private void showCurrentDayData(String currentDate, List<Visit> visitList) {
        try {
            List<VisitMain> newList = getNewList(visitList, currentDate, currentDate);
            Collections.sort(newList, Collections.reverseOrder());
            visitExpandableAdapter = new VisitExpandableAdapter(mContext, newList);
            rvVisits.setAdapter(visitExpandableAdapter);
            if (newList.size() > 0)
                tv_error.setVisibility(View.GONE);
            else {
                tv_error.setText("No Records available. Please Select FROM - TO date");
                tv_error.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private List<VisitMain> getNewList(List<Visit> oldList, String selectedMinDate,
                                       String selectedMaxDate) throws ParseException {
        Date d1 = null, d2 = null, d3 = null, d4 = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        d3 = f.parse(selectedMinDate);
        d4 = f.parse(selectedMaxDate);
        List<Visit> visitList = new ArrayList<>();
        for (int i = 0; i < oldList.size(); i++) {
            String b = oldList.get(i).getVisitDateTime();
            d2 = f.parse(b);
            if (d2.compareTo(d3) >= 0 && d2.compareTo(d4) <= 0) {
                visitList.add(oldList.get(i));
            }
        }
        Set<String> stringSet = new HashSet<>();
        for (int i = 0; i < visitList.size(); i++) {
            stringSet.add(visitList.get(i).getUserId());
        }
        List<String> strings = new ArrayList<>(stringSet);
        List<VisitMain> visitMainList = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            ArrayList<Visit> visits = new ArrayList<>();
            VisitMain v = new VisitMain();
            for (int j = 0; j < visitList.size(); j++) {
                if (strings.get(i).matches(visitList.get(j).getUserId())) {
                    Visit visit = visitList.get(j);
                    v.setFirstName(visit.getFirstName());
                    v.setLastName(visit.getLastName());
                    v.setUserId(visit.getUserId());
                    visits.add(visit);
                }
            }
            Collections.sort(visits, Collections.reverseOrder());
            v.setVisitList(visits);
            v.setMaxVisitDateTime(visits.get(0).getVisitDateTime());
            visitMainList.add(v);
        }
        return visitMainList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        action_search = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_filter:
                if (dpd != null)
                    dpd.show(getFragmentManager(), "DatePickerDialog");
                return true;
            case R.id.action_search:
                if (visitExpandableAdapter != null && visitExpandableAdapter.getMainList() != null && visitExpandableAdapter.getMainList().size() > 0) {
                    ArrayList<Visit> visits = new ArrayList<>();
                    List<VisitMain> mainList = visitExpandableAdapter.getMainList();
                    for (int i = 0; i < mainList.size(); i++)
                        visits.addAll(mainList.get(i).getVisitList());
                    app.getPreferences().setVisitList(visits);
                    startActivity(new Intent(mContext, SearchVisitsActivity.class));
                } else {
                    app.getPreferences().setVisitList(visitList);
                    startActivity(new Intent(mContext, SearchVisitsActivity.class));
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
