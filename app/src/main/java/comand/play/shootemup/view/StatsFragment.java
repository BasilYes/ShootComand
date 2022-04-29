package comand.play.shootemup.view;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comand.play.shootemup.R;
import comand.play.shootemup.controller.GameController;

public class StatsFragment extends Fragment {

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        GameController.gameView.setOnGame(true);
        GameController.gameView.statsFragment = this;
        GameController.gameView.pointsView = view.findViewById(R.id.point_count_text);
        return view;
    }

    public void gameOver(int points){
        EndGameFragment endGameFragment = new EndGameFragment();
        endGameFragment.lastGamePoint = points;
        getFragmentManager().beginTransaction()
                .remove(this)
                .add(R.id.hud_frame_layout, endGameFragment)
                .commit();
    }
}