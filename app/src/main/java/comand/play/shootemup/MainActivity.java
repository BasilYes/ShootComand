package comand.play.shootemup;

import android.app.Activity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import comand.play.shootemup.controller.GameView;
import comand.play.shootemup.view.MenuFragment;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new GameController(this));
        setContentView(R.layout.activity_main);
        MenuFragment menuFragment = new MenuFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.hud_frame_layout, menuFragment)
                .commit();
        RelativeLayout relativeLayout = findViewById(R.id.relative_layout);

        //GameController gameController = new GameController();
        GameView gameView = new GameView(this);

        relativeLayout.addView(gameView);
    }
}