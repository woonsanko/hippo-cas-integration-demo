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
package org.example.casintegdemo.security;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.api.security.user.UserManager;
import org.hippoecm.repository.security.DelegatingSecurityProvider;
import org.hippoecm.repository.security.RepositorySecurityProvider;
import org.hippoecm.repository.security.user.DelegatingHippoUserManager;
import org.hippoecm.repository.security.user.HippoUserManager;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom <code>org.hippoecm.repository.security.SecurityProvider</code> implementation.
 * <P>
 * Hippo Repository allows to set a custom security provider for various reasons (e.g, SSO) for specific users.
 * If a user is associated with a custom security provider, then Hippo Repository invokes
 * the custom security provider to do authentication and authorization.
 * </P>
 */
public class CASDelegatingSecurityProvider extends DelegatingSecurityProvider {

    private static Logger log = LoggerFactory.getLogger(CASDelegatingSecurityProvider.class);

    private HippoUserManager userManager;

    /**
     * Constructs by creating the default <code>RepositorySecurityProvider</code> to delegate all the other calls
     * except of authentication calls.
     * @throws RepositoryException
     */
    public CASDelegatingSecurityProvider() throws RepositoryException {
        super(new RepositorySecurityProvider());
    }

    /**
     * Returns a custom (delegating) HippoUserManager to authenticate a user by CAS Ticket.
     */
    @Override
    public UserManager getUserManager() throws RepositoryException {
        if (userManager == null) {
            userManager = new DelegatingHippoUserManager((HippoUserManager) super.getUserManager()) {
                @Override
                public boolean authenticate(SimpleCredentials creds) throws RepositoryException {
                    return validateAuthentication(creds);
                }
            };
        }

        return userManager;
    }

    /**
     * Returns a custom (delegating) HippoUserManager to authenticate a user by CAS Ticket.
     */
    @Override
    public UserManager getUserManager(Session session) throws RepositoryException {
        return new DelegatingHippoUserManager((HippoUserManager) super.getUserManager(session)) {
            @Override
            public boolean authenticate(SimpleCredentials creds) throws RepositoryException {
                return validateAuthentication(creds);
            }
        };
    }

    /**
     * Validates CAS SSO Ticket.
     * <P>
     * In this example, simply invokes CAS API (<code>AssertionHolder#getAssertion()</code>) to validate.
     * </P>
     * @param creds
     * @return
     * @throws RepositoryException
     */
    protected boolean validateAuthentication(SimpleCredentials creds) throws RepositoryException {
        log.info("CASDelegatingSecurityProvider validating credentials: {}", creds);
        // Asserting must have been done by the *TicketValidationFilter* and the assertion thread local variable
        // must have been set by AssertionThreadLocalFilter already.
        // So, simply check if you have assertion object in the thread local.
        Assertion assertion = AssertionHolder.getAssertion();
        log.info("Assertion: {}", assertion);
        return assertion != null;
    }

}