#  Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

# https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html

FROM amazoncorretto:8

ARG JAR_FILE=provider/dataset-aws/target/*spring-boot.jar
WORKDIR /

#Default to using self signed generated TLS cert
ENV USE_SELF_SIGNED_SSL_CERT true

WORKDIR /
COPY ${JAR_FILE} app.jar
COPY /provider/dataset-aws/build-aws/ssl.sh /ssl.sh
COPY /provider/dataset-aws/build-aws/entrypoint.sh /entrypoint.sh
EXPOSE 8080

ENTRYPOINT ["/bin/sh", "-c", ". /entrypoint.sh"]
