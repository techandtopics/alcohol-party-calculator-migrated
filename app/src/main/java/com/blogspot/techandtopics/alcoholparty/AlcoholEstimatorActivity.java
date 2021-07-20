/* 	   Alcohol Party Calc - Estimates the amount of alcohol needed for a party 
 * 						and a cost comparison that includes volume and proof.
 *         Copyright (C) 2012 George Yauneridge
 * 
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or (at
 *         your option) any later version.
 * 
 *         This program is distributed in the hope that it will be useful, but
 *         WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         General Public License for more details.
 * 
 *         You should have received a copy of the GNU General Public License
 *         along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.blogspot.techandtopics.alcoholparty;

import com.blogspot.techandtopics.alcoholparty.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * Party Alcohol Calc Source and support can be found at
 * <http://techandtopics.blogspot.com>. Version: 1.0 Target device: HTC
 * Incredible 2.3.4 and Galaxy S4 mini
 * 
 * This program calculates the amount of alcohol needed for a party. Also
 * includes a cost analysis comparing two bottles of alcohol, factoring in proof
 * and volume.
 * 
 * @author George Yauneridge
 * 
 */
public class AlcoholEstimatorActivity extends Activity{
			
	// cost half
	private EditText volumeEntry;
	private EditText proofEntry;
	private EditText priceEntry;

	private TextView displayCost;

	// party half
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
		setContentView(R.layout.main);
		
		Eula.show(this);
		
		// cost half
		volumeEntry = (EditText) findViewById(R.id.volumeEntry);
		proofEntry = (EditText) findViewById(R.id.proofEntry);
		priceEntry = (EditText) findViewById(R.id.priceEntry);

		displayCost = (TextView) findViewById(R.id.displayCost);

		// party half
		numPeople = (EditText) findViewById(R.id.peopleEntry);
		avgWeight = (EditText) findViewById(R.id.weightEntry);
		hourDuration = (EditText) findViewById(R.id.hourEntry);
		proof = (EditText) findViewById(R.id.partyProofEntry);

		bacBar = (SeekBar) findViewById(R.id.bac);
		if(bacBar!=null){
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
		}
		displayNeed = (TextView) findViewById(R.id.displayNeed);
	}

	/**
	 * Override to save the values that were generated during runtime.
	 */
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("bundleValueCost",
				(String) displayCost.getText());
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
		displayCost.setText(savedInstanceState.getString("bundleValueCost"));
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
		alertbox.setMessage(R.string.both_about_info);

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
		alertbox.setMessage(R.string.both_help_info);

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
		alertbox.setMessage(R.string.both_error_info);

		alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		alertbox.show();
	}
	
	public void clearResults(){
		displayCost.setText("");
		displayNeed.setText("");
	}
	/**
	 * 
	 * @param v
	 */
	public void calculate(View v) {
		clearResults();
		switch (checkFields()) {
		case 1:			
			calcCostAnalysis(v);
			break;
		case 2:
			calcParty(v);
			break;
		case 3:
			calcCostAnalysis(v);
			calcParty(v);
			break;
		default:
			showError();
		}
	}

	/**
	 * Method to check each editText.
	 * 
	 * @return int 0:error, 1:cost, 2:party, 3:cost+party
	 */
	public int checkFields() {
		int i = 0;
		//check cost half
		if (!(volumeEntry.getText().toString().equals(""))
				&& !(proofEntry.getText().toString().equals(""))
				&& !(priceEntry.getText().toString().equals(""))) {

			i += 1;

		} else if (!(volumeEntry.getText().toString().equals(""))
				|| !(proofEntry.getText().toString().equals(""))
				|| !(priceEntry.getText().toString().equals(""))) {

			return 0;
		}
		//check party half
		if (!(avgWeight.getText().toString().equals(""))
				&& !(numPeople.getText().toString().equals(""))
				&& !(proof.getText().toString().equals(""))) {

			i += 2;

		} else if (!(avgWeight.getText().toString().equals(""))
				|| !(numPeople.getText().toString().equals(""))
				|| !(proof.getText().toString().equals(""))) {

			return 0;
		}

		return i;
	}

	/**
	 * 
	 * @return
	 */
	public void calcCostAnalysis(View v) {
		// close soft keyboard
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

		double priceVolume = 0;

		int proof = Integer.parseInt(proofEntry.getText().toString());
		double volume = Double.parseDouble(volumeEntry.getText().toString());
		double price = Double.parseDouble(priceEntry.getText().toString());

		if (proof > 200 || proof < 1 || volume <= 0) {
			showError();
		} else {

			// divide proof by 200 to get a percent (.xx)
			// multiply by volume, then divide price by the answer
			priceVolume = price / (volume * ((double) proof / (double) 200));

			displayCost.setText(myFormatFour(String.valueOf(priceVolume)));
		}
	}

	/**
	 * 
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
				
		if (numPeople.getText().equals("") || avgWeight.getText().equals("")
				|| proof.getText().equals("")) {
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
			if (0 > percent || 100 < percent) {
				showError();
			} else {

				/*
				 * .015 * hours is the metabolized amount add to bac gives you
				 * the total for the duration of the party divide by 100
				 * multiply by (average weight per person * %water)
				 */
				vol = (((.015 * hours) + bac) / 100.0)
						* ((double) weightInt * .68);
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
	 * Start the Cost Activity when the button is pressed.
	 * 
	 * @param View v
	 */
	public void startCostActivity(View v) {
		Intent costIntent = new Intent(this, CostActivity.class);
		this.startActivity(costIntent);
	}

	/**
	 * Start the Party Activity when the button is pressed.
	 * 
	 * @param View v
	 */
	public void startPartyActivity(View v) {
		Intent partyIntent = new Intent(this, PartyActivity.class);
		this.startActivity(partyIntent);
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
	
	/**
	 * Formats the decimal result to four decimal places.
	 * 
	 * @param String numberString
	 * @return String
	 */
	static public String myFormatFour(String numberString) {
		int indexDot = numberString.indexOf(".");
		if (numberString.contains(".")
				&& (numberString.length() - indexDot) > 5) {
			return numberString.substring(0, indexDot + 5);
		} else {
			return numberString;
		}
	}	
}