# Tech Test: Log Access Analytics(LAA)

### Requisitos

* Java 8+
* Maven 3.3+
* ElasticSearch 7.5.1

### Executar

Maven:
<pre><code>./mvn jetty:run</code></pre>

### Testar

Maven:
<pre><code>./mvn test</code></pre>

### Instalar

### Especificação

GET Endpoints:
<pre><code>/api/laaa/health</code></pre>
<pre><code>/api/laa/metrics/top/</code></pre>
<pre><code>/api/laa/metrics/top/3</code></pre>
<pre><code></code>/api/laa/metrics/less</pre>
<pre><code></code>/api/laa/metrics/period?day=23&week=12&year=2019</pre>
<pre><code>/api/laa/metrics/minute</code></pre>

Exemplo:
<pre><code>curl -X GET http://localhost:8080/api/laaa/health</code></pre>

POST Endpoint:
<pre><code>/api/laar/ingest</code></pre>

Exemplo:
<pre><code>curl -X POST http://localhost:8080/api/laar/ingest \
    -H 'Content-Type: application/json' \
    -d '{
        "url" : "/pets/exotic/cats/10",
        "dateTime" : "1037825323957",
        "uuid" : "5b019db5-b3d0-46d2-9963-437860af707f",
        "regionCode" : "3"
    }'</code></pre>

### Referência

<https://www.eclipse.org/jetty/documentation/9.4.x/jetty-maven-plugin.html>

<https://www.javaguides.net/2018/06/how-to-test-jersey-rest-api-with-junit.html>

<https://www.elastic.co/guide>