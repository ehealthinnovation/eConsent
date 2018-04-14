import React from 'react'

const PatientConsentsTable = ({ patientConsentForms }) => (
  <table className="table is-striped is-bordered is-fullwidth">
    <thead>
      <tr>
        <th>Organization</th>
        <th>Study/Procedure</th>
        <th>Date</th>
        <th>Consent Given</th>
        <th />
      </tr>
    </thead>
    <tbody>
      {patientConsentForms.map(consent => {
        return <tr>
          <td>{consent.organization}</td>
          <td>{consent['procedure/study']}</td>
          <td>{consent.date}</td>
          <td>{consent.consentGiven ? 'Yes' : 'No' }</td>
          <td>Edit</td>
        </tr>
      })}
    </tbody>
  </table>
)


export default PatientConsentsTable
