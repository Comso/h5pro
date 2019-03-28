
import cn.com.uama.retrofitmanager.AdvancedRetrofitHelper
import cn.com.uama.retrofitmanager.bean.*
import com.cosmo.common.extension.ioThread
import com.cosmo.common.extension.mainThread
import io.reactivex.Observable


fun  Observable<BaseResp>.baseTrans() =
        compose(AdvancedRetrofitHelper.rxObservableTransformer<BaseResp>(null, false))
                .map { it }
                .subscribeOn(ioThread)
                .observeOn(mainThread)

fun <T> Observable<SimpleResp<T>>.trans(): Observable<T> =
        compose(AdvancedRetrofitHelper.rxObservableTransformer<SimpleResp<T>>(null, false))
                .filter { it.data != null }
                .map { it.data }
                .subscribeOn(ioThread)
                .observeOn(mainThread)

fun <T> Observable<SimpleListResp<T>>.listTrans(): Observable<List<T>> =
        compose(AdvancedRetrofitHelper.rxObservableTransformer<SimpleListResp<T>>(null, false))
                .filter { it.data != null }
                .map { it.data }
                .subscribeOn(ioThread)
                .observeOn(mainThread)

fun <T> Observable<SimplePagedListResp<T>>.pageTrans(): Observable<PagedBean<T>> =
        compose(AdvancedRetrofitHelper.rxObservableTransformer<SimplePagedListResp<T>>(null, false))
                .filter { it.data != null }
                .map { it.data }
                .subscribeOn(ioThread)
                .observeOn(mainThread)

