package br.edu.fatec.chamada;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

public class ProfessorActivity extends Activity {
	
	private Button btnChamada;
	private static final String TAG = "ProfessorActivity";
	private static final int REQUEST_ENABLE_BT = 100;
	private BroadcastReceiver mReceiver;
	private List<Aluno> mListAlunos;
	private ListView lvAlunos;
	private ProgressBar progressBar; 

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_professor);
		
		btnChamada = (Button) findViewById(R.id.btnFazChamada);
		if (btnChamada != null) {
			btnChamada.setOnClickListener(new IniciaChamada());
		}

		lvAlunos = (ListView) findViewById(R.id.listAlunos);
		
		mReceiver = null;
		mListAlunos = new ArrayList<Aluno>();
		
		// Create a progress bar to display while the list loads
        progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        progressBar.setIndeterminate(true);
	}
	
	
	
	
	/****************************************************************************************************
	 * 
	 ****************************************************************************************************/
	private void registerBluetoothReceiver() {
		// Create a BroadcastReceiver for ACTION_FOUND
		
		if (mReceiver == null) {
			mReceiver = new BluetoothBroadcastReceiver();
		
			// TODO: Não esqueça de remover o broadcastReceier dentro do método OnDestroy
		
			// Registra o BroadcastReceiver para saber quando um dispositivo é encontrado
			IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			registerReceiver(mReceiver, filter); 
		
			filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
			registerReceiver(mReceiver, filter);
		
			filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
			registerReceiver(mReceiver, filter);


			filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
			registerReceiver(mReceiver, filter);
		}
	}

	/****************************************************************************************************
	 * @author ghsilva
	 *
	 ****************************************************************************************************/
	private class BluetoothBroadcastReceiver extends BroadcastReceiver {

		/* (non-Javadoc)
		 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
	        String action = intent.getAction();
	        Log.d(TAG, "BluetoothBReciever action: " + action);
	        // When discovery finds a device
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	            // Get the BluetoothDevice object from the Intent
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            // Add the name and address to an array adapter to show in a ListView
	            Aluno aluno = new Aluno();
	            aluno.setNome("Aluno " + mListAlunos.size());
	            aluno.setBluetoothMacAddress(device.getAddress());
	            mListAlunos.add(aluno);
	        }
	        else if(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
	        }
	        else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
	        	lvAlunos.setEmptyView(progressBar);
	        	 // Must add the progress bar to the root of the layout
	            ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
	            root.addView(progressBar);
	        }
	        else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
	            ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
	            root.removeView(progressBar);

	        	updateList();
	        }
		}
	
	
	}
	
	/****************************************************************************************************
	 * 
	 ****************************************************************************************************/
	protected void updateList() {
    	AlunoArrayAdapter a = new AlunoArrayAdapter(this, mListAlunos.toArray());
    	lvAlunos.setAdapter(a);
	}
	
	
	/****************************************************************************************************
	 * @author ghsilva
	 *
	 ****************************************************************************************************/
	private class IniciaChamada implements OnClickListener {

		@Override
		public void onClick(View v) {
			BluetoothAdapter bAdapt = BluetoothAdapter.getDefaultAdapter();
			if(bAdapt == null) {
				//TODO: Bluetooth não suportado
			}
			else {
				if(!bAdapt.isEnabled()) {
					Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
				}
				else {
					bAdapt.startDiscovery();
				}
			}
			
		}
		
	}
	
	/****************************************************************************************************
	 * @author ghsilva
	 *
	 ****************************************************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.professor, menu);
		return true;
	}

	/****************************************************************************************************
	 * @author ghsilva
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
	
	/****************************************************************************************************
	 * @author ghsilva
	 *
	 ****************************************************************************************************/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			
			if(resultCode == RESULT_OK) {
				Log.v(TAG, " BLuetooth activity result: ok");
							}
			else { 
				Log.v(TAG, " BLuetooth activity result: Cancel");
					
			}
			break;

		default:
			break;
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	protected void RequestBluetoothStatusUpdate (int status)
	{
	}




	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		
		registerBluetoothReceiver();
		
		super.onResume();
	}



	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
			mReceiver = null;
		}

		super.onPause();
	}
	
}
