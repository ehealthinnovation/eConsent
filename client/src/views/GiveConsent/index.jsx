import React from 'react'
import Dropdown from '../../components/Downdown'
import Notification from '../../components/Notification'
// TODO: Use real patient names and econsent forms.
// mocks
const patients = ['Patient1','Patient2','Patient3','Patient4']
const econsents = ['eConsent1','eConsent2','eConsent3','eConsent4']
class GiveConsent extends React.Component {
  state = {
    showPatients: false,
    showNotification: false,
    notificationMessage: '',
    selectedPatient: '',
    selectedEConsent: '',
  }

  onClick =() => {
    this.setState({ showPatients: !this.state.showPatients })
  }

  onPatientSelect = (value) => {
    this.setState({ selectedPatient: value })
  }

  onEConsentSelect = (value) => {
    this.setState({ selectedEConsent: value })
  }

  onIssue = () => {
    console.log(`Sent invitation to ${this.state.selectedPatient} for ${this.state.selectedEConsent}.`)
    this.setState({
      showNotification: true,
      notificationMessage: `Sent invitation to ${this.state.selectedPatient} for ${this.state.selectedEConsent}.`
    })
  }

  onNotificationClose = () => {
    this.setState({ showNotification: false })
  }

  render() {
    return <div>
      <Dropdown
        placeholder="Select a patient..."
        dropdownOptions={patients}
        onSelect={this.onPatientSelect}
      />
      <Dropdown
        placeholder="Select a eConsent..."
        dropdownOptions={econsents}
        onSelect={this.onEConsentSelect}
      />
      <div>
        <button onClick={this.onIssue} className="button">Issue Consent</button>
      </div>
      <Notification onClose={this.onNotificationClose} show={this.state.showNotification} message={this.state.notificationMessage}/>
    </div>
  }
}

export default GiveConsent
