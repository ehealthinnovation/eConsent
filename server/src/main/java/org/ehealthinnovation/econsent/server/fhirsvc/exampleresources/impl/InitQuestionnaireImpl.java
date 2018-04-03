package org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.impl;

import ca.uhn.fhir.jpa.dao.IFhirResourceDao;
import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api.IInitQuestionnaire;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class InitQuestionnaireImpl implements IInitQuestionnaire {
    @Autowired
    IFhirResourceDao<Questionnaire> myQuestionnaireDao;

    @Autowired
    IFhirResourceDao<ValueSet> myValueSetDao;

    public Questionnaire postQuestionnaire() {
        Questionnaire toReturn = createQuestionnaire();
        myQuestionnaireDao.update(createQuestionnaire());
        return toReturn;
    }


    public static final String linkIdStart = "ca.uhn.ehealthinnovation.questionnaire.template1.";

    public Questionnaire createQuestionnaire() {
        Questionnaire template1Template = new Questionnaire();
        template1Template.setId("Questionnaire/template1");
        template1Template.setPublisher("eHealth Innovation");
        template1Template.setName("Template 1 Research Study Consent Template");

        //study title
        template1Template.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Title of research project").setLinkId(linkIdStart + "title");

        //investigator
        template1Template.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Principal Investigator (Study Doctor)").setLinkId(linkIdStart + "investigator");

        //study coordinator(s)/research contact
        template1Template.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Study Coordinator(s)/Research Contact").setLinkId(linkIdStart + "contact");

        //purpose
        Questionnaire.QuestionnaireItemComponent purpose = new Questionnaire.QuestionnaireItemComponent().setRequired(true).setType(Questionnaire.QuestionnaireItemType.GROUP).setText("Purpose of the research").setLinkId(linkIdStart + "purpose");
        purpose.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Summary").setLinkId(purpose.getLinkId() + ".summary");
        purpose.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Learn more").setLinkId(purpose.getLinkId() + ".learnMore");
        template1Template.addItem(purpose);

        //description
        Questionnaire.QuestionnaireItemComponent description = new Questionnaire.QuestionnaireItemComponent().setRequired(true).setType(Questionnaire.QuestionnaireItemType.GROUP).setText("Description of the research").setLinkId(linkIdStart + "description");
        description.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Summary").setLinkId(description.getLinkId() + ".summary");
        description.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Learn more").setLinkId(description.getLinkId() + ".learnMore");
        template1Template.addItem(description);

        //harms
        Questionnaire.QuestionnaireItemComponent harms = new Questionnaire.QuestionnaireItemComponent().setRequired(true).setType(Questionnaire.QuestionnaireItemType.GROUP).setText("Potential harms").setLinkId(linkIdStart + "harms");
        harms.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.CHOICE).setText("Summary").setLinkId(harms.getLinkId() + ".summary").setOptions(new Reference(createHarmsValueSet()));
        harms.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Learn more").setLinkId(harms.getLinkId() + ".learnMore");
        template1Template.addItem(harms);

        //benefits
        Questionnaire.QuestionnaireItemComponent benefits = new Questionnaire.QuestionnaireItemComponent().setRequired(true).setType(Questionnaire.QuestionnaireItemType.GROUP).setText("Potential benefits").setLinkId(linkIdStart + "benefits");
        Questionnaire.QuestionnaireItemComponent personalBenefits = new Questionnaire.QuestionnaireItemComponent().setRequired(true).setType(Questionnaire.QuestionnaireItemType.GROUP).setText("Personal benefits").setLinkId(benefits.getLinkId() + ".personal");
        personalBenefits.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Summary").setLinkId(personalBenefits.getLinkId() + ".summary");
        personalBenefits.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Learn more").setLinkId(personalBenefits.getLinkId() + ".learnMore");

        Questionnaire.QuestionnaireItemComponent societyBenefits = new Questionnaire.QuestionnaireItemComponent().setRequired(true).setType(Questionnaire.QuestionnaireItemType.GROUP).setText("Society benefits").setLinkId(benefits.getLinkId() + ".society");
        societyBenefits.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Summary").setLinkId(societyBenefits.getLinkId() + ".summary");
        societyBenefits.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Learn more").setLinkId(societyBenefits.getLinkId() + ".learnMore");


        benefits.addItem(personalBenefits);
        benefits.addItem(societyBenefits);

        template1Template.addItem(benefits);

        //image
        Questionnaire.QuestionnaireItemComponent image = new Questionnaire.QuestionnaireItemComponent().setRequired(true).setType(Questionnaire.QuestionnaireItemType.GROUP).setText("Show an image").setLinkId(linkIdStart + "somethingGraphical");
        image.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.STRING).setText("Text").setLinkId(image.getLinkId() + ".text");
        image.addItem().setRequired(true).setType(Questionnaire.QuestionnaireItemType.ATTACHMENT).setText("Image").setLinkId(image.getLinkId() + ".image");
        template1Template.addItem(image);

        return template1Template;
    }

    private ValueSet createHarmsValueSet() {
        ValueSet valueSet = new ValueSet();
        valueSet.setId("harms");
        valueSet.setUrl("ca.ehealthinnovation.econsent.valueset.harms");
        valueSet.setVersion("0.1");
        valueSet.setName("valueSetHarms");
        valueSet.setTitle("ValueSet of potential harm coes");
        valueSet.setStatus(Enumerations.PublicationStatus.DRAFT);
        valueSet.setExperimental(true);
        valueSet.setDate(new Date());
        valueSet.setPublisher("Centre for Global eHealth Innovation");
        ValueSet.ValueSetComposeComponent composeComponent = new ValueSet.ValueSetComposeComponent();
        ValueSet.ConceptSetComponent include = new ValueSet.ConceptSetComponent();
        include.setSystem("ca.ehealthinnovation.econsent.harms");
        include.addConcept().setCode("123").setDisplay("Upsetting");
        include.addConcept().setCode("456").setDisplay("Loss of data");
        include.addConcept().setCode("789").setDisplay("Change of mood");
        include.addConcept().setCode("1011").setDisplay("Bleeding");
        include.addConcept().setCode("1213").setDisplay("Damage of surrounding tissue");
        composeComponent.addInclude(include);
        valueSet.setCompose(composeComponent);
        myValueSetDao.update(valueSet);
        return valueSet;
    }
}
