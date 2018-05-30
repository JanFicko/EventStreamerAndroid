package xyz.eventstreamer.eventstreamer.ui.dashboard;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import xyz.eventstreamer.eventstreamer.EventStreamer;
import xyz.eventstreamer.eventstreamer.data.AppDatabase;
import xyz.eventstreamer.eventstreamer.data.event.EventRepository;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.model.database.EventEntity;
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
                .timeout(3, TimeUnit.SECONDS)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        events -> {
                            view.setLoadingIndicator(false);
                            view.showEventsView(events);
                        },
                        throwable -> {
                            view.setLoadingIndicator(false);
                            view.showNoInternet();
                        });

        compositeDisposable.add(disposable);
    }

    @Override
    public void getLocalEvents() {
        view.setLoadingIndicator(true);

        compositeDisposable.clear();

        Disposable disposable = repository
                .getLocalEvents()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        events -> {
                            view.setLoadingIndicator(false);
                            List<Event> eventList = new ArrayList<>();
                            for(EventEntity eventEntity : events){
                                Event event = new Event();
                                event.setIdDogodek(eventEntity.get_id());
                                event.setNaziv(eventEntity.getNaziv());
                                event.setOpis(eventEntity.getOpis());
                                event.setDatum(eventEntity.getDatum());
                                event.setIdUporabnik(eventEntity.getId_uporabnik());
                                eventList.add(event);
                            }
                            view.showLocalEventsView(eventList);
                        },
                        throwable -> {
                            view.setLoadingIndicator(false);
                            view.showLocalEventsView(null);
                        });

        compositeDisposable.add(disposable);
    }

}
