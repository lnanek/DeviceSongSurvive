package name.nanek.devicesongsurvive;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	private static final int REQUEST_ENABLE_BLUETOOTH = 0;

	private static final int REQUEST_SELECT_DEVICE = 1;

	private MidiView mMidiView;

	private BluetoothAdapter mBluetooth;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBluetooth = BluetoothAdapter.getDefaultAdapter();
		mMidiView = (MidiView) findViewById(R.id.midi_view);

		// Handle no Bluetooth support.
		if (mBluetooth == null) {
			new AlertDialog.Builder(this)
					.setMessage(
							"Tap notes in generated music to survive!\n\nPlay on a device with Bluetooth for extra features!")
					// Start game without Bluetooth.
					.setPositiveButton("OK", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mMidiView.startGame(null);
						}
					// Leave game.
					}).setOnCancelListener(new OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
							finish();
						}
					}).show();
		}

		new AlertDialog.Builder(this)
				.setMessage(
						"Tap notes in generated music to survive!\n\nSelect a Bluetooth device for a unique song each device!")
				.setPositiveButton("Select Device", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Request enable Bluetooth.
						if (!mBluetooth.isEnabled()) {
							startEnableBluetoothActivity();
							return;
						}

						// Otherwise can select a device immediately.
						startSelectDeviceActivity();
					}
				// Start game without Bluetooth.
				}).setNegativeButton("Skip", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mMidiView.startGame(null);
					}
				// Start game without Bluetooth.
				}).setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						mMidiView.startGame(null);
					}
				}).show();

	}

	private void startEnableBluetoothActivity() {
		final Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		if ( getPackageManager().queryIntentActivities(enableBtIntent, 0).isEmpty() ) {
			startSettingsActivity();
			return;
		}

		startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);
	}
	
	private void startSettingsActivity() {
		final Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		startActivityForResult(intent, REQUEST_ENABLE_BLUETOOTH);		
	}

	private void startSelectDeviceActivity() {
		Intent newIntent = new Intent(this, DeviceListActivity.class);
		startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		
		if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
			// Bluetooth was enabled.
			if (mBluetooth.isEnabled()) {
				// Pick device.
				mBluetooth.startDiscovery();
				startSelectDeviceActivity();
				return;
			}

			// Start game without Bluetooth.
			mMidiView.startGame(null);
			return;
		} 
			
		if (requestCode == REQUEST_SELECT_DEVICE) {
			if (resultCode == Activity.RESULT_OK && data != null) {
				// Start game with Bluetooth and selected device.
				final String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
				// use address of picked device as a random seed to always generate the same song.
				mMidiView.startGame((long) deviceAddress.hashCode());
				return;
			}

			// Start game without Bluetooth.
			mMidiView.startGame(null);
			return;
		}
	}// onActivityResult

}
