package xyz.eventstreamer.eventstreamer.ui.login;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import xyz.eventstreamer.eventstreamer.data.user.UserRepository;
import xyz.eventstreamer.eventstreamer.model.User;
import xyz.eventstreamer.eventstreamer.util.schedulers.BaseSchedulerProvider;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private UserRepository repository;

    private final BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    public LoginPresenter(LoginContract.View view,
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
    public void loginUser(User user) {
        view.setLoadingIndicator(true);

        compositeDisposable.clear();

        Disposable disposable = repository
                .login(user)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        loggedInUser -> {
                            view.setLoadingIndicator(false);
                            if(loggedInUser.isSuccess()){
                                view.onSuccessfulLogin(loggedInUser);
                            } else {
                                if (loggedInUser.getStatus().equalsIgnoreCase("User not yet registered")){
                                    view.registerGoogleUser();
                                } else {
                                    view.showErrorMessage();
                                }
                            }
                        },
                        throwable -> {
                            view.setLoadingIndicator(false);
                            view.showErrorMessage();
                        });

        compositeDisposable.add(disposable);
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
                        loggedInUser -> {
                            view.setLoadingIndicator(false);
                            if(loggedInUser.isSuccess()){
                                view.onSuccessfulRegistered();
                            } else {
                                view.showErrorMessage();
                            }
                        },
                        throwable -> {
                            view.setLoadingIndicator(false);
                            view.showErrorMessage();
                        });

        compositeDisposable.add(disposable);
    }
}
