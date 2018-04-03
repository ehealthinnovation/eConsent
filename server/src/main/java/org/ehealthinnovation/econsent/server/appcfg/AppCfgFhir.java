package org.ehealthinnovation.econsent.server.appcfg;

import ca.uhn.fhir.jpa.config.BaseJavaConfigDstu3;
import ca.uhn.fhir.jpa.dao.DaoConfig;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring configuration for the FHIR services
 */
@Configuration
@EnableTransactionManagement()
@EnableJpaRepositories(basePackages = "ca.uhn.fhir.jpa.dao.data")
public class AppCfgFhir extends BaseJavaConfigDstu3 {
    @Bean
    public DaoConfig daoConfig() throws Exception {
        DaoConfig retVal = new DaoConfig();
        return retVal;
    }

    //This interceptor detects when a request is coming from a browser, and automatically returns a response
    // with syntax highlighted (coloured) HTML for the response instead of just returning raw XML/JSON.
    //http://hapifhir.io/apidocs/ca/uhn/fhir/rest/server/interceptor/ResponseHighlighterInterceptor.html
    @Bean(name = "myResponseHighlighterInterceptor")
    public ResponseHighlighterInterceptor responseHighlighterInterceptor() {
        ResponseHighlighterInterceptor retVal = new ResponseHighlighterInterceptor();
        return retVal;
    }

    @Bean(name = "myResourceProvidersDstu3")
    public List<IResourceProvider> resourceProviders() throws Exception {
        List<IResourceProvider> retVal = new ArrayList<>();

        retVal.add(rpConsentDstu3());
        retVal.add(rpOrganizationDstu3());
        retVal.add(rpPatientDstu3());
        retVal.add(rpPractitionerDstu3());
        retVal.add(rpQuestionnaireDstu3());
        retVal.add(rpQuestionnaireResponseDstu3());
        retVal.add(rpValueSetDstu3());
        retVal.add(rpSearchParameterDstu3());
        retVal.add(rpRelatedPersonDstu3());
        retVal.add(rpProvenanceDstu3());
        return retVal;
    }
}
