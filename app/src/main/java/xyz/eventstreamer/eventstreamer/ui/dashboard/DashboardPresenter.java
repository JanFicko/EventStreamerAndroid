package xyz.eventstreamer.eventstreamer.ui.dashboard;

import android.support.annotation.NonNull;

import java.net.ConnectException;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import xyz.eventstreamer.eventstreamer.data.event.EventRepository;
import xyz.eventstreamer.eventstreamer.util.schedulers.BaseSchedulerProvider;

public class DashboardPresenter implements DashboardContract.Presenter {

    private DashboardContract.View view;
    private EventRepository repository;

    private final BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    public DashboardPresenter(DashboardContract.View view,
                          EventRepository repository,
                          BaseSchedulerProvider schedulerProvider) {
        this.repository = repository;
        this.view = view;
        this.schedulerProvider = schedulerProvider;

        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getEvents();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getEvents() {
        view.setLoadingIndicator(true);

        compositeDisposable.clear();

        Disposable disposable = repository
                .getEvents()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        events -> {
                            view.setLoadingIndicator(false);
                            view.showEventsView(events);
                        },
                        throwable -> {
                            if(throwable instanceof ConnectException){
                                view.showNoInternet();
                            }
                            view.setLoadingIndicator(false);
                            view.showErrorMessage();
                        });

        compositeDisposable.add(disposable);
    }
}
