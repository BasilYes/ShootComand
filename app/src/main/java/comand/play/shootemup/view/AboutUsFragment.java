package comand.play.shootemup.view;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comand.play.shootemup.R;
import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;

public class AboutUsFragment extends Fragment {

    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        Fragment fragment = this;
        GameView.gameView.setAboutUs(true);
        view.findViewById(R.id.menu_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameController.gameController.aboutUsOver();
                MenuFragment menuFragment = new MenuFragment();
                getFragmentManager().beginTransaction()
                        .remove(fragment)
                        .add(R.id.hud_frame_layout, menuFragment)
                        .commit();
            }
        });
        return view;
    }
}