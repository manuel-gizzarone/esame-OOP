<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>README</title>
  <link rel="stylesheet" href="https://stackedit.io/style.css" />
</head>

<body class="stackedit">
  <div class="stackedit__html"><h1 id="progetto-desame-programmazione-ad-oggetti">Progetto d’esame programmazione ad oggetti</h1>
<p>L’applicazione permette principalmente di ottenere e salvare dati di previsione istantanei sulla nuvolosità di una o più città, calcolare statistiche da database e filtrarle per data o nome della città. Inoltre consente anche di verificare la qualità delle previsioni tramite una soglia di errore opportunamente specificata.</p>
<p>L’interazione con l’applicazione una volta avviata viene gestita tramite protocollo http verso un server creato in maniera automatica sulla porta 8080 localmente perciò per utilizzare le sue funzionalità è necessario un browser oppure un software apposito per l’esecuzione di chiamate GET/POST/DELETE etc. come postman.</p>
<p>Le rotte di chiamata verso il server dell’applicazione (localhost:8080) disponibili sono:</p>
<p>-POST  “/nuvoleCitta5giorni”<br>
-GET    “/salvaOgniOra”<br>
-GET    “/statsGiornaliere”<br>
-GET    “/statsSettimanali”<br>
-GET    “/statsMensili”<br>
-GET    “/statsTotali”<br>
-POST “/filtraStatsGiornaliero”<br>
-POST  “/filtraStatsSettimanale”<br>
-POST  “/filtraStatsMensile”<br>
-POST  “/filtraStatsTotale”<br>
-GET “/getDatabase”<br>
-POST “/previsioniSoglia”</p>
<p>La spiegazione dell’utilizzo delle varie rotte con esempi di esecuzione è mostrato in seguito.</p>
<p><strong>DIAGRAMMI UML FINALI</strong></p>
<ul>
<li>USE CASE DIAGRAM<br>
<img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/UML/UmlFinal/ControllerUseCase.png" alt="USECASEdiagramFinal"></li>
</ul>
<hr>
<h2 id="rotta-nuvolecitta5giorni">Rotta “/nuvoleCitta5giorni”</h2>
<p>Il suo fine è quello di poter far visualizzare le previsioni meteo della nuvolosità percentuale di una più città dall’istante in cui si esegue la chiamata fino ai prossimi 5 giorni. I dati di previsione sono intervallati di un tempo di 3 ore l’uno all’altro ed inoltre sono automaticamente salvati su un database nella cartella del progetto denominata “Database_Previsioni.json”.</p>
<p>Questa rotta è di tipo POST. Per funzionare correttamente la rotta ha bisogno di ricevere un body di tipo JSONObject composto da un sola coppia (key : value):</p>
<p><strong>"nomiCitta" : "listaDeiNomiDelleCittàSeparateDallaVirgola"</strong></p>
<p>Il risultato della chiamata sarà un JSONArray i cui singoli elementi di tipo JSONObject contengono le informazioni indicate sopra.</p>
<h3 id="eccezioni"><em>Eccezioni</em>:</h3>
<p>Nel caso la lista dei nomi delle città fosse vuota verrà lanciata un eccezione di tipo “InserimentoException”.<br>
Se invece sono inseriti degli spazi prima o dopo il nome di una città allora verrà lanciata un’eccezione di tipo “GestisciStringaException”.<br>
Se una delle città inserite non esiste viene lanciata un eccezione del tipo “NomeCittaException”.<br>
Se la lettura dell’apiKey per la chiamata alle api di openweather non viene estratta correttamente dal file config.json nel progetto viene lanciata una “ConfigFileException”.<br>
Se ci sono problemi con l’estrazione delle date dei dati di previsione viene lanciata un’eccezione di tipo “DataMeteoException”.<br>
Per problemi di parsing verso/da JSONObject viene lanciata l’eccezione ParseException, per problemi di I/O l’eccezione IOException.</p>
<p>ESEMPIO</p>
</div>
</body>

</html>
