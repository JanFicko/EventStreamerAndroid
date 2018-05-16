package xyz.eventstreamer.eventstreamer.data;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import xyz.eventstreamer.eventstreamer.model.BaseResponse;

public class BaseRepository<T extends BaseResponse> {

    protected RetrofitFactory retrofitFactory = new RetrofitFactory();
    private CompositeDisposable compositeDisposable;

    public void setCompositeDisposable(CompositeDisposable compositeDisposable){
        this.compositeDisposable = compositeDisposable;
    }

    protected void getDataFromService(Single<T> single, final BaseDataSource<T> dataSource) {
        compositeDisposable
            .add(single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    dataSource::onSuccess,
                    throwable -> {
                        if(throwable instanceof IOException){
                            dataSource.onNoInternet();
                        } else {
                            dataSource.onError();
                        }
                    }
                )
            );
    }

}
