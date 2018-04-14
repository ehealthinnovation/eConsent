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
    // HACK: Fake consent data.
    if (/\/Consent\//.test(endpoint)) {
      return [{
        organization: 'Sick Kids',
        'procedure/study': 'iCanCope',
        date: '2017-09-01',
        consentGiven: true,
      }, {
        organization: 'Sick Kids',
        'procedure/study': 'UCAN',
        date: '2016-12-25',
        consentGiven: true,
      }, {
        organization: 'TGH',
        'procedure/study': 'medly',
        date: '2016-11-03',
        consentGiven: false,
      }, {
        organization: 'TGH',
        'procedure/study': 'bant',
        date: '2016-01-29',
        consentGiven: true,
      }]
    }
    try {
      const response = await fetch(url, myInit)
      return response.json()
    } catch (e) {
      throw new Error(e)
    }
  }
}

export default new HTTP()
