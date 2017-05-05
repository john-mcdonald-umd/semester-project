package edu.umd.cs.semesterproject.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

import edu.umd.cs.semesterproject.DependencyFactory;
import edu.umd.cs.semesterproject.R;
import edu.umd.cs.semesterproject.VolumeLocationActivity;
import edu.umd.cs.semesterproject.VolumeTimeActivity;
import edu.umd.cs.semesterproject.fragment.VolumeTimeFragment;
import edu.umd.cs.semesterproject.model.Rule;
import edu.umd.cs.semesterproject.model.TimeRule2;
import edu.umd.cs.semesterproject.service.RuleService;
import edu.umd.cs.semesterproject.util.Codes;

public class RuleTypeDialogFragment extends DialogFragment implements View.OnClickListener {

    private Button mTimeRuleTextView;
    private Button mLocationRuleTextView;

    public static final int REQUEST_CODE_CREATE_RULE_TIME = 0;
    public static final int REQUEST_CODE_CREATE_RULE_LOCATION = 8;

    private static final int PLACE_PICKER_REQUEST = 199;
    public RuleTypeDialogFragment() {
    }

    public static RuleTypeDialogFragment newInstance() {
        RuleTypeDialogFragment ruleTypeDialogFragment = new RuleTypeDialogFragment();

        return ruleTypeDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_rule_type, null, false);

        mTimeRuleTextView = (Button) view.findViewById(R.id.text_view_time_rule);
        mTimeRuleTextView.setOnClickListener(this);
        mLocationRuleTextView = (Button) view.findViewById(R.id.text_view_location_rule);
        mLocationRuleTextView.setOnClickListener(this);

        builder.setView(view)
                .setTitle("Rule Type")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        return dialog;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.text_view_time_rule:
                Intent intent = VolumeTimeActivity.newIntent(getActivity(), null);
                startActivityForResult(intent, REQUEST_CODE_CREATE_RULE_TIME);
                break;
            // Started an activity for Place Picker to select a location
            case R.id.text_view_location_rule:

                Intent locationIntent = new Intent(getActivity(), VolumeLocationActivity.class);
                startActivityForResult(locationIntent, REQUEST_CODE_CREATE_RULE_LOCATION);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Codes.REQUEST_CODE_CREATE_RULE && resultCode == Activity.RESULT_OK) {
            Serializable result = data.getSerializableExtra(VolumeTimeFragment.RULE_CREATED);
            Rule timeRule = (Rule) result;
            RuleService ruleService = DependencyFactory.getRuleService(getActivity());
            ruleService.addRule(timeRule);
            Log.d("RuleTypeDialogFragment", "Created rule " + timeRule.getName() + " with rule type " + timeRule.getRuleType());
        }


        /*
        // Retrieves the location selected by the user through Place Picker
        else if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
            }
        }

        */
        dismiss();
    }
}
