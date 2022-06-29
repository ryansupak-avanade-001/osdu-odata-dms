package org.opengroup.osdu.odatadms.provider.azure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("osdu.odatadms.dms")
@Configuration
@Getter
@Setter
public class ODataDMSConfig
{
    // the URLs for the (typically external) target Storage API itself
    // {single curly braces with numbers} are used to signify tokens
    // example:
    // "https://mock-dsis-api.azurewebsites.net/api/{{2}}/{{3}}/{{4}}/{{5}}?$filter=(native_uid%20eq%20%27{{6}}%27)&$format=json"
    // Use the method:
    // String ODataDmsService.parseODataURL(List<String> tokensFromDatasetRegistryID, String tokenizedURL)
    // to obtain the actual URL

    private String TokenizedURL;

}
