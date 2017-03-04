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

package jp.jun_nama.droidkaigi2017.qiitabrowsersample.viewmodel;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import jp.jun_nama.droidkaigi2017.qiitabrowsersample.MainApplication;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.model.FavEvent;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.model.QiitaItem;
import rx.subjects.Subject;

public class FavableQiitaItem {
    private static final String TAG = FavableQiitaItem.class.getSimpleName();
    @NonNull
    public final String qiitaItemid;
    @NonNull
    public final String title;
    @NonNull
    public final String url;
    @NonNull
    public final String profileImageUrl;
    public final boolean isFaved;

    public FavableQiitaItem(@NonNull String qiitaItemid, @NonNull String title, @NonNull String url, @NonNull String profileImageUrl, boolean isFaved) {
        this.qiitaItemid = qiitaItemid;
        this.title = title;
        this.url = url;
        this.profileImageUrl = profileImageUrl;
        this.isFaved = isFaved;
    }

    public FavableQiitaItem(@NonNull QiitaItem qiitaItem) {
        this.qiitaItemid = qiitaItem.id;
        this.title = qiitaItem.title;
        this.url = qiitaItem.url;
        this.profileImageUrl = qiitaItem.user.profileImageUrl;
        this.isFaved = false;
    }

    public FavableQiitaItem newInstance(boolean isFaved) {
        return new FavableQiitaItem(this.qiitaItemid, this.title, this.url, this.profileImageUrl, isFaved);
    }

    public void onFavCheckBoxTapped(View view) {
        if (!(view instanceof CheckBox)) {
            Log.w(TAG, "onFavCheckBoxTapped: view is not CheckBox: " + view);
            return;
        }
        boolean isChecked = ((CheckBox) view).isChecked();
        Subject<FavEvent, FavEvent> favEventSubject = ((MainApplication) view.getContext().getApplicationContext()).getFavEventSubject();
        favEventSubject.onNext(new FavEvent(this.qiitaItemid, isChecked));
    }

    public void launchCustomTabs(Context context) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(this.url));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavableQiitaItem that = (FavableQiitaItem) o;

        if (isFaved != that.isFaved) return false;
        if (!qiitaItemid.equals(that.qiitaItemid)) return false;
        if (!title.equals(that.title)) return false;
        if (!url.equals(that.url)) return false;
        return profileImageUrl.equals(that.profileImageUrl);

    }

    @Override
    public int hashCode() {
        int result = qiitaItemid.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + profileImageUrl.hashCode();
        result = 31 * result + (isFaved ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FavableQiitaItem{" +
                "qiitaItemid='" + qiitaItemid + '\'' +
                ", isFaved=" + isFaved +
                '}';
    }
}
