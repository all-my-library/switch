package leduyhung.me.switchview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import leduyhung.me.aswitch.SwitchView;

public class DemoActivity extends AppCompatActivity {

    SwitchView switchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        switchView = findViewById(R.id.s);
        switchView.init(4);
    }
}
