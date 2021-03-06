package edu.umd.cs.semesterproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.model.Action;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.TimeRule;
import edu.umd.cs.semesterproject.util.Codes;

// The Fragment used to create Wifi Time Rules.
public class WifiTimeFragment extends BaseTimeFragment {

    private final String TAG = "WifiTimeFragment";

    public static Fragment newInstance(String userID){
        Bundle bundle = new Bundle();
        bundle.putString(Codes.RULE_ID, userID);
        WifiTimeFragment ruleFragment = new WifiTimeFragment();
        ruleFragment.setArguments(bundle);
        return ruleFragment;
    }

    @Override
    protected Rule.ActionType getActionType() {
        return Rule.ActionType.WIFI;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wifi_time;
    }

    @Override
    protected void setupSpecificLayout(View view, TimeRule rule) {
        WifiFragment.setupSpecificLayout(view, rule);
    }

    @Override
    protected Action getAction() {
        return WifiFragment.getAction(view);
    }
}
