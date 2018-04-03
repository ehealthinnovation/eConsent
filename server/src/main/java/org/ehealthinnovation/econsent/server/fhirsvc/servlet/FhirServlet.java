package org.ehealthinnovation.econsent.server.fhirsvc.servlet;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.jpa.dao.DaoConfig;
import ca.uhn.fhir.jpa.provider.dstu3.JpaConformanceProviderDstu3;
import ca.uhn.fhir.rest.api.EncodingEnum;
import ca.uhn.fhir.rest.server.HardcodedServerAddressStrategy;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import java.util.List;


public class FhirServlet extends RestfulServer {
    private WebApplicationContext myAppCtx;
    private DaoConfig myDaoConfig;

    public FhirServlet() {
    }

    protected void initialize() throws ServletException {
        super.initialize();
        this.myAppCtx = ContextLoaderListener.getCurrentWebApplicationContext();
        this.myDaoConfig = this.myAppCtx.getBean(DaoConfig.class);
        this.setFhirContext(this.myAppCtx.getBean(FhirContext.class));
        List<IResourceProvider> beans = (List) this.myAppCtx.getBean("myResourceProvidersDstu3", List.class);
        this.setResourceProviders(beans);
        JpaConformanceProviderDstu3 confProvider = new JpaConformanceProviderDstu3(this, null, this.myDaoConfig);
        confProvider.setImplementationDescription("eConsent FHIR Server");
        this.setServerConformanceProvider(confProvider);
        this.setDefaultPrettyPrint(true);
        this.setDefaultResponseEncoding(EncodingEnum.JSON);
        this.registerInterceptor(myAppCtx.getBean("myResponseHighlighterInterceptor", ResponseHighlighterInterceptor.class));

        //for conformance statement
        String contextRoot = "http://127.0.0.1:8081/";
        String contextPath = "server/";
        this.setServerAddressStrategy(new HardcodedServerAddressStrategy(contextRoot + contextPath + "fhir"));
    }
}
