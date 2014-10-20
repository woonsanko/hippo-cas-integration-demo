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

import java.io.IOException;
import java.util.ArrayList;

import javax.jcr.SimpleCredentials;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hippoecm.frontend.model.UserCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example SSO filter implementation to integrate with CAS
 */
public class SSOExampleCMSLoginFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(SSOExampleCMSLoginFilter.class);

    private static final String SSO_USER_STATE = SSOUserState.class.getName();

    private String [] prefixExclusions;
    private String [] suffixExclusions;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        prefixExclusions = splitParamValue(filterConfig.getInitParameter("prefixExclusions"), ",");
        suffixExclusions = splitParamValue(filterConfig.getInitParameter("suffixExclusions"), ",");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;

            // If the request path is for static resources such as images, css, etc., don't continue.
            if (isSkippableRequest(req)) {
                chain.doFilter(request, response);
                return;
            }

            // Check if the user alreay has a SSO user state stored in HttpSession before.
            HttpSession session = req.getSession();
            SSOUserState userState = (SSOUserState) session.getAttribute(SSO_USER_STATE);

            // If the stored SSO user state is null or has a different JSESSIONID, then
            // recreate a new SSO user state only when the user was already authenticated by CAS Server.
            if (userState == null || !userState.getSessionId().equals(session.getId())) {
                final String netId = req.getRemoteUser();

                // Only when the user was already authenticated and so had a CAS NetID..
                if (netId != null) {
                    log.info("NetID: {}", netId);
                    // Enter any dummy string which must not be an empty string.
                    SimpleCredentials creds = new SimpleCredentials(netId, "DUMMY".toCharArray());
                    creds.setAttribute(SSOUserState.CAS_NET_ID_ATTR, netId);
                    userState = new SSOUserState(new UserCredentials(creds), session.getId());
                    session.setAttribute(SSO_USER_STATE, userState);
                }
            }

            // If the user has a valid SSO user state, then
            // set a JCR Credentials as request attribute (named by FQCN of UserCredentials class).
            // Then the CMS application will use the JCR credentials passed through this request attribute.
            if (userState != null && userState.getSessionId().equals(session.getId())) {
                req.setAttribute(UserCredentials.class.getName(), userState.getCredentials());
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    /**
     * Determine if the reuqest is for static resources.
     * @param request
     * @return
     */
    private boolean isSkippableRequest(final HttpServletRequest request) {
        String pathInfo = request.getRequestURI();

        if (pathInfo != null) {
            pathInfo = pathInfo.substring(request.getContextPath().length());
        }

        if (prefixExclusions != null) {
            for (String excludePrefix : prefixExclusions) {
                if (pathInfo.startsWith(excludePrefix)) {
                    return true;
                }
            }
        }

        if (suffixExclusions != null) {
            for (String excludeSuffix : suffixExclusions) {
                if (pathInfo.endsWith(excludeSuffix)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Split parameter string value into an array.
     * @param param
     * @param delimiter
     * @return
     */
    private static String [] splitParamValue(String param, String delimiter) {
        if (param == null) {
            return null;
        }

        String [] tokens = param.split(delimiter);
        ArrayList<String> valuesList = new ArrayList<String>();

        for (int i = 0; i < tokens.length; i++) {
            String value = tokens[i].trim();

            if (!value.isEmpty()) {
                valuesList.add(value);
            }
        }

        if (!valuesList.isEmpty()) {
            return valuesList.toArray(new String[valuesList.size()]);
        }

        return null;
    }

}
