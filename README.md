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
<p>L’applicazione fa utilizzo delle api openweather “forecast5days3hours” e “current weather”.</p>
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
-DELETE “/deleteDatabase”<br>
-GET “/getDatabase”<br>
-POST “/previsioniSoglia”</p>
<p>La spiegazione dell’utilizzo delle varie rotte con esempi di esecuzione è mostrato in seguito.</p>
<br>
<p><em>NOTA:<br>
le statistiche sia filtrate che non fanno tutte riferimento ai dati presenti sul database “Database_Previsioni.json”.<br>
La rotta per la verifica della qualità delle previsioni con soglia di errore fa utilizzo sia del “Database_Previsioni.json” che del “Database_Raccolta.json” . Entrambi i file sono già riempiti con dati da noi raccolti sulle città Rome,Milan,Naples per consentire il diretto uso di tutte le rotte già al primo avvio dell’applicazione.</em><br>
<br></p>
<p><strong>DIAGRAMMI UML FINALI</strong><br>
Di seguito i diagrammi UML definitivi del progetto. I diagrammi prototipo costruiti prima dell’implementazione del codice sono invece visionabili nella cartella del progetto UML\UMLprototype.<br>
<br></p>
<ul>
<li>USE CASE DIAGRAM<br>
<br><br>
<img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/UML/UmlFinal/ControllerUseCase.png" alt="USECASEdiagramFinal"></li>
</ul>
<br>
<ul>
<li>
<p>SEQUENCE DIAGRAM ROTTA “/nuvoleCitta5giorni”<br>
<br><br>
<img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/UML/UmlFinal/SequenceApi5giorni.png" alt="sequence diagram 1"><br>
<br></p>
</li>
<li>
<p>SEQUENCE DIAGRAM ROTTA “/statsGiornaliere”<br>
<br><br>
<img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/UML/UmlFinal/SequenceStatsGiornaliere.png" alt="sequence diagram 2"><br>
<br></p>
</li>
<li>
<p>CLASS DIAGRAM<br>
<br><br>
<img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/UML/UmlFinal/ClassDiagram.png" alt="Class diagram"></p>
</li>
</ul>
<hr>
<h2 id="rotta-nuvolecitta5giorni">Rotta “/nuvoleCitta5giorni”</h2>
<p>Il suo fine è quello di poter far visualizzare le previsioni meteo della nuvolosità percentuale di una più città dall’istante in cui si esegue la chiamata fino ai prossimi 5 giorni. I dati di previsione sono intervallati di un tempo di 3 ore l’uno all’altro ed inoltre sono automaticamente salvati su un database nella cartella del progetto denominata “Database_Previsioni.json”.</p>
<p>Questa rotta è di tipo POST. Per funzionare correttamente la rotta ha bisogno di ricevere un body di tipo JSONObject composto da un sola coppia (key : value):</p>
<p><strong>"nomiCitta" : "listaDeiNomiDelleCittàSeparateDallaVirgola"</strong></p>
<p>Il risultato della chiamata sarà un JSONArray i cui singoli elementi di tipo JSONObject contengono le informazioni indicate sopra.</p>
<h3 id="eccezioni"><em>Eccezioni</em>:</h3>
<ul>
<li>Nel caso la lista dei nomi delle città fosse vuota verrà lanciata un eccezione di tipo “InserimentoException”.</li>
<li>Se invece sono inseriti degli spazi prima o dopo il nome di una città allora verrà lanciata un’eccezione di tipo “GestisciStringaException”.</li>
<li>Se una delle città inserite non esiste viene lanciata un eccezione del tipo “NomeCittaException”.</li>
<li>Se la lettura dell’apiKey per la chiamata alle api di openweather non viene estratta correttamente dal file config.json nel progetto viene lanciata una “ConfigFileException”.</li>
<li>Se ci sono problemi con l’estrazione delle date dei dati di previsione viene lanciata un’eccezione di tipo “DataMeteoException”.</li>
<li>Per problemi di parsing verso/da JSONObject viene lanciata l’eccezione ParseException.</li>
<li>Per problemi di I/O viene lanciata l’eccezione IOException.</li>
</ul>
<p>ESEMPIO</p>
<p><img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/forecast5giorni3Citt%C3%A0.png" alt=""></p>
<p>Il JSONArray  di risposta è:</p>
<p>[<br>
{<br>
“nuvolosita”: 92,<br>
“Data”: “Thu Mar 18 01:00:00 CET 2021”,<br>
“unixData”: 1616025600,<br>
“citta”: “Ripalimosani”<br>
},<br>
{<br>
“nuvolosita”: 96,<br>
“Data”: “Thu Mar 18 04:00:00 CET 2021”,<br>
“unixData”: 1616036400,<br>
“citta”: “Ripalimosani”<br>
},<br>
{<br>
“nuvolosita”: 98,<br>
“Data”: “Thu Mar 18 07:00:00 CET 2021”,<br>
“unixData”: 1616047200,<br>
“citta”: “Ripalimosani”<br>
},<br>
{<br>
“nuvolosita”: 100,<br>
“Data”: “Thu Mar 18 10:00:00 CET 2021”,<br>
“unixData”: 1616058000,<br>
“citta”: “Ripalimosani”<br>
},<br>
…</p>
<p>{<br>
“nuvolosita”: 95,<br>
“Data”: “Thu Mar 18 04:00:00 CET 2021”,<br>
“unixData”: 1616036400,<br>
“citta”: “Mirabello”<br>
},<br>
{<br>
“nuvolosita”: 92,<br>
“Data”: “Thu Mar 18 07:00:00 CET 2021”,<br>
“unixData”: 1616047200,<br>
“citta”: “Mirabello”<br>
},<br>
{<br>
“nuvolosita”: 98,<br>
“Data”: “Thu Mar 18 10:00:00 CET 2021”,<br>
“unixData”: 1616058000,<br>
“citta”: “Mirabello”<br>
},<br>
{<br>
“nuvolosita”: 97,<br>
“Data”: “Thu Mar 18 13:00:00 CET 2021”,<br>
“unixData”: 1616068800,<br>
“citta”: “Mirabello”<br>
},</p>
<p>…</p>
<p>{<br>
“nuvolosita”: 99,<br>
“Data”: “Thu Mar 18 01:00:00 CET 2021”,<br>
“unixData”: 1616025600,<br>
“citta”: “Vinchiaturo”<br>
},<br>
{<br>
“nuvolosita”: 98,<br>
“Data”: “Thu Mar 18 04:00:00 CET 2021”,<br>
“unixData”: 1616036400,<br>
“citta”: “Vinchiaturo”<br>
},<br>
{<br>
“nuvolosita”: 98,<br>
“Data”: “Thu Mar 18 07:00:00 CET 2021”,<br>
“unixData”: 1616047200,<br>
“citta”: “Vinchiaturo”<br>
},<br>
{<br>
“nuvolosita”: 100,<br>
“Data”: “Thu Mar 18 10:00:00 CET 2021”,<br>
“unixData”: 1616058000,<br>
“citta”: “Vinchiaturo”<br>
},</p>
<p>…<br>
]</p>
<hr>
<h2 id="rotta-salvaogniora">Rotta “/salvaOgniOra”</h2>
<p>Il suo fine è quello di salvare ad intervalli regolari di un’ora i dati di nuvolosità di una città a partire dall’istante della chiamata.Nel caso di chiamate multiple i dati di ogni città verrano salvati separatamente in un file apposito nella cartella del progetto col nome “nomeDellaCitta.json”.</p>
<p>Questa rotta è di tipo GET. Per funzionare correttamente la rotta ha bisogno di ricevere un parametro del tipo key : value :</p>
<p><strong>"nomeCitta" : "nomeDellaCittà"</strong></p>
<p>Il risultato della chiamata sarà una stringa col path del database creato per il salvataggio dei dati ogni ora.</p>
<p><img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/salvaOgniOra.png" alt="salvaOgniOra"></p>
<h3 id="eccezioni-1"><em>Eccezioni</em>:</h3>
<ul>
<li>Se il campo del nome della città fosse vuoto verrà lanciata un eccezione di tipo “InserimentoException”.</li>
<li>Se la città inserita non esiste viene lanciata un eccezione del tipo “NomeCittaException”.</li>
<li>Se la lettura dell’apiKey per la chiamata alle api di openweather non viene estratta correttamente dal file config.json nel progetto viene lanciata una “ConfigFileException”.</li>
<li>Per problemi di parsing verso/da JSONObject viene lanciata l’eccezione ParseException.</li>
<li>Per problemi di I/O viene lanciata l’eccezione IOException.</li>
</ul>
<hr>
<h2 id="rotta-statsgiornaliere">Rotta “/statsGiornaliere”</h2>
<p>Il suo fine è quello di calcolare e restituire le statistiche di varianza,media,max,min della nuvolosità percentuale al giorno della data inserita usando i dati sul database “Database_Previsioni.json”.<br>
Se non avete eseguito chiamate alla rotta “nuvole5giorni” il database ha i dati predefiniti sulle città Naples,Milan e Rome relativi alla nuvolosità del mese di marzo dal 8/03/2021 al 17/03/2021.</p>
<p>Questa rotta è di tipo GET. Per funzionare correttamente la rotta ha bisogno di ricevere un parametro del tipo key : value :</p>
<p><strong>"data" : "gg/mm/aaaa"</strong></p>
<p>*NOTA: la data deve essere necessariamente inserita nel formato gg/mm/aaaa.</p>
<p>Il risultato della chiamata è un JSONObject con le relative informazioni sulle statistiche.</p>
<p>ESEMPIO</p>
<p><img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/statsGiorn.png" alt="enter image description here"></p>
<h3 id="eccezioni-2"><em>Eccezioni</em>:</h3>
<ul>
<li>Nel caso il campo data fosse vuoto verrà lanciata un eccezione di tipo “InserimentoException”.</li>
<li>Se la data è stata inserita ma non correttamente viene lanciata una “text ParseException”.</li>
<li>Se ci sono problemi con l’estrazione delle date dei dati da database , per esempio se la data inserita non è presente sul database, viene lanciata un’eccezione di tipo “DataMeteoException”.</li>
<li>Per problemi di parsing verso/da JSONObject viene lanciata l’eccezione ParseException.</li>
<li>Per problemi di I/O viene lanciata l’eccezione IOException.</li>
</ul>
<hr>
<h2 id="rotta-statssettimanali">Rotta “/statsSettimanali”</h2>
<p>Il suo fine è quello di calcolare e restituire le statistiche di varianza,media,max,min della nuvolosità percentuale nella settimana della data inserita usando i dati sul database “Database_Previsioni.json”.<br>
Se non avete eseguito chiamate alla rotta “nuvole5giorni” il database ha i dati predefiniti sulle città Naples,Milan e Rome relativi alla nuvolosità del mese di marzo dal 8/03/2021 al 17/03/2021.</p>
<p>Questa rotta è di tipo GET. Per funzionare correttamente la rotta ha bisogno di ricevere un parametro del tipo key : value :</p>
<p><strong>"data" : "gg/mm/aaaa"</strong></p>
<p>*NOTA: la data deve essere necessariamente inserita nel formato gg/mm/aaaa.</p>
<p>Il risultato della chiamata è un JSONObject con le relative informazioni sulle statistiche.</p>
<p>ESEMPIO<br>
<img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/statsSett.png" alt="enter image description here"></p>
<h3 id="eccezioni-3"><em>Eccezioni</em>:</h3>
<ul>
<li>Nel caso il campo data fosse vuoto verrà lanciata un eccezione di tipo “InserimentoException”.</li>
<li>Se la data è stata inserita ma non correttamente viene lanciata una “text ParseException”.</li>
<li>Se ci sono problemi con l’estrazione delle date dei dati da database , per esempio se la data inserita non è presente sul database, viene lanciata un’eccezione di tipo “DataMeteoException”.</li>
<li>Per problemi di parsing verso/da JSONObject viene lanciata l’eccezione ParseException.</li>
<li>Per problemi di I/O viene lanciata l’eccezione IOException.</li>
</ul>
<hr>
<h2 id="rotta-statsmensili">Rotta “/statsMensili”</h2>
<p>Il suo fine è quello di calcolare e restituire le statistiche di varianza,media,max,min della nuvolosità percentuale nel mese inserito usando i dati sul database “Database_Previsioni.json”.<br>
Se non avete eseguito chiamate alla rotta “nuvole5giorni” il database ha i dati predefiniti sulle città Naples,Milan e Rome relativi alla nuvolosità del mese di marzo dal 8/03/2021 al 17/03/2021.</p>
<p>Questa rotta è di tipo GET. Per funzionare correttamente la rotta ha bisogno di ricevere un parametro del tipo key : value :</p>
<p><strong>"data" : "mm/aaaa"</strong></p>
<p>*NOTA: la data deve essere necessariamente inserita nel formato mm/aaaa.</p>
<p>Il risultato della chiamata è un JSONObject con le relative informazioni sulle statistiche.</p>
<p>ESEMPIO<br>
<img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/statsMens.png" alt="enter image description here"></p>
<h3 id="eccezioni-4"><em>Eccezioni</em>:</h3>
<ul>
<li>Nel caso il campo data fosse vuoto verrà lanciata un eccezione di tipo “InserimentoException”.</li>
<li>Se la data è stata inserita ma non correttamente viene lanciata una “text ParseException”.</li>
<li>Se ci sono problemi con l’estrazione delle date dei dati da database , per esempio se la data inserita non è presente sul database, viene lanciata un’eccezione di tipo “DataMeteoException”.</li>
<li>Per problemi di parsing verso/da JSONObject viene lanciata l’eccezione ParseException.</li>
<li>Per problemi di I/O viene lanciata l’eccezione IOException.</li>
</ul>
<hr>
<h2 id="rotta-statstotali">Rotta “/statsTotali”</h2>
<p>Il suo fine è quello di calcolare e restituire le statistiche di varianza,media,max,min della nuvolosità percentuale usando i dati sul database “Database_Previsioni.json”.<br>
Se non avete eseguito chiamate alla rotta “nuvole5giorni” il database ha i dati predefiniti sulle città Naples,Milan e Rome relativi alla nuvolosità del mese di marzo dal 8/03/2021 al 17/03/2021.</p>
<p>Questa rotta è di tipo GET. Per funzionare correttamente la rotta ha bisogno di ricevere non deve ricevere parametri.</p>
<p>Il risultato della chiamata è un JSONObject con le relative informazioni sulle statistiche.</p>
<p>ESEMPIO<br>
<img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/statsTotali.png" alt="enter image description here"></p>
<h3 id="eccezioni-5"><em>Eccezioni</em>:</h3>
<ul>
<li>Se ci sono problemi con l’estrazione delle date dei dati da database , per esempio se la data inserita non è presente sul database, viene lanciata un’eccezione di tipo “DataMeteoException”.</li>
<li>Per problemi di parsing verso/da JSONObject viene lanciata l’eccezione ParseException.</li>
<li>Per problemi di I/O viene lanciata l’eccezione IOException.</li>
</ul>
<hr>
</div>
</body>

</html>
