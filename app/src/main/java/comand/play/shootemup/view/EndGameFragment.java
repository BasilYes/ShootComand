package comand.play.shootemup.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import comand.play.shootemup.R;

public class EndGameFragment extends Fragment {
    public int lastGamePoint;
    public EndGameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end_game, container, false);
        Fragment fragment = this;
        TextView pointText = (TextView)(view.findViewById(R.id.last_game_point_count_text));
        TextView maxPointText = (TextView)(view.findViewById(R.id.max_game_point_count_text));

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int defaultValue = getResources().getInteger(R.integer.default_max_point_key);
        int maxPoint = sharedPreferences.getInt(getString(R.string.max_point_key), defaultValue);
        Log.d("MAX_POINT", maxPoint+"");
        if (maxPoint < lastGamePoint){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.max_point_key), lastGamePoint);
            editor.apply();
            maxPoint = lastGamePoint;
        }
        pointText.setText("Ваш счет: " + lastGamePoint);
        maxPointText.setText("Ваш лучший счет: " + maxPoint);
        view.findViewById(R.id.play_again_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatsFragment statsFragment = new StatsFragment();
                getFragmentManager().beginTransaction()
                        .remove(fragment)
                        .add(R.id.hud_frame_layout, statsFragment)
                        .commit();
            }
        });
        view.findViewById(R.id.to_menu_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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