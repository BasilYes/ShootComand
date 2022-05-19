package comand.play.shootemup.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import android.app.DialogFragment;

import comand.play.shootemup.R;
import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;

public class SettingsDialog extends DialogFragment {

    public static String TAG = "dialog_tag";
    //Java



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] catNamesArray =
                {getString(R.string.sound),
                        getString(R.string.music),
                        getString(R.string.finger)};
        final boolean[] checkedItemsArray = {GameController.playSound,
                GameController.playMusic,
                !GameController.useGiro};

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(),
                R.style.dialog));
        builder.setTitle(getString(R.string.settings))
                .setMultiChoiceItems(catNamesArray, checkedItemsArray,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which, boolean isChecked) {
                                checkedItemsArray[which] = isChecked;
                            }
                        })
                .setPositiveButton(getString(R.string.ready),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                GameController.playSound = checkedItemsArray[0];
                                GameController.playMusic = checkedItemsArray[1];
                                GameController.useGiro = !checkedItemsArray[2];
                                SharedPreferences sharedPreferences =
                                        getActivity().getPreferences(Context.MODE_PRIVATE);

                                if (checkedItemsArray[0])
                                    GameView.gameView.startSound();
                                else
                                    GameView.gameView.stopSound();
                                if (checkedItemsArray[1])
                                    GameView.gameView.startMusic();
                                else
                                    GameView.gameView.stopMusic();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean(getString(R.string.sound_key), checkedItemsArray[0]);
                                editor.putBoolean(getString(R.string.music_key), checkedItemsArray[1]);
                                editor.putBoolean(getString(R.string.giro_key), !checkedItemsArray[2]);
                                editor.apply();
                            }
                        })

                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });

        return builder.create();
    }
};
