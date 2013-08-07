package org.sagemath.droid;

import org.sagemath.droid.CellGroupsFragment.OnGroupSelectedListener;

import sheetrock.panda.changelog.ChangeLog;

import com.example.android.actionbarcompat.ActionBarActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class CellActivity
		extends ActionBarActivity 
		implements OnGroupSelectedListener{
	private final static String TAG = "CellActivity";

	private ChangeLog changeLog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CellCollection.initialize(getApplicationContext());
		setContentView(R.layout.cell_activity);
		
		changeLog = new ChangeLog(this);
        if (changeLog.firstRun())
            changeLog.getLogDialog().show();
		
		CellGroupsFragment groupsFragment = (CellGroupsFragment) 
				getSupportFragmentManager().findFragmentById(R.id.cell_groups_fragment);
		groupsFragment.setOnGroupSelected(this);
		
		CellListFragment listFragment = (CellListFragment)
				getSupportFragmentManager().findFragmentById(R.id.cell_list_fragment);
		if (listFragment != null && listFragment.isInLayout()) 
			listFragment.switchToGroup(null);
	}
	
	public static final String INTENT_SWITCH_GROUP = "intent_switch_group";
	
	@Override
	public void onGroupSelected(String group) {
		CellListFragment listFragment = (CellListFragment)
				getSupportFragmentManager().findFragmentById(R.id.cell_list_fragment);
		if (listFragment == null || !listFragment.isInLayout()) {
			Intent i = new Intent(getApplicationContext(), CellListActivity.class);
			i.putExtra(INTENT_SWITCH_GROUP, group);
			startActivity(i);
		} else {
			listFragment.switchToGroup(group);
		}
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case android.R.id.home:
    		finish();
    		return true;
    	}
        return super.onOptionsItemSelected(item);
    }
	
}