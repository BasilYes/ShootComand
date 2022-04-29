package comand.play.shootemup.view;

import android.app.FragmentTransaction;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comand.play.shootemup.R;

public class MenuFragment extends Fragment {
    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        Fragment fragment = this;
        view.findViewById(R.id.game_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatsFragment statsFragment = new StatsFragment();
                getFragmentManager().beginTransaction()
                        .remove(fragment)
                        .add(R.id.hud_frame_layout, statsFragment)
                        .commit();
            }
        });
        view.findViewById(R.id.about_us_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutUsFragment aboutUsFragment = new AboutUsFragment();
                getFragmentManager().beginTransaction()
                        .remove(fragment)
                        .add(R.id.hud_frame_layout, aboutUsFragment)
                        .commit();
            }
        });
        view.findViewById(R.id.settings_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsDialog dialogFragment = new SettingsDialog();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialogFragment.show(ft, SettingsDialog.TAG);
            }
        });
        return view;
    }
}