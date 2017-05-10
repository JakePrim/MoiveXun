package moive.sus.com.moivexun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.moive.sus.library.base.core.retofit.ExceptionHandle;
import com.moive.sus.library.base.core.retofit.RetrofitClient;

import moive.sus.com.moivexun.api.DApi;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
