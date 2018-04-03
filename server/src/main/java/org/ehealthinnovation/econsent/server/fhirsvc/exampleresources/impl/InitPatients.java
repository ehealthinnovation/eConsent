package org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.impl;

import ca.uhn.fhir.jpa.dao.IFhirResourceDao;
import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api.IInitPatient;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.instance.model.api.IIdType;
import org.springframework.beans.factory.annotation.Autowired;

public class InitPatients implements IInitPatient {
    @Autowired
    IFhirResourceDao<Patient> myPatientDao;

    @Override
    public Patient postPatient(String name) {
        Patient patient = createPatient(name);
        IIdType id = myPatientDao.create(patient).getId();
        patient.setId(id);
        return patient;

    }

    @Override
    public Patient createPatient(String name) {
        Patient patient = new Patient();
        patient.addName().setFamily(name);
        return patient;
    }

}
