package com.example.administrator.flash.ui;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.flash.R;
import com.example.administrator.flash.ui.adapter.MyRecylerViewAdapter;
import com.example.administrator.flash.utils.NavigatorUtils;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private String[] strs;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Replace with your own action",Snackbar.LENGTH_SHORT).
                        setAction("Action",null).show();
            }
        });
        StringBuilder stringBuilder=new StringBuilder();
        String str="Hello word!#";
        for (int i = 0; i < 30; i++) {
            stringBuilder.append(str);
        }
        strs=stringBuilder.toString().split("#");
        rv= (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this,1));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(new MyRecylerViewAdapter(context,strs));
    }
    public Context getContext(){
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.action_settings:
                return true;
            case R.id.action_client:
                NavigatorUtils.toChooseFileUI(getContext());
                return true;
            case R.id.action_server:
                NavigatorUtils.toReceiveWaitingUI(getContext());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
