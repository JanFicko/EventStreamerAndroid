package xyz.eventstreamer.eventstreamer.ui.addevent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import xyz.eventstreamer.eventstreamer.data.event.EventRepository;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.util.schedulers.BaseSchedulerProvider;

public class AddEventPresenter implements AddEventContract.Presenter {

    private AddEventContract.View view;
    private EventRepository repository;

    private final BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    public AddEventPresenter(AddEventContract.View view,
                              EventRepository repository,
                              BaseSchedulerProvider schedulerProvider) {
        this.repository = repository;
        this.view = view;
        this.schedulerProvider = schedulerProvider;

        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() { }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void addEvent(Event event) {
        view.setLoadingIndicator(true);

        compositeDisposable.clear();

        Disposable disposable = repository
                .addEvent(event)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        createdEvent -> {
                            view.setLoadingIndicator(false);
                            view.onSuccessfulEventAdded();
                        },
                        throwable -> {
                            view.setLoadingIndicator(false);
                            view.showErrorMessage();
                        });

        compositeDisposable.add(disposable);
    }
}
