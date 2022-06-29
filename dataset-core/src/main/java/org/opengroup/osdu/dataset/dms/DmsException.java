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

//TODO: Move to os-core-common

package org.opengroup.osdu.dataset.dms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opengroup.osdu.core.common.model.http.DpsException;
import org.opengroup.osdu.core.common.http.HttpResponse;

@Data
@EqualsAndHashCode(callSuper = false)
public class DmsException extends DpsException {

    private static final long serialVersionUID = 9094949225576291097L;

    public DmsException(String message, HttpResponse httpResponse) {
        super(message, httpResponse);
    }
}