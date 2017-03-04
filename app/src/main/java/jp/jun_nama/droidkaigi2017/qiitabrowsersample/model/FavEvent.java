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

public class FavEvent {

    public static final FavEvent DUMMY_FAV_EVENT = new FavEvent("", false);

    @NonNull
    public final String qiitaItemId;
    public final boolean isFaved;

    public FavEvent(@NonNull String qiitaItemId, boolean isFaved) {
        this.qiitaItemId = qiitaItemId;
        this.isFaved = isFaved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavEvent favEvent = (FavEvent) o;

        if (isFaved != favEvent.isFaved) return false;
        return qiitaItemId.equals(favEvent.qiitaItemId);

    }

    @Override
    public int hashCode() {
        int result = qiitaItemId.hashCode();
        result = 31 * result + (isFaved ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FavEvent{" +
                "qiitaItemId='" + qiitaItemId + '\'' +
                ", isFaved=" + isFaved +
                '}';
    }
}
