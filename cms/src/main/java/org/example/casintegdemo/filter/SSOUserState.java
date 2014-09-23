/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.example.casintegdemo.filter;

import java.io.Serializable;

import org.hippoecm.frontend.model.UserCredentials;

/**
 * SSO User State object which contains a pair of JSESSIONID and <code>UserCredentials</code>.
 */
public class SSOUserState implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UserCredentials credentials;
    private final String sessionId;

    public SSOUserState(final UserCredentials credentials, final String sessionId) {
        this.credentials = credentials;
        this.sessionId = sessionId;
    }

    public UserCredentials getCredentials() {
        return credentials;
    }

    public String getSessionId() {
        return sessionId;
    }

}
