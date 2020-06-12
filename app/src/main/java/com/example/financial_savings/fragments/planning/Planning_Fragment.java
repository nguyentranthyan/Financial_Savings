package com.example.financial_savings.fragments.planning;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.budgets.HomeBudgetActivity;
import com.example.financial_savings.controllers.events.HomeEventActivity;
import com.example.financial_savings.controllers.savings.HomeSavingsActivity;
import com.example.financial_savings.interfaces.IMappingView;

public class Planning_Fragment extends Fragment implements IMappingView {
    private ImageButton buttonBudgetImg, buttonEventImg, buttonSavingsImg;
    private Button buttonBudget, buttonEvent, buttonSavings;
    private Button buttonBudgetInfo, buttonEventInfo, buttonSavingsInfo;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.planning_home, container, false);
        init();
        eventBudget();
        eventEvent();
        eventSavings();
        return root;
    }

    private void handlingSavings() {
        Intent intent = new Intent(getActivity(), HomeSavingsActivity.class);
        startActivity(intent);
    }

    private void handlingEvent() {
        Intent intent = new Intent(getActivity(), HomeEventActivity.class);
        startActivity(intent);
    }

    private void handlingBudget() {
        Intent intent = new Intent(getActivity(), HomeBudgetActivity.class);
        startActivity(intent);
    }

    private void eventSavings() {
        buttonSavingsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingSavings();
            }
        });
        buttonSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingSavings();
            }
        });
        buttonSavingsInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingSavings();
            }
        });
    }

    private void eventEvent() {
        buttonEventImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingEvent();
            }
        });
        buttonEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingEvent();
            }
        });
        buttonEventInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingEvent();
            }
        });
    }

    private void eventBudget() {
        buttonBudgetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingBudget();
            }
        });
        buttonBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingBudget();
            }
        });
        buttonBudgetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingBudget();
            }
        });
    }

    @Override
    public void init() {
        buttonBudgetImg = root.findViewById(R.id.buttonBudgetImg_planning_home);
        buttonEventImg = root.findViewById(R.id.buttonEventImg_planning_home);
        buttonSavingsImg = root.findViewById(R.id.buttonSavingsImg_planning_home);
        buttonBudget = root.findViewById(R.id.buttonBudget_planning_home);
        buttonEvent = root.findViewById(R.id.buttonEvent_planning_home);
        buttonSavings = root.findViewById(R.id.buttonSavings_planning_home);
        buttonBudgetInfo = root.findViewById(R.id.buttonBudgetInfo_planning_home);
        buttonEventInfo = root.findViewById(R.id.buttonEventInfo_planning_home);
        buttonSavingsInfo = root.findViewById(R.id.buttonSavingsInfo_planning_home);
    }
}
