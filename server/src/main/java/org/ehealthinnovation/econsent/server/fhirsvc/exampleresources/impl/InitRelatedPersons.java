package org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.impl;

import ca.uhn.fhir.jpa.dao.IFhirResourceDao;
import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api.IInitRelatedPersons;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IIdType;
import org.springframework.beans.factory.annotation.Autowired;

public class InitRelatedPersons implements IInitRelatedPersons {
    @Autowired
    IFhirResourceDao<RelatedPerson> myRelatedPersonDao;

    @Override
    public RelatedPerson postRelatedPerson(RelatedPerson thePerson) {
        IIdType id = myRelatedPersonDao.create(thePerson).getId();
        thePerson.setId(id);
        return thePerson;
    }

    @Override
    public RelatedPerson createRelatedPerson(Patient theRelatedToPatient, Coding theRelationship, String theFamilyName) {
        RelatedPerson relatedPerson = new RelatedPerson();
        relatedPerson.setPatient(new Reference(theRelatedToPatient.getId()));
        relatedPerson.setRelationship(new CodeableConcept().addCoding(theRelationship));
        relatedPerson.addName().setFamily(theFamilyName);
        return relatedPerson;
    }
}
