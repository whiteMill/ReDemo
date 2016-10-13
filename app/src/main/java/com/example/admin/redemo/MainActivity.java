package com.example.admin.redemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button selectAll;
    private Button cancelAll;
    private Button edit;
    private MeListView mListView;
    private TextView mTextView;
    private ArrayList<String> sList = new ArrayList<>();
    private mBaseAdapter mAdapter;
    private  Boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initDate();
        mAdapter = new mBaseAdapter(sList,this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mBaseAdapter.ViewHolder viewHolder = (mBaseAdapter.ViewHolder) view.getTag();
                    viewHolder.mCheckBox.toggle();
                    mAdapter.getIsSelected().put(position,viewHolder.mCheckBox.isChecked());
                    Log.d("TTT",viewHolder.mCheckBox.isChecked()+"+++");
                    mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void init() {
        selectAll = (Button) findViewById(R.id.selectAll);
        cancelAll = (Button) findViewById(R.id.cancelAll);
        edit = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(this);
        selectAll.setOnClickListener(this);
        cancelAll.setOnClickListener(this);
        mListView = (MeListView) findViewById(R.id.mListView);
        mTextView = (TextView) findViewById(R.id.mTextView);
    }


    private void initDate() {
        for (int i = 0; i < 100; i++) {
            sList.add(i + "条数据");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectAll:
                for (int i = 0; i < 100; i++) {
                    mBaseAdapter.getIsSelected().put(i,true);
                    dataChange();
                }
                break;
            case R.id.cancelAll:
                for (int i = 0; i < 100; i++) {
                    if(mBaseAdapter.getIsSelected().get(i)){
                        mBaseAdapter.getIsSelected().put(i,false);
                    }else{
                        mBaseAdapter.getIsSelected().put(i,true);
                    }
                    dataChange();
                }
                break;
            case R.id.edit:
                if(flag){
                    mListView.allDeleteShow();
                    edit.setText("完成");
                    for (int i = 0; i < 100; i++) {
                        mBaseAdapter.getIsVisible().put(i,true);
                    }
                    flag=false;
                }else{
                    mListView.cancaelShow();
                    edit.setText("编辑");
                    for (int i = 0; i < 100; i++) {
                        mBaseAdapter.getIsVisible().put(i,true);
                    }
                    flag=true;
                }
                dataChange();
                break;
        }
    }


    private void dataChange(){
        mAdapter.notifyDataSetChanged();
    }


}
