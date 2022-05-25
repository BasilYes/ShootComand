package comand.play.shootemup.view;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comand.play.shootemup.R;
import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;

/**
 * Класс AboutUsFragment наследуется от класса Fragment и представляет себе отображение информации
 * в окне "О нас".
 * @author Василий Реуков
 * @version 1.0
 * @see androidx.fragment.app.Fragment
 */
public class AboutUsFragment extends Fragment {
    /**
     * Пустой конструктор класса AboutUsFragment.
     */
    public AboutUsFragment() {
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
     * Метод onCreateView связывает поля разметки с действиями игрока.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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