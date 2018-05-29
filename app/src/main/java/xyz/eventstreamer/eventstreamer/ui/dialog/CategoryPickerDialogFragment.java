package xyz.eventstreamer.eventstreamer.ui.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import xyz.eventstreamer.eventstreamer.R;
import xyz.eventstreamer.eventstreamer.model.Category;

@SuppressLint("ValidFragment")
public class CategoryPickerDialogFragment extends DialogFragment {

    private OnCategoryListener onCategoryListener;
    private List<String> categoryList;

    @SuppressLint("ValidFragment")
    public CategoryPickerDialogFragment(OnCategoryListener onCategoryListener){
        this.onCategoryListener = onCategoryListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        categoryList = new ArrayList<>();
        String[] categoryArray = getResources().getStringArray(R.array.categories);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.add_event_pick_categories)
                .setMultiChoiceItems(R.array.categories, null,
                        (dialog, which, isChecked) -> {
                            if (isChecked) {

                                categoryList.add(categoryArray[which]);
                            } else if (categoryList.contains(categoryArray[which])) {
                                categoryList.remove(which);
                            }
                        })
                // Set the action buttons
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    onCategoryListener.onCategoriesPicked(categoryList);
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    dismiss();
                });

        return builder.create();
    }

}
