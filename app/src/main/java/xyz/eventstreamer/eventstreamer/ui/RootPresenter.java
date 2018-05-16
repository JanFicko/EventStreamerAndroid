package xyz.eventstreamer.eventstreamer.ui;

import io.reactivex.disposables.CompositeDisposable;

public abstract class RootPresenter<V extends BaseView> {

    private V view;
    public CompositeDisposable compositeDisposable;

    public RootPresenter(V view){
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    public void onNoInternet() {
        view.showNoInternet();
    }
}
