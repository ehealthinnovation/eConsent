import React from 'react'

const Notification = ({ onClose, message, show }) => {
  return (
    <div className={"notification " + (!show && 'is-invisible')}>
      <button onClick={() => onClose()} className="delete"/>
      {message}
    </div>
  )
}

export default Notification
