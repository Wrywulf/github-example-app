package com.jayway.rxbinding2.groupie;

import android.view.View;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

import static com.jayway.rxbinding2.internal.Preconditions.checkMainThread;


final class ItemClickObservable extends Observable<Item> {
    private final GroupAdapter adapter;

    ItemClickObservable(GroupAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void subscribeActual(Observer<? super Item> observer) {
        if (!checkMainThread(observer)) {
            return;
        }
        Listener listener = new Listener(adapter, observer);
        observer.onSubscribe(listener);
        adapter.setOnItemClickListener(listener);
    }

    static final class Listener extends MainThreadDisposable implements OnItemClickListener {
        private final GroupAdapter adapter;
        private final Observer<? super Item> observer;

        Listener(GroupAdapter adapter, Observer<? super Item> observer) {
            this.adapter = adapter;
            this.observer = observer;
        }

        @Override
        protected void onDispose() {
            adapter.setOnItemClickListener(null);
        }

        @Override
        public void onItemClick(@NonNull Item item, @NonNull View view) {
            if (!isDisposed()) {
                observer.onNext(item);
            }
        }
    }
}
