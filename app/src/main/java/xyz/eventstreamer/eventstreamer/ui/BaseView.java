package xyz.eventstreamer.eventstreamer.ui;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

    void showNoInternet();

}
