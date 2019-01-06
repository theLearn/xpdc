package com.example.hongcheng.data.retrofit;

import com.example.hongcheng.data.retrofit.response.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


/**
 * Created by hongcheng on 17/1/22.
 */
public class RetrofitClient {

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitClient() {

    }

    public <T> Disposable map(Observable<BaseResponse<T>> observable, BaseSubscriber<T> subscriber) {
        Observable o = observable
                .compose(transformer())
                .compose(SchedulerProvider.getInstance().applySchedulers());
        o.subscribe(subscriber);
        return subscriber;
    }

    public <T> ObservableTransformer<T, T> transformer() {

        return new ObservableTransformer() {

            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.onErrorResumeNext(new ResponseErrorFunc<>())
                        .flatMap(new ResponseFunction<>());
            }
        };
    }

    public static class ResponseErrorFunc<T> implements Function<Throwable, ObservableSource<? extends BaseResponse<T>>> {

        @Override
        public Observable<? extends BaseResponse<T>> apply(Throwable throwable) {
            return Observable.error(ExceptionHandler.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(BaseResponse<T> tResponse) {
            int code = tResponse.getStatus();
            String message = tResponse.getDescription();
            if (code == HttpConstants.COMMON_SUCCESS_CODE) {
                return Observable.just(tResponse.getData());
            } else {
                return Observable.error(new ActionException(code, message));
            }
        }
    }
}
