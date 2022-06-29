// Copyright © 2021 Amazon Web Services
// Copyright 2017-2019, Schlumberger
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.opengroup.osdu.dataset.util;

import javax.inject.Inject;

import org.opengroup.osdu.core.common.entitlements.IEntitlementsAndCacheService;
import org.opengroup.osdu.core.common.model.http.DpsHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component("authorizationFilter")
@RequestScope
public class AuthorizationFilter {
    @Inject
    private IEntitlementsAndCacheService entitlementsAndCacheService;

    @Inject
    private DpsHeaders headers;

    public boolean hasRole(String... requiredRoles) {
        String user = entitlementsAndCacheService.authorize(headers, requiredRoles);
        headers.put(DpsHeaders.USER_EMAIL, user);
        return true;
    }
}
