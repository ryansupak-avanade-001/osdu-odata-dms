// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

package org.opengroup.osdu.dataset.provider.aws.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WhoamiController {

    @RequestMapping(value = "/whoami")
    @ResponseBody
    public String whoami() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String userName = auth.getName();
        String roles = String.valueOf(auth.getAuthorities());
        String details = String.valueOf(auth.getPrincipal());

        return "user: " + userName + "<BR>" +
                   "roles: " + roles + "<BR>" +
                   "details: " + details + "<BR>";
    }
}
