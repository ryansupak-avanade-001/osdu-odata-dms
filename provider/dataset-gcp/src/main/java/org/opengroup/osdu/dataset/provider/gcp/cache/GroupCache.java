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

package org.opengroup.osdu.dataset.provider.gcp.cache;

import org.opengroup.osdu.core.common.cache.RedisCache;
import org.opengroup.osdu.core.common.model.entitlements.Groups;
import org.opengroup.osdu.dataset.provider.gcp.config.GcpConfigProperties;
import org.springframework.stereotype.Component;

@Component
public class GroupCache extends RedisCache<String, Groups> {

	public GroupCache(GcpConfigProperties propertiesConfig) {
		super(propertiesConfig.getRedisGroupHost(), propertiesConfig.getRedisGroupPort(), 30, String.class,
			Groups.class);
	}

}
