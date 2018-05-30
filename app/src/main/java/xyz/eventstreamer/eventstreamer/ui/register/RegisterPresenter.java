package xyz.eventstreamer.eventstreamer.ui.register;

import java.net.ConnectException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import xyz.eventstreamer.eventstreamer.data.user.UserRepository;
import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.util.schedulers.BaseSchedulerProvider;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private UserRepository repository;

    private final BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    public RegisterPresenter(RegisterContract.View view,
                             UserRepository repository,
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
    public void registerUser(User user) {
        view.setLoadingIndicator(true);

        compositeDisposable.clear();

        Disposable disposable = repository
                .register(user)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        registeredUser -> {
                            view.setLoadingIndicator(false);
                            view.onSuccessfulRegister();
                        },
                        throwable -> {
                            view.setLoadingIndicator(false);
                            if(throwable instanceof ConnectException){
                                view.showNoInternet();
                            } else {
                                view.showErrorMessage();
                            }
                        });

        compositeDisposable.add(disposable);
    }
}
