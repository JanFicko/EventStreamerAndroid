package xyz.eventstreamer.eventstreamer.ui.dashboard;

import android.support.annotation.NonNull;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import xyz.eventstreamer.eventstreamer.data.event.EventRepository;
import xyz.eventstreamer.eventstreamer.model.Event;
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

    @Override
    public void getLocalEvents() {
        /*view.setLoadingIndicator(true);

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
                            }
                            view.showEventsView(eventList);
                        },
                        throwable -> {
                            if(throwable instanceof ConnectException){
                                view.showNoInternet();
                            }
                            view.setLoadingIndicator(false);
                            view.showErrorMessage();
                        });

        compositeDisposable.add(disposable);*/
    }

    @Override
    public void insertLocalEvents(List<Event> eventList) {
        /*compositeDisposable.clear();

        List<EventEntity> eventEntityList = new ArrayList<>();
        for(Event event : eventList){
            EventEntity eventEntity = new EventEntity(
                    event.getIdDogodek(),
                    event.getNaziv(),
                    event.getOpis(),
                    event.getDatum(),
                    event.getIdUporabnik()
            );
            eventEntityList.add(eventEntity);
        }

        Disposable disposable = repository.addLocalEvent(eventEntityList)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        events -> { },
                        throwable -> { });

        compositeDisposable.add(disposable);*/
    }
}
