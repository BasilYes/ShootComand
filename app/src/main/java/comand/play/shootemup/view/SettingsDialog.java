package comand.play.shootemup.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import android.app.DialogFragment;

import comand.play.shootemup.R;

public class SettingsDialog extends DialogFragment {

    public static String TAG = "dialog_tag";
    //Java



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] catNamesArray = {"Звуки", "Музыка", "Управление пальцем"};
        final boolean[] checkedItemsArray = {false, true, false};

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.dialog));
        builder.setTitle("Настройки")
                .setMultiChoiceItems(catNamesArray, checkedItemsArray,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which, boolean isChecked) {
                                checkedItemsArray[which] = isChecked;
                            }
                        })
                .setPositiveButton("Готово",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
//                                StringBuilder state = new StringBuilder();
//                                for (int i = 0; i < catNamesArray.length; i++) {
//                                    state.append(catNamesArray[i]);
//                                    if (checkedItemsArray[i])
//                                        state.append(" выбран\n");
//                                    else
//                                        state.append(" не выбран\n");
//                                }
//                                Toast.makeText(getActivity(),
//                                        state.toString(), Toast.LENGTH_LONG)
//                                        .show();
                            }
                        })

                .setNegativeButton("Отмена",
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
