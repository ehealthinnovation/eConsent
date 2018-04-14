class HTTP {
  get = async (endpoint) => {
    const myInit = {
      method: 'GET',
      headers: new Headers({
        'Content-Type': 'application/json+fhir'
      }),
      // mode: 'cors',
      cache: 'default'
    };

    const url = process.env.REACT_APP_SERVER_BASE_URL+endpoint
    try {
      const response = await fetch(url, myInit)
      return response.json()
    } catch (e) {
      throw new Error(e)
    }
  }
}

export default new HTTP()
