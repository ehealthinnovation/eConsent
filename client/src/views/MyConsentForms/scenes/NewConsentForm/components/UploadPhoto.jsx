import React from 'react'
import Dropzone from 'react-dropzone'
class UploadPhoto extends React.Component  {
  constructor(props) {
    super(props)
    this.state = {
      accepted: [],
      rejected: []
    }
  }

  render() {
    return (
      <section>
        <div className="dropzone">
          <Dropzone
            accept="image/jpeg, image/png"
            onDrop={(accepted, rejected) => {
              const thumbnails = []
              accepted.forEach(file => {
                // http://www.onlywebpro.com/2012/01/24/create-thumbnail-preview-of-images-using-html5-api/
                // https://react-dropzone.js.org/
                const reader = new FileReader();
                reader.onload = () => {
                    thumbnails.push(reader.result)
                    this.setState({ accepted: thumbnails });
                };
                reader.onabort = () => console.log('file reading was aborted');
                reader.onerror = () => console.log('file reading has failed');
                reader.readAsDataURL(file)
              })
            }}
          >
            <p>Files here</p>
          </Dropzone>
        </div>
        <aside>
          <ul>
            {
              this.state.accepted.map((f,i) => <li key={i}><img alt="thumbnail" src={f} width="50"/></li>)
            }
          </ul>
        </aside>
      </section>
    );
  }
}

export default UploadPhoto
