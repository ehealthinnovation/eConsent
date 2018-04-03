package org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api;

import org.hl7.fhir.dstu3.model.Coding;
import org.hl7.fhir.dstu3.model.Consent;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.instance.model.api.IBaseResource;

import java.util.List;

public interface IInitConsent {
    Consent postConsent(Consent theConsent);

    Consent createConsent(Consent.ConsentState theState, Patient thePatient, IBaseResource theConsentingParty, String theQrId, Coding thePurpose);

    List<Consent> createDefaultConsents();
}
