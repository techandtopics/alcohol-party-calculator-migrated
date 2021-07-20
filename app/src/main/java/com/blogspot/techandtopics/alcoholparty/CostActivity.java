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
import android.widget.TextView;

public class CostActivity extends Activity {

	private EditText volumeEntry;
	private EditText proofEntry;
	private EditText priceEntry;

	private TextView displayCost;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost);

		volumeEntry = (EditText) findViewById(R.id.volumeEntry);
		proofEntry = (EditText) findViewById(R.id.proofEntry);
		priceEntry = (EditText) findViewById(R.id.priceEntry);

		displayCost = (TextView) findViewById(R.id.displayCost);
	}

	/**
	 * Override to save the values that were generated during runtime.
	 */
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("bundleValueCost",
				(String) displayCost.getText());
		super.onSaveInstanceState(savedInstanceState);
	}

	/**
	 * Override to restore the values that were generated during runtime.
	 */
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		displayCost.setText(savedInstanceState.getString("bundleValueCost"));
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
		alertbox.setMessage(R.string.cost_about_info);

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
		alertbox.setMessage(R.string.cost_help_info);

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
		alertbox.setMessage(R.string.cost_error_info);

		alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		alertbox.show();
	}

	/**
	 * 
	 * @param View v
	 */
	public void calcCostAnalysis(View v) {
		// close soft keyboard
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

		double priceVolume;
		int proof;
		double volume;
		double price;
		
		if (volumeEntry.getText().toString().equals("")
				|| proofEntry.getText().toString().equals("")
				|| priceEntry.getText().toString().equals("")) {			
			showError();			
		}else{
			
			proof = Integer.parseInt(proofEntry.getText().toString());
			volume = Double.parseDouble(volumeEntry.getText().toString());
			price = Double.parseDouble(priceEntry.getText().toString());
	
			if (proof > 200 || proof < 1 || volume <= 0) {
				showError();
			} else {
	
				// divide proof by 200 to get a percent (.xx)
				// multiply by volume, then divide price by the answer
				priceVolume = price / (volume * ((double) proof / (double) 200));
	
				displayCost.setText(myFormat(String.valueOf(priceVolume)));
			}
		}
	}

	/**
	 * Formats the decimal result to four decimal places.
	 * 
	 * @param String
	 *            numberString
	 * @return String
	 */
	static public String myFormat(String numberString) {
		int indexDot = numberString.indexOf(".");
		if (numberString.contains(".")
				&& (numberString.length() - indexDot) > 5) {
			return numberString.substring(0, indexDot + 5);
		} else {
			return numberString;
		}
	}
}