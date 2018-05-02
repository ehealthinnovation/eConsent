import React from 'react'
import Signature from './SignaturePad'
import HTTPService from '../../Services/HTTPService'
import Loader from '../../components/Loading'
import FHIREndpoints from '../../Services/FHIREndpoints'
import LearnMore from '../../components/LearnMore'
import LearnMoreList from '../../components/LearnMoreList'
import Img from 'react-image'

class ConsentTemplate extends React.Component {
  state = {
    questions: [],
    showSignaturePad: false,
    base64String: '',
    loading: true,
  }

  async componentDidMount() {
    const { match } = this.props
    const consentFormId = match.params.id
    const response = await HTTPService.get(FHIREndpoints.QRs(consentFormId))
    this.setState({
      questions: response.item,
      loading: false,
    })
  }

  onShowSignatureClick = () => {
    this.setState({
      showSignaturePad: !this.state.showSignaturePad
    })
  }

  saveSignature = (base64PngEncodedString) => {
    // Save signature encoded string
    this.setState({
      showSignaturePad: false,
      base64String: base64PngEncodedString,
    })
  }

  traverseQuestions = (question, arg2) => {
    if (
      question.type === 'text'
    ) {
      return <div className="box" key={question.linkId}>
        <h2>{question.text}</h2>
        <p><b>{question.answer[0].valueString}</b></p>
      </div>
    }

    if(
      question.item &&
      question.item[0].text === 'Summary' &&
      question.item[0].answer[0].valueCoding &&
      question.item[1].text === 'Learn more'
    ) {
      return <LearnMoreList
        title={question.text}
        list={question.item[0].answer}
        learnMore={question.item[1].answer[0].valueString}
      />
    }

    if (
      // note: This was not present inside nest question... threw off check/
      //question.type === 'long-text' &&
      question.item &&
      question.item[0].text === 'Summary' &&
      question.item[1].text === 'Learn more'
    ) {
      return <LearnMore
        key={question.linkId}
        title={question.text}
        summary={question.item[0].answer[0].valueString}
        learnMore={question.item[1].answer[0].valueString}
      />
    }

    if (question.answer && question.answer[0].valueAttachment) {
      const textDescription = question.text
      const imageData = question.answer[0].valueAttachment
      // TODO: Include the image type in the post to server.
      const imageString = "data:" + imageData.contentType + ';base64,' + imageData.data
      return <div className="box">
        <p>{textDescription}</p>
        <Img src={imageString} />
      </div>
    }

    if (
      question.item &&
      question.item[0].item
    ) {
      return <div className="box">
        <h2>{question.text}</h2>
          {question.item.map(subQuestion => {
            return this.traverseQuestions(subQuestion, 'subQuestion')
          })}
      </div>
    }

    if (
      !question.item
    ) {
      return <div className="box" key={question.linkId}>
        <h2>{question.text}</h2>
        <p><b>{question.answer[0].valueString}</b></p>
      </div>
    }

    return null
  }

  render() {
    if (this.state.loading) {
      return <Loader />
    }
    return <div className="consent-content">

      <h1>eConsent Signoff</h1>
      {this.state.questions.map(this.traverseQuestions)}
      <div style={{ marginBottom: '16px'}}>
        I am a parent or guardian
        <input type="checkbox" />
      </div>
      <button style={{ marginBottom: '16px'}} className={`button ${this.state.base64String ? '' : 'is-large is-primary'}`} onClick={this.onShowSignatureClick}>{this.state.base64String ? 'Redo Signature' : 'Give Consent'}</button>
      {this.state.showSignaturePad ?
        <Signature saveSignature={this.saveSignature} closePad={this.onShowSignatureClick} /> : null
      }
      {this.state.base64String ?
        <div className="box" style={{
          width: '70%',
          margin: 'auto',
          marginBottom: '16px'
        }}>
          <img style={{ width: '80%'}} alt="I am a signature" src={this.state.base64String} />
        </div> : null
      }
      {
        this.state.base64String ?
        <button className={`button ${this.state.base64String ? 'is-large is-primary' : ''}`} style={{ marginBottom: '16px', zIndex: 1}}>Submit Consent</button> : null
      }
    </div>
  }

}

export default ConsentTemplate
