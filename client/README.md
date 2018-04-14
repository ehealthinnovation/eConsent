This project was bootstrapped with [Create React App](https://github.com/facebookincubator/create-react-app) and state is managed with [Redux.js](https://redux.js.org/)

You can find the most recent version of the CRA guide [here](https://github.com/facebookincubator/create-react-app/blob/master/packages/react-scripts/template/README.md).

To see API's in action, import the insomnia file at the project root (./insomnia_2018-04-14.json)

When in development, we are assuming a configuration of:
~~~
BASE_URL: http://localhost:8080/

SERVER_BASENAME: econsent-server
~~~
The default config for server is
~~~
port 8081
contextpath server
~~~
so these will need to be modified.
This can easily be done using the provided patch file.

To apply the patch, follow these stop after navigating to the eConsent repo root on your machine.

~~~
 cd server
 patch ./server/pom.xml < develop.patch
 mvn package war:war
~~~

You should now see a econsent-server.war inside the target folder.

Deploy it to your local tomcat instance to use in development.

Some seed data endpoints of interest:

Sample consent forms:
  http://BASE_URL/client/econsentform/info1
  http://BASE_URL/client/econsentform/info2
  http://BASE_URL/client/econsentform/info3

