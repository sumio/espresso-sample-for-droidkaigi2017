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

package jp.jun_nama.droidkaigi2017.qiitabrowsersample.model;

import android.support.annotation.NonNull;

public class QiitaItem {
    @NonNull
    public final String id;
    @NonNull
    public final String title;
    @NonNull
    public final String url;
    @NonNull
    public final User user;

    public QiitaItem(@NonNull String id, @NonNull String title, @NonNull String url, @NonNull User user) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QiitaItem qiitaItem = (QiitaItem) o;

        if (!id.equals(qiitaItem.id)) return false;
        if (!title.equals(qiitaItem.title)) return false;
        if (!url.equals(qiitaItem.url)) return false;
        if (!user.equals(qiitaItem.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "QiitaItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", user=" + user +
                '}';
    }
}
