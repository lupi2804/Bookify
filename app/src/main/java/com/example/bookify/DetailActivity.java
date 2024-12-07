package com.example.bookify;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.bookify.model.ModelMain;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_DONGENG = "DETAIL_DONGENG";
    private String strJudul, strCerita; // Variabel untuk judul dan cerita
    private ModelMain modelMain;
    private Toolbar toolbar;
    private TextView tvJudul, tvCerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Set transparent status bar untuk efek fullscreen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // Inisialisasi Views
        toolbar = findViewById(R.id.toolbar);
        tvJudul = findViewById(R.id.tvJudul);
        tvCerita = findViewById(R.id.tvCerita);

        // Set toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Ambil data dari Intent
        modelMain = (ModelMain) getIntent().getSerializableExtra(DETAIL_DONGENG);
        if (modelMain != null) {
            strJudul = modelMain.getStrJudul();
            strCerita = modelMain.getStrCerita();

            tvJudul.setText(strJudul);

            // Tampilkan cerita dengan format HTML
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvCerita.setText(Html.fromHtml(strCerita, Html.FROM_HTML_MODE_LEGACY));
            } else {
                tvCerita.setText(Html.fromHtml(strCerita));
            }
        } else {
            tvJudul.setText(getString(R.string.data_not_found)); // Tampilkan pesan jika data null
            tvCerita.setText("");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }
}
