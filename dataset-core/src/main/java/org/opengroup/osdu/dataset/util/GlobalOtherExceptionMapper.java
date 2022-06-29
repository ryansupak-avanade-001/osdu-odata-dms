// Copyright © 2021 Amazon Web Services
// Copyright 2017-2020, Schlumberger
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

import org.opengroup.osdu.core.common.model.http.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalOtherExceptionMapper {

    private GlobalExceptionMapper mapper;

    public GlobalOtherExceptionMapper(GlobalExceptionMapper mapper) {
        this.mapper = mapper;
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGeneralException(Exception e) {
        return mapper.getErrorResponse(
                new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error.",
                        "An unknown error has occurred.", e));
    }

}