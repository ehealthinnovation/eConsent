import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { fetchQuestionnaireIfNeeded } from '../../../../actions'
import { questionnaireIds } from '../../../../Services/FHIREndpoints'
import Loading from '../../../../components/Loading'
import DatePicker from '../../../../components/DatePicker'
import LearnMoreChoices from './components/LearnMoreChoices'
import UploadPhoto from './components/UploadPhoto'

// const format = 'YYYY-MM-DD'

const propTypes = {
  questionnaire: PropTypes.shape({
    item: PropTypes.array
  }),
  dispatch: PropTypes.func,
  questionnaireValid: PropTypes.bool,
  isFetchingQuestionnare: PropTypes.bool,
}

const LearnMoreText = props => {
  const { item, fontSize, indent } = props
  return (
    <div className="box">
      <div className="field">
        <div className="label" style={{ fontSize, position: 'relative', left: `${indent}px` }}>
          {item[0].text}
        </div>
        <div className="control" style={{ fontSize, position: 'relative', left: `${indent}px` }}>
          <input className="input" type="text"/>
        </div>
      </div>
      <div className="field">
        <div className="label" style={{ fontSize, position: 'relative', left: `${indent}px` }}>
          {props.item[1].text}
        </div>
        <textarea className="textarea" placeholder="" style={{ fontSize, position: 'relative', left: `${indent}px` }}></textarea>
      </div>
    </div>
  )
}

const FormItems = (question, fontSize, indent) => {
  const questionProps = {
    className: "label",
    style: {
      position: 'relative',
      fontSize: `${fontSize}px`,
      left: `${indent}px`
    }
  }

  const fieldProps = {
    className: 'field'
  }
  if (question.type === "date") {
    return (
      <div>
        <p {...questionProps}>{question.text}</p>
        <DatePicker />
      </div>
    )
  }

  if (question.type === 'string') {
    return <div {...fieldProps}><p {...questionProps}>{question.text}</p><input className="input" type="text"/></div>
  }

  if (
    question.type === 'group' &&
    question.item[0].text === "Text" &&
    question.item[0].type === "string" &&
    question.item[1].text === "Image" &&
    question.item[1].type === "attachment"
  ) {
    return <div className="box">
      <p {...questionProps}>{question.text}</p>
      <div className="field">
        <div className="label">
          {question.item[0].text}
        </div>
        <textarea className="textarea" placeholder=""></textarea>
      </div>
      <UploadPhoto />
    </div>
  }

  if (
    question.type === 'group' &&
    question.item[0].text === "Summary" &&
    question.item[0].type === "string" &&
    question.item[1].text === "Learn more" &&
    question.item[1].type === "string"
  ) {
    return <div {...fieldProps}>
      <p {...questionProps}>{question.text}</p>
      <LearnMoreText {...question} />
      </div>
  }

  if (
    question.type === 'group' &&
    question.item[0].type === 'choice' &&
    question.item[0].text === 'Summary' &&
    question.item[1].text === "Learn more" &&
    question.item[1].type === "string"
  ) {
    console.log(question)
    return <div {...fieldProps}>
      <p {...questionProps}>{question.text}</p>
      <LearnMoreChoices reference={question.item[0].options.reference} />
    </div>
  }

  if (question.item) {

    return <div {...fieldProps}>
    <p {...questionProps}>{question.text}</p>
      {question.item.map(question => FormItems(question, fontSize - 2, indent + 8))}
    </div>
  }

  return <div {...fieldProps}>I dont know how to render{question.linkId}</div>
}

class NewConsentForm extends React.Component {
  componentDidMount() {
    this.props.dispatch(fetchQuestionnaireIfNeeded(questionnaireIds.sickKids))
  }

  render() {
    const { isFetchingQuestionnaire, questionnaire, questionnaireValid } = this.props
    return (
      <div className="box">
        {!questionnaireValid && <Loading />}
        {(isFetchingQuestionnaire && !questionnaire.item) && <Loading />}
        {!isFetchingQuestionnaire && !questionnaire.item && <h1>No questionnaire.</h1>}

        <h1>{!isFetchingQuestionnaire && questionnaire.name}</h1>
        {questionnaire.item.length > 0 && questionnaire.item.map(question => FormItems(question, 16, 0))}

        {questionnaire.item.length > 0 && !isFetchingQuestionnaire &&
          <button className="button">Submit eConsent Form</button>
        }

      </div>
    )
  }
}

const mapStateToProps = (state) => {
  const { questionnaire, questionnaireValid, isFetchingQuestionnaire } = state
  return {
    questionnaire,
    questionnaireValid,
    isFetchingQuestionnaire,
  }
}

NewConsentForm.propTypes = propTypes

export default connect(mapStateToProps)(NewConsentForm)
