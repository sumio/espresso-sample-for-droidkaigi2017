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

package jp.jun_nama.droidkaigi2017.qiitabrowsersample.api;

import jp.jun_nama.droidkaigi2017.qiitabrowsersample.model.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface UsersApi {

    @GET("users/{userId}")
    public Observable<User> getUser(@Path("userId") String userId);

    @GET("authenticated_user")
    public Observable<User> getMe();
}
