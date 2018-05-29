package xyz.eventstreamer.eventstreamer.ui.dialog;

public interface OnDateTimeListener {

    void onDateSet(int year, int month, int day);
    void onTimeSet(int hourOfDay, int minute);

}
