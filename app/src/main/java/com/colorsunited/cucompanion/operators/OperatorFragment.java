package com.colorsunited.cucompanion.operators;
/*
 * Created by Khrys.
 *
 * App : RainbowSixAssistant
 * Info : 05/31/2017 [00:00 PM]
*/

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colorsunited.cucompanion.R;
import com.colorsunited.cucompanion.data.LoadData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

public class OperatorFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView mRecycler;
        RecyclerView.LayoutManager mLayout;

        mRecycler = view.findViewById(R.id.WeaponsRecycler);

        mRecycler.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(mLayout);

        ArrayList<Operator> operatorsAtk = new ArrayList<>();
        ArrayList<Operator> operatorsDef = new ArrayList<>();

        try
        {
            LoadData loadData = new LoadData();
            JSONArray operatorsList = loadData.loadList(getContext(), loadData.RES_OPERATORS);
            JSONObject operatorsData = loadData.loadData(getContext(), loadData.RES_OPERATORS);

            for (int i = 0; i < operatorsList.length(); i++)
            {
                String operatorId = operatorsList.getString(i);
                JSONObject operator = operatorsData.getJSONObject(operatorId);

                int imgid = getResources().getIdentifier("o_" + operatorId, "drawable", view.getContext().getPackageName());
                Operator operatorForList = new Operator(operatorId, imgid, operator.getString("name"));

                if(operator.getInt("side") == 1)
                {
                    operatorsAtk.add(operatorForList);
                }
                else
                {
                    operatorsDef.add(operatorForList);
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        ArrayList<Operator> finalOperators = new ArrayList<>(operatorsAtk);
        finalOperators.addAll(operatorsDef);

        OperatorHeaderListAdapter mAdapter = new OperatorHeaderListAdapter(finalOperators, operatorsAtk.size());
        mRecycler.setAdapter(mAdapter);
        mRecycler.addItemDecoration(new StickyHeaderDecoration(mAdapter));
    }
}