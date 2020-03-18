function fn() {
  var port = karate.properties['server.port'];
  if (!port) {
    port =  9000;
  }

  var protocol = 'http';
  if (karate.properties['server.https'] == 'true') {
    protocol = 'https';
    karate.configure('ssl', true);
  }

  var host = karate.properties['server.host'];
  if (!host) {
    host = 'localhost';
  }

  return { baseUrl: protocol + '://' + host + ':' + port };
}
