package br.edu.fatec.chamada;

import java.util.TooManyListenersException;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class AlunoActivity extends Activity {
	
	private static final String TAG = "AlunoActivity";
	private static final int REQUEST_ENABLE_BT = 100;

	/**
	 * 
	 */
	private CheckBox chkBluetoothStatus;
	private ToggleButton tbtnBluetoothStatus;

	/****************************************************************************************************
	 * 
	 ****************************************************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aluno);

		// Pega referências da UI
		chkBluetoothStatus = (CheckBox) findViewById(R.id.checkbox_enable_bluetooth);
		if(chkBluetoothStatus != null)
			chkBluetoothStatus.setOnClickListener(new ChangeBluetoothStatus());
		else
			Log.e(TAG, "CheckButton not found!");
		
		
		tbtnBluetoothStatus = (ToggleButton) findViewById(R.id.toggleButtonBuetooth);
		if(tbtnBluetoothStatus != null)
			tbtnBluetoothStatus.setClickable(false);	
		//inicializaStatus();
	}
	

	/****************************************************************************************************
	 * @author ghsilva
	 *
	 ****************************************************************************************************/
	private class ChangeBluetoothStatus implements OnClickListener {

		@Override
		public void onClick(View v) {
			if(v != null && v == chkBluetoothStatus) {
				Log.i(TAG , "onClick: " + chkBluetoothStatus.isChecked());
				BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
				if (blueAdapter != null && blueAdapter.isEnabled() == chkBluetoothStatus.isChecked()) {
					// nothing to do
				}
				else {
				    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
				}
			}
			else
				Log.i(TAG , "How come this log appears?");
			
		}
		
	}
	
	/****************************************************************************************************
	 * 
	 ****************************************************************************************************/
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		inicializaStatus();
		super.onResume();
	}

	/****************************************************************************************************
	 * 
	 ****************************************************************************************************/
	private void inicializaStatus() {
		/* Get BLuetooth Initial Status */
		if (tbtnBluetoothStatus != null) {
			tbtnBluetoothStatus.setChecked(false);
			BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
			if (blueAdapter != null) {
				tbtnBluetoothStatus.setChecked(blueAdapter.isEnabled());
			}
			else {
				tbtnBluetoothStatus.setEnabled(false);
			}
		}
	}

	/****************************************************************************************************
	 * 
	 ****************************************************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aluno, menu);
		return true;
	}

	/****************************************************************************************************
	 * 
	 ****************************************************************************************************/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			
			if(resultCode == RESULT_OK) {
				Log.v(TAG, " BLuetooth activity result: ok");
				chkBluetoothStatus.setChecked(true);
			}
			else { 
				Log.v(TAG, " BLuetooth activity result: Cancel");
				chkBluetoothStatus.setChecked(false);
			}
			break;

		default:
			break;
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	

}
