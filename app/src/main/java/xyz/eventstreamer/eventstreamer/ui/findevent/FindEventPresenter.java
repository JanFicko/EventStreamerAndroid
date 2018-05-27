package xyz.eventstreamer.eventstreamer.ui.findevent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import xyz.eventstreamer.eventstreamer.data.event.EventRepository;
import xyz.eventstreamer.eventstreamer.data.user.UserRepository;
import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.ui.login.LoginContract;
import xyz.eventstreamer.eventstreamer.util.schedulers.BaseSchedulerProvider;

public class FindEventPresenter implements FindEventContract.Presenter {

    private FindEventContract.View view;
    private EventRepository repository;

    private final BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    public FindEventPresenter(FindEventContract.View view,
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
    public void findEventsByQuery(String query) {
        view.setLoadingIndicator(true);

        compositeDisposable.clear();

        Disposable disposable = repository
                .getEventByQuery(query)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        events -> {
                            view.setLoadingIndicator(false);
                            view.showEventsView(events);
                        },
                        throwable -> {
                            view.setLoadingIndicator(false);
                            view.showErrorMessage();
                        });

        compositeDisposable.add(disposable);
    }
}
