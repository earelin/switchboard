function fn() {
  var port = karate.properties['server.port'];
  if (!port) {
    port =  8080;
  }

  var protocol = 'http';
  if (karate.properties['server.https'] == 'true') {
    protocol = 'https';
    karate.configure('ssl', true);
  }

  var host = karate.properties['server.host'];
  if (!host) {
    host = '127.0.0.1';
  }

  return { baseUrl: protocol + '://' + host + ':' + port };
}
