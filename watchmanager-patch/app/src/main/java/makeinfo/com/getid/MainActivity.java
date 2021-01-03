package makeinfo.com.getid;

import hack.getid.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ru.bartwell.exfilepicker.ExFilePickerActivity;

import lanchon.dexpatcher.annotation.*;

@DexEdit
public class MainActivity extends ActionBarActivity {

    @DexIgnore EditText device;
    @DexIgnore Button device_copy;
    //@DexIgnore EditText gsfid;
    //@DexIgnore Button gsf_copy;
    //@DexIgnore EditText imei;
    //@DexIgnore Button imei_copy;
    //@DexIgnore EditText imsi;
    //@DexIgnore Button imsi_copy;
    //@DexIgnore EditText sim;
    //@DexIgnore Button sim_copy;
    //@DexIgnore EditText wifi;
    //@DexIgnore Button wifi_copy;

    //@DexIgnore ImageView makeinfoapps;
    @DexIgnore boolean val;

    // Append code to the existing onCreate method.
    @DexAppend
    @Override protected void onCreate(Bundle bundle){
        // After executing the original onCreate method, replace the text and handler
        // of the device ID 'Copy' button to make it a 'Share' button instead.
        device_copy.setText("Share");
        device_copy.setOnClickListener(new DeviceCopyOnClick());
    }

    // Use a named class to avoid clashing with existing anonymous classes.
    @DexAdd
    class DeviceCopyOnClick implements View.OnClickListener {
        @Override public void onClick(View view) {
            if (MainActivity.this.val) {
                // Instead of copying the device ID, share it.
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Device ID");
                intent.putExtra(Intent.EXTRA_TEXT, device.getText().toString());
                startActivity(Intent.createChooser(intent, "Share Device ID"));
            } else {
                Toast.makeText(MainActivity.this.getApplicationContext(), "Nothing to Share", 0).show();
            }
        }
    }

    // Wrap the existing onOptionsItemSelected method.
    @DexWrap
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // We imported this file chooser from the project's 'build.gradle' script:
        // -> https://github.com/bartwell/ExFilePicker
        // Let's make the 'Rate' option menu item open the file chooser.
        int id = item.getItemId();
        // Note that our dynamically generated 'R' class (hack.getid.R) includes
        // the imported resources from the source app.
        if (id == R.id.rate) {
            startActivity(new Intent(getApplicationContext(), ExFilePickerActivity.class));
            return true;
        }
        // If 'Rate' was not the selected item, delegate to the original handler.
        return onOptionsItemSelected(item);
    }

}
