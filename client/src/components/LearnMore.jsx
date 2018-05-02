import React from 'react'

class LearnMore extends React.Component {
  state = {
    showMore: false,
  }

  toggle = () => {
    this.setState({
      showMore: !this.state.showMore
    })
  }

  render () {
    const { title, summary, learnMore } = this.props
    return (
      <div className="box">
        <p><b>{title}</b></p>
        { !this.state.showMore && <p>{summary}</p> }
        { !this.state.showMore && <button onClick={this.toggle} className="button">Show more...</button>}
        { this.state.showMore && <p>{learnMore}</p>}
        { this.state.showMore && <button onClick={this.toggle} className="button">Show less...</button>}
      </div>
    )
  }
}

export default LearnMore
