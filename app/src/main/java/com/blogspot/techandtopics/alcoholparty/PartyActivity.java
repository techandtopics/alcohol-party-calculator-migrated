package com.blogspot.techandtopics.alcoholparty;

import com.blogspot.techandtopics.alcoholparty.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PartyActivity extends Activity {

	private EditText numPeople;
	private EditText avgWeight;
	private EditText hourDuration;
	private EditText proof;

	private SeekBar bacBar;

	private TextView displayNeed;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.party);

		numPeople = (EditText) findViewById(R.id.peopleEntry);
		avgWeight = (EditText) findViewById(R.id.weightEntry);
		hourDuration = (EditText) findViewById(R.id.hourEntry);
		proof = (EditText) findViewById(R.id.proofEntry);

		bacBar = (SeekBar) findViewById(R.id.bac);
		bacBar.setProgress(bacBar.getMax() / 2);
		bacBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {       

		    @Override       
		    public void onStopTrackingTouch(SeekBar seekBar) {   
		    	String toastBAC = "BAC " + myFormat(String.valueOf(getBAC()));
		        Toast.makeText(getApplicationContext(), toastBAC,Toast.LENGTH_LONG).show();
		    }       

		    @Override       
		    public void onStartTrackingTouch(SeekBar seekBar) {  
		    }       

		    @Override       
		    public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {  
		    }       
		});
		
		displayNeed = (TextView) findViewById(R.id.displayNeed);
	}

	/**
	 * Override to save the values that were generated during runtime.
	 */
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("bundleValueNeed",
				(String) displayNeed.getText());
		super.onSaveInstanceState(savedInstanceState);
	}

	/**
	 * Override to restore the values that were generated during runtime.
	 */
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		displayNeed.setText(savedInstanceState.getString("bundleValueNeed"));
	}

	/**
	 * Inflate the menu from R.layout.menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	/**
	 * Display content based on menu item selected.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			showAbout();
			return true;
		case R.id.menu_help:
			showHelp();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Displays a dialog box with the about message.
	 */
	public void showAbout() {

		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setMessage(R.string.party_about_info);

		alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		alertbox.show();
	}

	/**
	 * Method to call showAbout from the button.
	 * @param view
	 */
	public void showAbout(View view) {
		showAbout();
	}

	/**
	 * Displays a dialog box with the help message.
	 */
	public void showHelp() {

		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setMessage(R.string.party_help_info);

		alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		alertbox.show();
	}

	/**
	 * Method to call showHelp from the button.
	 * @param view
	 */
	public void showHelp(View view) {
		showHelp();
	}

	/**
	 * Displays a dialog box with the error message.
	 */
	public void showError() {

		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setMessage(R.string.party_error_info);

		alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		alertbox.show();
	}

	/**
	 * Set variables from input and perform calculation.
	 * 
	 * @param View v
	 */
	public void calcParty(View v) {
		// close soft keyboard
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

		double bac;
		double hours;
		double vol;
		double percent;
		double totalVol;
		int peopleInt;
		int weightInt;
				
		if (numPeople.getText().toString().equals("") 
				|| avgWeight.getText().toString().equals("")
				|| proof.getText().toString().equals("")) {
			
			showError();
			
		} else {

			peopleInt = Integer.parseInt(numPeople.getText().toString());
			weightInt = Integer.parseInt(avgWeight.getText().toString());
			percent = (Double.parseDouble(proof.getText().toString()) / 200);
			bac = getBAC();
			
			if (hourDuration.getText().toString().equals("")) {				
				hours = 0;				
			} else {				
				hours = Double.parseDouble(hourDuration.getText().toString());				
			}
			if (0 >= percent || 100 < percent) {				
				showError();				
			} else {

				/*
				 * .015 * hours is the metabolized amount add to bac gives you
				 * the total for the duration of the party divide by 100
				 * multiply by (average weight per person * %water)
				 */
				if(bac==0){
					vol = 0;
				}else{
					vol = (((.015 * hours) + bac) / 100.0)
							* ((double) weightInt * .68);
				}
				/*
				 * divide by 1.055 divide by .0514 divide by percent alcohol
				 * (.xx) for result per person
				 */
				vol = vol / 1.055 / .0514 / percent;

				// total result
				totalVol = vol * peopleInt;

				// convert from oz to L. 33.8140226 oz per L
				totalVol = totalVol / 33.8140226;

				displayNeed.setText(myFormat(String.valueOf(totalVol)));
			}
		}
	}
	/**
	 * Returns the BAC that the seekbar is set to.
	 * @return double 
	 */
	public double getBAC(){
		return (((double) bacBar.getProgress() / (double) bacBar
				.getMax()) * .35);
	}

	/**
	 * Formats the decimal result to two decimal places.
	 * 
	 * @param String numberString
	 * @return String
	 */
	static public String myFormat(String numberString) {
		int indexDot = numberString.indexOf(".");
		if (numberString.contains(".")
				&& (numberString.length() - indexDot) > 3) {
			return numberString.substring(0, indexDot + 3);
		} else {
			return numberString;
		}
	}
}