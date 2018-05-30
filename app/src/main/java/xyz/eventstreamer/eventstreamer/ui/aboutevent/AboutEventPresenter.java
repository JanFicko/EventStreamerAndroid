package xyz.eventstreamer.eventstreamer.ui.aboutevent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import xyz.eventstreamer.eventstreamer.data.post.PostRepository;
import xyz.eventstreamer.eventstreamer.model.Event;
import xyz.eventstreamer.eventstreamer.model.Post;
import xyz.eventstreamer.eventstreamer.util.schedulers.BaseSchedulerProvider;

public class AboutEventPresenter implements AboutEventContract.Presenter {

    private AboutEventContract.View view;
    private PostRepository repository;

    private final BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    public AboutEventPresenter(AboutEventContract.View view,
                               PostRepository repository,
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
    public void getPosts(Event event) {
        view.setLoadingIndicator(true);

        compositeDisposable.clear();

        Disposable disposable = repository
                .getPosts(event.getIdDogodek())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        posts -> {
                            view.setLoadingIndicator(false);
                            view.showPosts(posts);
                        },
                        throwable -> {
                            view.setLoadingIndicator(false);
                            view.showErrorMessage();
                        });

        compositeDisposable.add(disposable);
    }

    @Override
    public void sendPost(Post post) {
        view.setLoadingIndicator(true);

        compositeDisposable.clear();

        Disposable disposable = repository
                .addPost(post)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        posts -> {
                            view.setLoadingIndicator(false);
                            // TODO
                        },
                        throwable -> {
                            view.setLoadingIndicator(false);
                            view.showErrorMessage();
                        });

        compositeDisposable.add(disposable);
    }

}
