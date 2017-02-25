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

import jp.jun_nama.droidkaigi2017.qiitabrowsersample.model.User;

public class MyProfile {
    public final String profileImageUrl;
    public final String id;
    public final String name;

    public MyProfile(String profileImageUrl, String id, String name) {
        this.profileImageUrl = profileImageUrl;
        this.id = id;
        this.name = name;
    }

    public static MyProfile from(User user) {
        return new MyProfile(user.profileImageUrl, user.id, user.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyProfile myProfile = (MyProfile) o;

        if (profileImageUrl != null ? !profileImageUrl.equals(myProfile.profileImageUrl) : myProfile.profileImageUrl != null)
            return false;
        if (id != null ? !id.equals(myProfile.id) : myProfile.id != null) return false;
        return name != null ? name.equals(myProfile.name) : myProfile.name == null;

    }

    @Override
    public int hashCode() {
        int result = profileImageUrl != null ? profileImageUrl.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MyProfile{" +
                "profileImageUrl='" + profileImageUrl + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
