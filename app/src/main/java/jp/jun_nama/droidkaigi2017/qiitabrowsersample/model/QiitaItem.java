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

import java.util.Date;

/**
 * Created by sumio on 2017/03/01.
 */

public class QiitaItem {
    public final Date createdAt;
    public final Date updatedAt;
    public final String id;
    public final String title;
    public final String url;
    public final User user;

    public QiitaItem(Date createdAt, Date updatedAt, String id, String title, String url, User user) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

        if (createdAt != null ? !createdAt.equals(qiitaItem.createdAt) : qiitaItem.createdAt != null)
            return false;
        if (updatedAt != null ? !updatedAt.equals(qiitaItem.updatedAt) : qiitaItem.updatedAt != null)
            return false;
        if (id != null ? !id.equals(qiitaItem.id) : qiitaItem.id != null) return false;
        if (title != null ? !title.equals(qiitaItem.title) : qiitaItem.title != null) return false;
        if (url != null ? !url.equals(qiitaItem.url) : qiitaItem.url != null) return false;
        return user != null ? user.equals(qiitaItem.user) : qiitaItem.user == null;

    }

    @Override
    public int hashCode() {
        int result = createdAt != null ? createdAt.hashCode() : 0;
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QiitaItem{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", user=" + user +
                '}';
    }
}
