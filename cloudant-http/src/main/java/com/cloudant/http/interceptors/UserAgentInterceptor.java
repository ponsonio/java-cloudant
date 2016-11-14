/*
 * Copyright © 2016 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.cloudant.http.interceptors;

import com.cloudant.http.HttpConnectionInterceptorContext;
import com.cloudant.http.HttpConnectionRequestInterceptor;

/**
 * Created by rhys on 14/11/2016.
 */
public class UserAgentInterceptor implements HttpConnectionRequestInterceptor {


    private final String userAgent;

    /**
     * Creates an UserAgentInterceptor
     * @param libraryName First part of the UA string this should include a version number if
     *                    appropriate eg java-cloudant/2.6.1
     */
    public UserAgentInterceptor(String libraryName){
        this.userAgent =  String.format("%s/%s/%s/%s/%s",
                libraryName,
                System.getProperty("java.version"),
                System.getProperty("java.vendor"),
                System.getProperty("os.name"),
                System.getProperty("os.arch"));
    }



    @Override
    public HttpConnectionInterceptorContext interceptRequest(HttpConnectionInterceptorContext
                                                                          context) {
        context.connection.getConnection().setRequestProperty("User-Agent", userAgent);
        return context;
    }
}
