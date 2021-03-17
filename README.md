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
<ul>
<li>“/nuvoleCitta5giorni”</li>
<li>“/salvaOgniOra”</li>
<li>“/statsGiornaliere”</li>
<li>“/statsSettimanali”</li>
<li>“/statsMensili”</li>
<li>“/statsTotali”</li>
<li>“/filtraStatsGiornaliero”</li>
<li>“/filtraStatsSettimanale”</li>
<li>“/filtraStatsMensile”</li>
<li>“/filtraStatsTotale”</li>
<li>“/deleteDatabase”</li>
<li>“/getDatabase”</li>
<li>“/previsioniSoglia”</li>
</ul>
<p>La spiegazione dell’utilizzo delle varie rotte con esempi di esecuzione è mostrato in seguito.</p>
</div>
</body>

</html>
