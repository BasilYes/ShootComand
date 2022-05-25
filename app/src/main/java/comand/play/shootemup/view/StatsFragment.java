package comand.play.shootemup.view;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comand.play.shootemup.R;
import comand.play.shootemup.controller.GameController;

/**
 * Класс StatsFragment наследуется от класса Fragment и представляет отображение очков на экране.
 * @author Василий Реуков
 * @version 1.0
 * @see androidx.fragment.app.Fragment
 */
public class StatsFragment extends Fragment {
    /**
     * Пустой конструктор класса StatsFragment.
     */
    public StatsFragment() {
        // Required empty public constructor
    }

    /**
     * Метод onCreate вызывает super-метод класса Fragment.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Метод onCreateView отвечает за привязку полей разметки к очкам игрока.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        GameController.gameView.setOnGame(true);
        GameController.gameView.statsFragment = this;
        GameController.gameView.pointsView = view.findViewById(R.id.point_count_text);
        return view;
    }

    /**
     * Метод gameOver отвчеает за движение очков после смерти игрока.
     * @param points
     */
    public void gameOver(int points){
        EndGameFragment endGameFragment = new EndGameFragment();
        endGameFragment.lastGamePoint = points;
        getFragmentManager().beginTransaction()
                .remove(this)
                .add(R.id.hud_frame_layout, endGameFragment)
                .commit();
    }
}