import React from 'react'
import SignaturePad from 'react-signature-pad'
import '../../components/signatureStyle.css'

const Signature = (props) => <div id="econsent-signature-pad">
  <SignaturePad
    clearButton="true"
    saveSignature={props.saveSignature}
    closePad={props.closePad}
  />
</div>

export default Signature
