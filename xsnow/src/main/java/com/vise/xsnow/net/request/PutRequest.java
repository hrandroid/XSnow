package com.vise.xsnow.net.request;

import android.content.Context;

import com.vise.utils.assist.ClassUtil;
import com.vise.xsnow.net.ViseNet;
import com.vise.xsnow.net.callback.ACallback;
import com.vise.xsnow.net.mode.CacheResult;
import com.vise.xsnow.net.subscriber.ApiCallbackSubscriber;

import rx.Observable;
import rx.Subscription;

/**
 * @Description: Put请求
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-04-28 16:06
 */
public class PutRequest extends BaseRequest<PutRequest> {
    @Override
    protected <T> Observable<T> execute(Class<T> clazz) {
        return apiService.put(suffixUrl, params).compose(this.norTransformer(clazz));
    }

    @Override
    protected <T> Observable<CacheResult<T>> cacheExecute(Class<T> clazz) {
        return execute(clazz).compose(ViseNet.getInstance().getApiCache().transformer(cacheMode, clazz));
    }

    @Override
    protected <T> Subscription execute(Context context, ACallback<T> callback) {
        if (isLocalCache) {
            return this.cacheExecute(ClassUtil.getTClass(callback))
                    .subscribe(new ApiCallbackSubscriber(context, callback));
        }
        return this.execute(ClassUtil.getTClass(callback))
                .subscribe(new ApiCallbackSubscriber(context, callback));
    }
}
