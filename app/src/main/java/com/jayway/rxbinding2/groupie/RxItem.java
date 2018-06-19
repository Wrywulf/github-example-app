package com.jayway.rxbinding2.groupie;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import io.reactivex.Observable;

import static com.jayway.rxbinding2.internal.Preconditions.checkNotNull;


/**
 * Created by ubh on 02/01/2018.
 */

public class RxItem {

    /**
     * Create an observable which emits on {@link GroupAdapter} click events.
     * <p>
     * <em>Warning:</em> The created observable keeps a strong reference to {@link GroupAdapter}. Unsubscribe
     * to free this reference.
     * <p>
     * <em>Warning:</em> The created observable uses {@link GroupAdapter#setOnItemClickListener} to observe
     * clicks. Only one observable can be used for a view at a time.
     */
    @CheckResult
    @NonNull
    public static Observable<Item> clicks(@NonNull GroupAdapter adapter) {
        checkNotNull(adapter, "view == null");
        return new ItemClickObservable(adapter);
    }
}
