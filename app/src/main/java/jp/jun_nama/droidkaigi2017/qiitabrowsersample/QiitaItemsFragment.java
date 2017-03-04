/*
 * Copyright (C) 2017 TOYAMA Sumio <jun.nama@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package jp.jun_nama.droidkaigi2017.qiitabrowsersample;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import jp.jun_nama.droidkaigi2017.qiitabrowsersample.model.FavEvent;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.viewmodel.FavableQiitaItem;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;
import rx.subscriptions.CompositeSubscription;


/**
 * A simple {@link Fragment} subclass.
 */
public class QiitaItemsFragment extends Fragment {

    private static final String TAG = QiitaItemsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private Subject<List<FavableQiitaItem>, List<FavableQiitaItem>> qiitaItemsSubject;
    private CompositeSubscription subscriptions;
    private QiitaItemsAdapter adapter;
    private Subject<List<FavableQiitaItem>, List<FavableQiitaItem>> qiitaFavsSubject;
    private MainApplication app;
    private Observable<FavEvent> favEventObservable;


    public QiitaItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MainApplication) getContext().getApplicationContext();
        qiitaItemsSubject = app.getQiitaItemsSubject();
        qiitaFavsSubject = app.getQiitaFavsSubject();
        favEventObservable = app.getFavEventSubject().distinctUntilChanged().doOnNext(v -> Log.d(TAG, "favEvent: " + v));
        subscriptions = new CompositeSubscription();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qiita_items, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        adapter = new QiitaItemsAdapter();
        recyclerView.setAdapter(adapter);
        Subscription favEventSubscription = Observable.combineLatest(qiitaItemsSubject, favEventObservable, this::updateQiitaItemsList)
                .subscribeOn(Schedulers.io())
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(qiitaItemsSubject::onNext);
        Subscription qiitaItemsUpdateSubscription = qiitaItemsSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::setQiitaItemList);
        subscriptions.addAll(favEventSubscription, qiitaItemsUpdateSubscription);

        return rootView;
    }

    private List<FavableQiitaItem> updateQiitaItemsList(List<FavableQiitaItem> favableQiitaItems, FavEvent favEvent) {
        ArrayList<FavableQiitaItem> copyOfFavableQiitaItems = new ArrayList<>(favableQiitaItems);
        for (int i = 0; i < copyOfFavableQiitaItems.size(); i++) {
            FavableQiitaItem favableQiitaItem = copyOfFavableQiitaItems.get(i);
            if (favableQiitaItem.qiitaItemid.equals(favEvent.qiitaItemId)) {
                copyOfFavableQiitaItems.set(i, favableQiitaItem.newInstance(favEvent.isFaved));
            }
        }
        return copyOfFavableQiitaItems;
    }

    @Override
    public void onDestroyView() {
        subscriptions.clear();
        super.onDestroyView();
    }
}
