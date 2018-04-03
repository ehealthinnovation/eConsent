package org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.impl;

import ca.uhn.fhir.jpa.dao.IFhirResourceDao;
import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InitProvenance implements IInitProvenance {
    @Autowired
    IFhirResourceDao<Provenance> myProvenanceDao;

    @Autowired
    IInitConsent myConsentInit;

    @Override
    public Provenance postProvenance(Provenance theProvenance) {
        IIdType id = myProvenanceDao.create(theProvenance).getId();
        theProvenance.setId(id);
        return theProvenance;
    }

    @Override
    public Provenance createProvenance(Consent theConsent) {
        String svgXml = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<svg width=\"400\" height=\"110\">\n" +
                "  <rect width=\"300\" height=\"100\" style=\"fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0)\" />\n" +
                "  Sorry, your browser does not support inline SVG.  \n" +
                "</svg>\n" +
                " \n" +
                "</body>\n" +
                "</html>";
        byte[] svgByte = svgXml.getBytes();

        Provenance provenance = new Provenance();
        provenance.setRecorded(new Date());
        String reference = theConsent.getConsentingPartyFirstRep().getReference();
        provenance.addAgent().setWho(new Reference(reference));
        provenance.addSignature().setWho(new Reference(reference));
        provenance.addSignature().setWhen(new Date()).setContentType("image/svg+xml").setBlob(svgByte);
        return provenance;

    }
}
