/*
 * Copyright 2021 Google LLC
 * Copyright 2021 EPAM Systems, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opengroup.osdu.odatadms.provider.gcp;

import org.opengroup.osdu.odatadms.ODataDMSApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "org.opengroup")
//@ComponentScan(value = {"org.opengroup.osdu"}, //excludeFilters = {
//	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {null,
//		ODataDMSApplication.class})})
public class DatasetApplicationGCP {

	public static void main(String[] args) {
		SpringApplication.run(DatasetApplicationGCP.class, args);
	}
}
