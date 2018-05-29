package xyz.eventstreamer.eventstreamer.ui.dialog;

import java.util.List;

import xyz.eventstreamer.eventstreamer.model.Category;

public interface OnCategoryListener {

    void onCategoriesPicked(List<String> categoryList);

}
