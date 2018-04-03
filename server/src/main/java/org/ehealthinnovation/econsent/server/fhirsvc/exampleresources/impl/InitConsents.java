package org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.impl;

import ca.uhn.fhir.jpa.dao.IFhirResourceDao;
import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class InitConsents implements IInitConsent {
    @Autowired
    IFhirResourceDao<Consent> myConsentDao;

    @Autowired
    IInitQuestionnaireResponse myQRDao;

    @Autowired
    IInitProvenance myProvenanceInit;

    @Autowired
    IInitPatient myPatientInit;

    @Autowired
    IInitRelatedPersons myRelatedPersonsInit;


    @Override
    public Consent postConsent(Consent theConsent) {
        IIdType id = myConsentDao.create(theConsent).getId();
        theConsent.setId(id);
        return theConsent;
    }

    @Override
    public Consent createConsent(Consent.ConsentState theState, Patient thePatient, IBaseResource theConsentingParty, String theQrId, Coding thePurpose) {
        Consent consent = new Consent();
        consent.setStatus(theState);
        consent.addCategory().addCoding(new Coding(
                "http://loinc.org",
                "59284-0",
                "Patient Consent"
        ));
        consent.setPatient(new Reference(thePatient.getIdElement()));
        consent.addConsentingParty(new Reference(theConsentingParty.getIdElement()));
        //TODO: organization
        consent.setSource(new Reference(theQrId));
        consent.addPurpose(thePurpose);
        return consent;
    }

    @Override
    public List<Consent> createDefaultConsents() {
        List<Consent> consents = new ArrayList<>();
        Patient patient = myPatientInit.postPatient("James Bond");
        Coding treatmentPurpose = new Coding("http://hl7.org/fhir/ValueSet/v3-PurposeOfUse", "HRESCH", "healthcare research");
        Coding researchPurpose = new Coding("http://hl7.org/fhir/ValueSet/v3-PurposeOfUse", "TREAT", "treatment");
        Coding emergencyContact = new Coding("http://hl7.org/fhir/v2/0131", "C", "Emergency Contact");

        List<QuestionnaireResponse> responses = myQRDao.createQuestionnaireResponses();
        consents.add(postConsent(createConsent(
                Consent.ConsentState.ACTIVE,
                patient,
                patient,
                responses.get(0).getId(),
                researchPurpose
        )));

        consents.add(postConsent(createConsent(
                Consent.ConsentState.ACTIVE,
                patient,
                myRelatedPersonsInit.postRelatedPerson(
                        myRelatedPersonsInit.createRelatedPerson(
                                patient,
                                emergencyContact,
                                "Wife of James Bond"
                        )
                ),
                responses.get(1).getId(),
                treatmentPurpose
        )));

        consents.add(postConsent(createConsent(
                Consent.ConsentState.REJECTED,
                patient,
                patient,
                responses.get(2).getId(),
                researchPurpose
        )));

        for (Consent consent : consents) {
            myProvenanceInit.postProvenance(myProvenanceInit.createProvenance(consent));
        }

        return consents;
    }
}
