package org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.impl;

import ca.uhn.fhir.jpa.dao.IFhirResourceDao;
import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.DummyData;
import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api.IInitQuestionnaire;
import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api.IInitQuestionnaireResponse;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IIdType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InitQuestionnaireResponseImpl implements IInitQuestionnaireResponse {
    @Autowired
    IFhirResourceDao<QuestionnaireResponse> myQuestionnaireResponseDao;

    @Autowired
    IInitQuestionnaire initQuestionnaire;

    @Override
    public QuestionnaireResponse postQuestionnaireResponse(QuestionnaireResponse theQuestionnaireResponse) {
        myQuestionnaireResponseDao.update(theQuestionnaireResponse);
        return theQuestionnaireResponse;
    }

    @Override
    public QuestionnaireResponse createQuestionnaireResponse(String theName, IIdType theBasedOn) {
        QuestionnaireResponse consentInfo = new QuestionnaireResponse();
        consentInfo.setId(theName);
        consentInfo.setQuestionnaire(new Reference(theBasedOn));
        consentInfo.setStatus(QuestionnaireResponse.QuestionnaireResponseStatus.COMPLETED);
        consentInfo.setAuthored(new Date());

        consentInfo.addItem().setLinkId(InitQuestionnaireImpl.linkIdStart + "title").setText("Title of research project").addAnswer().setValue(new StringType(theName));
        consentInfo.addItem().setLinkId(InitQuestionnaireImpl.linkIdStart + "investigator").setText("Principal Investigator (Study Doctor)").addAnswer().setValue(new StringType("Dr. Derek Shepherd"));
        consentInfo.addItem().setLinkId(InitQuestionnaireImpl.linkIdStart + "contact").setText("Study Coordinator(s)/Research Contact").addAnswer().setValue(new StringType("Cristina Yang: 647-123-4567"));


        QuestionnaireResponse.QuestionnaireResponseItemComponent itemComponentPurpose = consentInfo.addItem().setLinkId(InitQuestionnaireImpl.linkIdStart + "purpose").setText("Purpose of the research");
        itemComponentPurpose.addItem().setLinkId(itemComponentPurpose.getLinkId() + "summary").setText("Summary").addAnswer().setValue(new StringType("The purpose of the study is to collect data about children that suffer from chronic diseases and learning something from this data."));
        itemComponentPurpose.addItem().setLinkId(itemComponentPurpose.getLinkId() + "learnMore").setText("Learn more").addAnswer().setValue(new StringType(DummyData.LOREM_IPSUM));
        QuestionnaireResponse.QuestionnaireResponseItemComponent itemComponentDescription = consentInfo.addItem().setLinkId(InitQuestionnaireImpl.linkIdStart + "description").setText("Description of the research");
        itemComponentDescription.addItem().setLinkId(itemComponentDescription.getLinkId() + "summary").setText("Summary").addAnswer().setValue(new StringType("First of all you will have to download the app. After signing up, you will be asked to fill out questionnaires about how you feel."));
        itemComponentDescription.addItem().setLinkId(itemComponentDescription.getLinkId() + "learnMore").setText("Learn more").addAnswer().setValue(new StringType(DummyData.LOREM_IPSUM));
        QuestionnaireResponse.QuestionnaireResponseItemComponent itemComponentHarms = consentInfo.addItem().setLinkId(InitQuestionnaireImpl.linkIdStart + "harms").setText("Potential harms");
        QuestionnaireResponse.QuestionnaireResponseItemComponent itemComponentHarmsSummary = itemComponentHarms.addItem().setLinkId(itemComponentHarms.getLinkId() + ".summary").setText("Summary");
        itemComponentHarmsSummary.addAnswer().setValue(new Coding("ehealthinnovation.coding.harms", "123", "Upsetting"));
        itemComponentHarmsSummary.addAnswer().setValue(new Coding("ehealthinnovation.coding.harms", "456", "Loss of data"));
        itemComponentHarmsSummary.addAnswer().setValue(new Coding("ehealthinnovation.coding.harms", "789", "Change of mood"));
        itemComponentHarms.addItem().setLinkId(itemComponentHarms.getLinkId() + ".learnMore").setText("Learn more").addAnswer().setValue(new StringType(DummyData.LOREM_IPSUM));

        QuestionnaireResponse.QuestionnaireResponseItemComponent itemComponentBenefits = consentInfo.addItem().setLinkId(InitQuestionnaireImpl.linkIdStart + "benefits").setText("Potential benefits");
        QuestionnaireResponse.QuestionnaireResponseItemComponent itemComponentBenefitsPersonal = itemComponentBenefits.addItem().setLinkId(itemComponentBenefits.getLinkId() + ".personal").setText("Personal benefits");

        QuestionnaireResponse.QuestionnaireResponseItemComponent itemComponentBenefitsPersonalSummary = itemComponentBenefitsPersonal.addItem().setLinkId(itemComponentBenefitsPersonal.getLinkId() + ".summary").setText("Summary");
        itemComponentBenefitsPersonalSummary.addAnswer().setValue(new Coding("ehealthinnovation.coding.benefits", "123", "Education"));
        itemComponentBenefitsPersonal.addItem().setLinkId(itemComponentBenefitsPersonal.getLinkId() + ".learnMore").setText("Learn more").addAnswer().setValue(new StringType(DummyData.LOREM_IPSUM));
        QuestionnaireResponse.QuestionnaireResponseItemComponent itemComponentBenefitsSociety = itemComponentBenefits.addItem().setLinkId(itemComponentBenefits.getLinkId() + ".society").setText("Society benefits");
        QuestionnaireResponse.QuestionnaireResponseItemComponent itemComponentBenefitsSocietySummary = itemComponentBenefitsSociety.addItem().setLinkId(itemComponentBenefitsSociety.getLinkId() + ".summary").setText("Summary");
        itemComponentBenefitsSocietySummary.addAnswer().setValue(new Coding("ehealthinnovation.coding.benefits", "456", "Help Research"));
        itemComponentBenefitsSociety.addItem().setLinkId(itemComponentBenefitsSociety.getLinkId() + ".learnMore").setText("Learn more").addAnswer().setValue(new StringType(DummyData.LOREM_IPSUM));

        QuestionnaireResponse.QuestionnaireResponseItemComponent itemComponentImage = consentInfo.addItem().setLinkId(InitQuestionnaireImpl.linkIdStart + "somethingGraphical").setText("Show an image");
        itemComponentImage.addItem().setLinkId(itemComponentImage.getLinkId() + ".text").setText("Text").addAnswer().setValue(new StringType("This is a description. You might understand better if you have a look on this image:"));
        Attachment attachment = new Attachment();
        attachment.setContentType("image/svg+xml");
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
        attachment.setData(svgByte);
        itemComponentImage.addItem().setLinkId(itemComponentImage.getLinkId() + ".image").setText("Image").addAnswer().setValue(attachment);

        return consentInfo;
    }

    @Override
    public List<QuestionnaireResponse> createQuestionnaireResponses() {
        List<QuestionnaireResponse> responses = new ArrayList<>();
        Questionnaire template = initQuestionnaire.postQuestionnaire();
        responses.add(
                postQuestionnaireResponse(
                        createQuestionnaireResponse(
                                "info1",
                                template.getIdElement()
                        ))
        );

        responses.add(
                postQuestionnaireResponse(
                        createQuestionnaireResponse(
                                "info2",
                                template.getIdElement()
                        ))
        );

        responses.add(
                postQuestionnaireResponse(
                        createQuestionnaireResponse(
                                "info3",
                                template.getIdElement()
                        ))
        );
        return responses;
    }
}
