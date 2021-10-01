package id.ac.umn.week03_34529;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private final LinkedList<String> mDaftarKata = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    for (int i = 1; i < 21; i++) {
        mDaftarKata.add("Kata " + i);
    }
}