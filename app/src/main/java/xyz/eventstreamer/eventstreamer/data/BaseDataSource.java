package xyz.eventstreamer.eventstreamer.data;

import xyz.eventstreamer.eventstreamer.model.BaseResponse;

public interface BaseDataSource<R extends BaseResponse> {

    void onSuccess(R body);
    void onError();
    void onNoInternet();

}
