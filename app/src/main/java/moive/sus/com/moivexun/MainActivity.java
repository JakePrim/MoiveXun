package moive.sus.com.moivexun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.moive.sus.library.base.core.retofit.BaseSubscriber;
import com.moive.sus.library.base.core.retofit.ExceptionHandle;
import com.moive.sus.library.base.core.retofit.RetrofitClient;

import moive.sus.com.moivexun.api.DApi;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitClient.getInstance(this, DApi.BASE_URL).createBaseApi().post(DApi.NOW_MOVIE_URL, null, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                Log.e("Lyk", e.getMessage());
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                Toast.makeText(MainActivity.this, responseBody.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
