---


---

<h1 id="progetto-programmazione-ad-oggetti">PROGETTO PROGRAMMAZIONE AD OGGETTI</h1>
<p>L’applicazione permette principalmente all’ utente di ottenere e salvare dati meteo sulla percentuale di nuvolosità di una o più città.  Sarà inoltre possibile calcolare statistiche in base ai dati raccolti e filtrarle per data e/o nomi delle città. Consente anche di verificare la qualità delle previsioni tramite una soglia di errore opportunamente specificata.</p>
<h2 id="api-di-riferimento">Api di riferimento</h2>
<p>L’applicazione sviluppata riceve i dati meteo tramite le Api <a href="https://openweathermap.org/">OpenWeather</a>. Più precisamente fa uso di due Api specifiche:</p>
<ul>
<li><a href="https://openweathermap.org/forecast5">5 Day Weather Forecast</a></li>
<li><a href="https://openweathermap.org/current">Current Weather Data</a></li>
</ul>
<p>Visitando i link indicati, troverete tutte le informazioni e la documentazione a riguardo.</p>
<h2 id="installazione">Installazione</h2>
<p>Per installare l’applicazione sui propri dispositivi è sufficiente digitare il seguente comando nel proprio Terminale:</p>
<pre><code>git clone https://github.com/manuel-gizzarone/esame-OOP.git
</code></pre>
<p>Nel caso in cui il comando <code>git clone</code> non venga riconosciuto dal vostro sistema operativo, dovrete scaricare ed installare il software <a href="https://git-scm.com/downloads">Git</a>.</p>
<h2 id="configurazioni-iniziali">Configurazioni iniziali</h2>
<p>Per prima cosa è consigliabile creare un <a href="https://home.openweathermap.org/users/sign_up">account Openweather</a> per ottenere un<code>ApiKey</code> personale da inserire <a href="https://github.com/manuel-gizzarone/esame-OOP/blob/master/progettoDekGiz/config.json">qui</a>. Fare attenzione a non modificare il formato del file. Per motivi di praticità, abbiamo comunque messo a disposizione una <code>ApiKey</code> già attiva.</p>
<p>Una volta installata, per avviare l’applicazione sarà necessario essere in possesso di un IDE specifico per linguaggio <code>java</code> come ad esempio <a href="https://www.eclipse.org/downloads/">Eclipse</a> (o simili). Se lo avete già installato sul vostro dispositivo dovrete semplicemente importare la cartella dell’applicazione ed avviarla come <code>SpringBoot App</code>.<br>
L’interazione con l’utente viene gestita tramite protocollo <code>http</code> verso un server creato in maniera automatica sulla porta <code>8080</code> del vostro <code>localhost</code>. Perciò per utilizzare le funzionalità dell’applicazione ed effettuare richieste è necessario un browser, ma consigliamo l’installazione del software <strong>Postman</strong> specifico per effettuare richieste a server Web. Il download è disponibile al seguente <a href="https://www.postman.com/downloads/">link</a>.</p>
<h1 id="diagrammi-uml-finali">DIAGRAMMI UML FINALI</h1>
<p>Di seguito sono illustrati i diagrammi UML definitivi del progetto. I diagrammi prototipo costruiti prima dell’implementazione del codice sono invece visionabili <a href="https://github.com/manuel-gizzarone/esame-OOP/tree/master/progettoDekGiz/UML/UmlPrototype">qui</a>.</p>
<ul>
<li><strong>USE CASE DIAGRAM</strong><br>
<br><br>
<img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/UML/UmlFinal/ControllerUseCase.png" alt="USECASEdiagramFinal"><br>
<br></li>
<li><strong>CLASS DIAGRAM</strong><br>
<br><br>
<img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/UML/UmlFinal/ClassDiagram.png" alt="Class diagram"><br>
<br></li>
<li><strong>SEQUENCE DIAGRAM ROTTA "/nuvoleCitta5giorni"</strong><br>
<br></li>
</ul>
<p><img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/UML/UmlFinal/SequenceApi5giorni.png" alt="sequence diagram 1"><br>
<br></p>
<ul>
<li><strong>SEQUENCE DIAGRAM ROTTA "/statsGiornaliere"</strong><br>
<br></li>
</ul>
<p><img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/UML/UmlFinal/SequenceStatsGiornaliere.png" alt="sequence diagram 2"></p>
<h1 id="rotte-disponibili">ROTTE DISPONIBILI</h1>
<p>Le richieste devono essere effettuate al seguente indirizzo:</p>
<pre><code>localhost:8080
</code></pre>
<p>Le rotte disponibili sono riportate nella seguente tabella:</p>

<table>
<thead>
<tr>
<th>TIPO</th>
<th>ROTTA</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>POST</code></td>
<td>/nuvoleCitta5giorni</td>
</tr>
<tr>
<td><code>GET</code></td>
<td>/salvaOgniOra</td>
</tr>
<tr>
<td><code>GET</code></td>
<td>/statsGiornaliere</td>
</tr>
<tr>
<td><code>GET</code></td>
<td>/statsSettimanali</td>
</tr>
<tr>
<td><code>GET</code></td>
<td>/statsMensili</td>
</tr>
<tr>
<td><code>GET</code></td>
<td>/statsTotali</td>
</tr>
<tr>
<td><code>POST</code></td>
<td>/filtraStatsGiornaliero</td>
</tr>
<tr>
<td><code>POST</code></td>
<td>/filtraStatsSettimanale</td>
</tr>
<tr>
<td><code>POST</code></td>
<td>/filtraStatsMensile</td>
</tr>
<tr>
<td><code>POST</code></td>
<td>/filtraStatsTotale</td>
</tr>
<tr>
<td><code>DELETE</code></td>
<td>/deleteDatabase</td>
</tr>
<tr>
<td><code>GET</code></td>
<td>/getDatabase</td>
</tr>
<tr>
<td><code>POST</code></td>
<td>/previsioniSoglia</td>
</tr>
</tbody>
</table><p><strong>La spiegazione dell’utilizzo delle varie rotte, con relativi esempi di esecuzione, è mostrata di seguito</strong>.<br>
<br></p>
<h2 id="rotta-nuvolecitta5giorni">Rotta “/nuvoleCitta5giorni”</h2>
<p>Il suo fine è quello di far visualizzare le previsioni meteo sulla nuvolosità percentuale di una o più città dall’istante in cui si esegue la chiamata fino 5 giorni successivi. I dati di previsione sono intervallati di un tempo pari a 3 ore l’uno all’altro ed inoltre sono automaticamente salvati su un database nella cartella del progetto denominata <code>Database_Previsioni.json</code>.</p>
<p>Questa rotta è di tipo <code>POST</code>. Per funzionare correttamente ha bisogno di ricevere un <strong>body</strong> in formato <code>JSON</code> come indicato:</p>
<pre class=" language-json"><code class="prism  language-json"><span class="token punctuation">{</span>
<span class="token string">"nomiCitta"</span> <span class="token punctuation">:</span> <span class="token string">"listaNomiDelleCittaSeparateDallaVirgola"</span>
<span class="token punctuation">}</span>
</code></pre>
<p>Il risultato della chiamata sarà un <code>JSONArray</code> i cui singoli elementi di tipo <code>JSONObject</code> contengono le informazioni sulla nuvolosità delle città inserite.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/forecast5giorni3Citt%C3%A0.png" alt=""></p>
<p>Il <code>JSONArray</code> di risposta ottenuto è di seguito illustrato:</p>
<pre class=" language-json"><code class="prism  language-json"><span class="token punctuation">[</span>
    <span class="token punctuation">{</span>
        <span class="token string">"nuvolosita"</span><span class="token punctuation">:</span> <span class="token number">92</span><span class="token punctuation">,</span>
        <span class="token string">"Data"</span><span class="token punctuation">:</span> <span class="token string">"Thu Mar 18 01:00:00 CET 2021"</span><span class="token punctuation">,</span>
        <span class="token string">"unixData"</span><span class="token punctuation">:</span> <span class="token number">1616025600</span><span class="token punctuation">,</span>
        <span class="token string">"citta"</span><span class="token punctuation">:</span> <span class="token string">"Ripalimosani"</span>
    <span class="token punctuation">}</span><span class="token punctuation">,</span>
    <span class="token punctuation">{</span>
        <span class="token string">"nuvolosita"</span><span class="token punctuation">:</span> <span class="token number">96</span><span class="token punctuation">,</span>
        <span class="token string">"Data"</span><span class="token punctuation">:</span> <span class="token string">"Thu Mar 18 04:00:00 CET 2021"</span><span class="token punctuation">,</span>
        <span class="token string">"unixData"</span><span class="token punctuation">:</span> <span class="token number">1616036400</span><span class="token punctuation">,</span>
        <span class="token string">"citta"</span><span class="token punctuation">:</span> <span class="token string">"Ripalimosani"</span>
    <span class="token punctuation">}</span><span class="token punctuation">,</span>
   <span class="token operator">...</span>
	<span class="token punctuation">{</span>
        <span class="token string">"nuvolosita"</span><span class="token punctuation">:</span> <span class="token number">95</span><span class="token punctuation">,</span>
        <span class="token string">"Data"</span><span class="token punctuation">:</span> <span class="token string">"Thu Mar 18 04:00:00 CET 2021"</span><span class="token punctuation">,</span>
        <span class="token string">"unixData"</span><span class="token punctuation">:</span> <span class="token number">1616036400</span><span class="token punctuation">,</span>
        <span class="token string">"citta"</span><span class="token punctuation">:</span> <span class="token string">"Mirabello"</span>
    <span class="token punctuation">}</span><span class="token punctuation">,</span>
    <span class="token punctuation">{</span>
        <span class="token string">"nuvolosita"</span><span class="token punctuation">:</span> <span class="token number">92</span><span class="token punctuation">,</span>
        <span class="token string">"Data"</span><span class="token punctuation">:</span> <span class="token string">"Thu Mar 18 07:00:00 CET 2021"</span><span class="token punctuation">,</span>
        <span class="token string">"unixData"</span><span class="token punctuation">:</span> <span class="token number">1616047200</span><span class="token punctuation">,</span>
        <span class="token string">"citta"</span><span class="token punctuation">:</span> <span class="token string">"Mirabello"</span>
    <span class="token punctuation">}</span><span class="token punctuation">,</span>
    <span class="token operator">...</span>
	<span class="token punctuation">{</span>
        <span class="token string">"nuvolosita"</span><span class="token punctuation">:</span> <span class="token number">99</span><span class="token punctuation">,</span>
        <span class="token string">"Data"</span><span class="token punctuation">:</span> <span class="token string">"Thu Mar 18 01:00:00 CET 2021"</span><span class="token punctuation">,</span>
        <span class="token string">"unixData"</span><span class="token punctuation">:</span> <span class="token number">1616025600</span><span class="token punctuation">,</span>
        <span class="token string">"citta"</span><span class="token punctuation">:</span> <span class="token string">"Vinchiaturo"</span>
    <span class="token punctuation">}</span><span class="token punctuation">,</span>
    <span class="token punctuation">{</span>
        <span class="token string">"nuvolosita"</span><span class="token punctuation">:</span> <span class="token number">98</span><span class="token punctuation">,</span>
        <span class="token string">"Data"</span><span class="token punctuation">:</span> <span class="token string">"Thu Mar 18 04:00:00 CET 2021"</span><span class="token punctuation">,</span>
        <span class="token string">"unixData"</span><span class="token punctuation">:</span> <span class="token number">1616036400</span><span class="token punctuation">,</span>
        <span class="token string">"citta"</span><span class="token punctuation">:</span> <span class="token string">"Vinchiaturo"</span>
    <span class="token punctuation">}</span>
    <span class="token operator">...</span>
<span class="token punctuation">]</span> 
</code></pre>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>InserimentoException</strong>: se l’utente dimentica di inserire i nomi delle città</li>
<li><strong>NomeCittaException</strong>: se l’utente inserisce il nome di una città non disponibile o commette errori di digitazione</li>
<li><strong>GestisciStringaException</strong>: se si commettono errori nell’inserimento delle città (in particolare se vengono lasciati spazi tra le virgole durante l’inserimento)</li>
<li><strong>ConfigFileException</strong>: se sono presenti errori nel file di configurazione (viene lanciata se non rispetta il formato <code>JSON</code>)</li>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura dei dati forniti dalle Api<br>
<br></li>
</ul>
<h2 id="rotta-salvaogniora">Rotta “/salvaOgniOra”</h2>
<p>Il suo fine è quello di salvare ad intervalli regolari di un’ora i dati meteo sulla nuvolosità di una città a partire dall’istante della chiamata. Tali dati verranno salvati su un apposito database. Nel caso di chiamate multiple i dati di ogni città verranno salvati separatamente in un file apposito nella cartella del progetto col nome <code>"nomeCitta.json"</code>.</p>
<p>Questa rotta è di tipo <code>GET</code>. Per funzionare correttamente ha bisogno di ricevere un <strong>parametro</strong> del tipo:<br>
<code>key:value</code><br>
<strong>nomeCitta  :  nomeDellaCittà</strong></p>
<p>Il risultato della chiamata sarà una stringa contenente il <code>path</code> del database creato per il salvataggio dei dati.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/salvaOgniOra.png" alt="salvaOgniOra"></p>
<p>Un esempio di messaggio ricevuto è di seguito indicato:</p>
<pre><code>Path database:  C:\Users\manue\IdeaProjects\esame-OOP\progettoDekGiz\Ripalimosani.json
</code></pre>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>InserimentoException</strong>: se l’utente dimentica di inserire il nome della città</li>
<li><strong>NomeCittaException</strong>: se l’utente inserisce il nome di una città non disponibile o commette errori di digitazione</li>
<li><strong>ConfigFileException</strong>: se sono presenti errori nel file di configurazione (viene lanciata se non rispetta il formato <code>JSON</code>)</li>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura dei dati forniti dalle Api<br>
<br></li>
</ul>
<h2 id="rotta-statsgiornaliere">Rotta “/statsGiornaliere”</h2>
<p>Il suo fine è quello di calcolare e restituire le statistiche sulla nuvolosità percentuale al giorno della data inserita. Verranno visualizzate le seguenti informazioni:</p>
<ul>
<li><em>Valore minimo</em></li>
<li><em>Valore massimo</em></li>
<li><em>Media</em></li>
<li><em>Varianza</em></li>
</ul>
<p>Tali statistiche sono calcolate utilizzando i dati presenti sul database <code>Database_Previsioni</code>.<br>
Se non avete eseguito chiamate alla rotta <code>/nuvole5giorni</code>, il database ha già presente al suo interno dei dati predefiniti su alcune città dal 08/03/2021 al 18/03/2021.</p>
<p>Questa rotta è di tipo <code>GET</code>. Per funzionare correttamente ha bisogno di ricevere un <strong>parametro</strong> del tipo<br>
<code>key:value</code><br>
<strong>data  :  dd/mm/yyyy</strong><br>
<em>NOTA:</em> bisogna rispettare necessariamente questo formato.</p>
<p>Il risultato della chiamata è un <code>JSONObject</code> contenente le relative informazioni sulle statistiche.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/statsGiorn.png" alt="enter image description here"></p>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>InserimentoException</strong>: se l’utente dimentica di inserire la data</li>
<li><strong>DataMeteoException</strong>: se la data inserita non è presente nei dati contenuti dal database</li>
<li><strong>java.text.ParseException</strong>: nel caso la data viene inserita in un formato errato</li>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura del file contenente il database<br>
<br></li>
</ul>
<h2 id="rotta-statssettimanali">Rotta “/statsSettimanali”</h2>
<p>Il suo fine è quello di calcolare e restituire le statistiche sulla nuvolosità percentuale nella settimana (del mese) della data inserita. Verranno visualizzate le seguenti informazioni:</p>
<ul>
<li><em>Valore minimo</em></li>
<li><em>Valore massimo</em></li>
<li><em>Media</em></li>
<li><em>Varianza</em></li>
</ul>
<p>Tali statistiche sono calcolate utilizzando i dati presenti sul database <code>Database_Previsioni</code>.<br>
Se non avete eseguito chiamate alla rotta <code>/nuvole5giorni</code>, il database ha già presente al suo interno dei dati predefiniti su alcune città dal 08/03/2021 al 18/03/2021.</p>
<p>Questa rotta è di tipo <code>GET</code>. Per funzionare correttamente ha bisogno di ricevere un <strong>parametro</strong> del tipo<br>
<code>key:value</code><br>
<strong>data  :  dd/mm/yyyy</strong><br>
<em>NOTA:</em> bisogna rispettare necessariamente questo formato.</p>
<p>Il risultato della chiamata è un <code>JSONObject</code> contenente le relative informazioni sulle statistiche.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/statsSett.png" alt="enter image description here"></p>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>InserimentoException</strong>: se l’utente dimentica di inserire la data</li>
<li><strong>DataMeteoException</strong>: se la data inserita non è presente nei dati contenuti dal database</li>
<li><strong>java.text.ParseException</strong>: nel caso la data viene inserita in un formato errato</li>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura del file contenente il database<br>
<br></li>
</ul>
<h2 id="rotta-statsmensili">Rotta “/statsMensili”</h2>
<p>Il suo fine è quello di calcolare e restituire le statistiche sulla nuvolosità percentuale nel mese dell’ anno inserito. Verranno visualizzate le seguenti informazioni:</p>
<ul>
<li><em>Valore minimo</em></li>
<li><em>Valore massimo</em></li>
<li><em>Media</em></li>
<li><em>Varianza</em></li>
</ul>
<p>Tali statistiche sono calcolate utilizzando i dati presenti sul database <code>Database_Previsioni</code>.<br>
Se non avete eseguito chiamate alla rotta <code>/nuvole5giorni</code>, il database ha già presente al suo interno dei dati predefiniti su alcune città dal 08/03/2021 al 18/03/2021.</p>
<p>Questa rotta è di tipo <code>GET</code>. Per funzionare correttamente ha bisogno di ricevere un <strong>parametro</strong> del tipo<br>
<code>key:value</code><br>
<strong>data  :  mm/yyyy</strong><br>
<em>NOTA:</em> bisogna rispettare necessariamente questo formato.</p>
<p>Il risultato della chiamata è un <code>JSONObject</code> contenente le relative informazioni sulle statistiche.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/statsMens.png" alt="enter image description here"></p>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>InserimentoException</strong>: se l’utente dimentica di inserire la data</li>
<li><strong>DataMeteoException</strong>: se la data inserita non è presente nei dati contenuti dal database</li>
<li><strong>java.text.ParseException</strong>: nel caso la data viene inserita in un formato errato</li>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura del file contenente il database<br>
<br></li>
</ul>
<h2 id="rotta-statstotali">Rotta “/statsTotali”</h2>
<p>Il suo fine è quello di calcolare e restituire le statistiche sulla nuvolosità percentuale su tutti i dati meteo presenti nel database <code>Database_Previsioni</code>. Verranno visualizzate le seguenti informazioni:</p>
<ul>
<li><em>Valore minimo</em></li>
<li><em>Valore massimo</em></li>
<li><em>Media</em></li>
<li><em>Varianza</em></li>
</ul>
<p>Questa rotta è di tipo <code>GET</code>. Per funzionare correttamente <strong>non ha bisogno di ricevere alcun parametro</strong>.</p>
<p>Il risultato della chiamata è un <code>JSONObject</code> contenente le relative informazioni sulle statistiche.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/statsTotali.png" alt="enter image description here"></p>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura del file contenente il database<br>
<br></li>
</ul>
<h2 id="rotta-filtrastatsgiornaliero">Rotta “/filtraStatsGiornaliero”</h2>
<p>Il suo fine è quello di filtrare le statistiche giornaliere sulla percentuale di nuvolosità per data e città. Verranno visualizzate le seguenti informazioni:</p>
<ul>
<li><em>Città</em></li>
<li><em>Valore minimo</em></li>
<li><em>Valore massimo</em></li>
<li><em>Media</em></li>
<li><em>Varianza</em></li>
</ul>
<p>Se non avete eseguito chiamate alla rotta <code>/nuvole5giorni</code>, il database da cui verranno filtrate le statistiche <code>Database_Previsioni</code> ha già presente al suo interno dei dati predefiniti dal 08/03/2021 al 18/03/2021 su alcune città come Naples, Milan, Rome, Miami, New York ecc. Controllare il file per visualizzare tutte le possibili scelte iniziali.</p>
<p>Questa rotta è di tipo <code>POST</code>. Per funzionare correttamente richiede l’inserimento di un <strong>body</strong> in formato <code>JSON</code> come indicato:</p>
<pre class=" language-json"><code class="prism  language-json"><span class="token punctuation">{</span>

<span class="token string">"nomiCitta"</span><span class="token punctuation">:</span> <span class="token string">"listaNomiDelleCittaSeparateDallaVirgola"</span><span class="token punctuation">,</span>

<span class="token string">"data"</span><span class="token punctuation">:</span> <span class="token string">"dd/mm/yyyy"</span>

<span class="token punctuation">}</span>
</code></pre>
<p><em>NOTA:</em> bisogna rispettare necessariamente questo formato.</p>
<p>Il risultato della chiamata sarà un <code>JSONArray</code> i cui singoli elementi di tipo <code>JSONObject</code> contengono le statistiche giornaliere sulla nuvolosità delle città inserite alla data indicata.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://github.com/manuel-gizzarone/esame-OOP/blob/master/progettoDekGiz/Immagini/FiltersGiornaliero.png?raw=true" alt="enter image description here"></p>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>InserimentoException</strong>: se l’utente dimentica di inserire le città o la data</li>
<li><strong>FiltersException</strong>: se le città o la data inseriti non sono presenti nel database</li>
<li><strong>GestisciStringaException</strong>: se si commettono errori nell’inserimento delle città (in particolare se vengono lasciati spazi tra le virgole durante l’inserimento)</li>
<li><strong>java.text.ParseException</strong>: nel caso la data viene inserita in un formato errato</li>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura del file contenente il database<br>
<br></li>
</ul>
<h2 id="rotta-filtrastatssettimanale">Rotta “/filtraStatsSettimanale”</h2>
<p>Il suo fine è quello di filtrare le statistiche settimanali sulla percentuale di nuvolosità per data e città. In particolare a partire dalla data inserita, verrà calcolato il relativo indice della settimana nel mese.  Verranno visualizzate le seguenti informazioni:</p>
<ul>
<li><em>Città</em></li>
<li><em>Valore minimo</em></li>
<li><em>Valore massimo</em></li>
<li><em>Media</em></li>
<li><em>Varianza</em></li>
</ul>
<p>Se non avete eseguito chiamate alla rotta <code>/nuvole5giorni</code>, il database da cui verranno filtrate le statistiche <code>Database_Previsioni</code> ha già presente al suo interno dei dati predefiniti dal 08/03/2021 al 18/03/2021 su alcune città come Naples, Milan, Rome, Miami, New York ecc. Controllare il file per visualizzare tutte le possibili scelte iniziali.</p>
<p>Questa rotta è di tipo <code>POST</code>. Per funzionare correttamente richiede l’inserimento di un <strong>body</strong> in formato <code>JSON</code> come indicato:</p>
<pre class=" language-json"><code class="prism  language-json"><span class="token punctuation">{</span>

<span class="token string">"nomiCitta"</span><span class="token punctuation">:</span> <span class="token string">"listaNomiDelleCittaSeparateDallaVirgola"</span><span class="token punctuation">,</span>

<span class="token string">"data"</span><span class="token punctuation">:</span> <span class="token string">"dd/mm/yyyy"</span>

<span class="token punctuation">}</span>
</code></pre>
<p><em>NOTA:</em> bisogna rispettare necessariamente questo formato.</p>
<p>Il risultato della chiamata sarà un <code>JSONArray</code> i cui singoli elementi di tipo <code>JSONObject</code> contengono le statistiche settimanali sulla nuvolosità delle città inserite nella relativa settimana a cui appartiene la data inserita.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://github.com/manuel-gizzarone/esame-OOP/blob/master/progettoDekGiz/Immagini/FiltersSettimanale.png?raw=true" alt="enter image description here"></p>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>InserimentoException</strong>: se l’utente dimentica di inserire le città o la data</li>
<li><strong>FiltersException</strong>: se le città o la data inseriti non sono presenti nel database</li>
<li><strong>GestisciStringaException</strong>: se si commettono errori nell’inserimento delle città (in particolare se vengono lasciati spazi tra le virgole durante l’inserimento)</li>
<li><strong>java.text.ParseException</strong>: nel caso la data viene inserita in un formato errato</li>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura del file contenente il database<br>
<br></li>
</ul>
<h2 id="rotta-filtrastatsmensile">Rotta “/filtraStatsMensile”</h2>
<p>Il suo fine è quello di filtrare le statistiche mensili sulla percentuale di nuvolosità per numero del mese e città. Verranno visualizzate le seguenti informazioni:</p>
<ul>
<li><em>Città</em></li>
<li><em>Valore minimo</em></li>
<li><em>Valore massimo</em></li>
<li><em>Media</em></li>
<li><em>Varianza</em></li>
</ul>
<p>Se non avete eseguito chiamate alla rotta <code>/nuvole5giorni</code>, il database da cui verranno filtrate le statistiche <code>Database_Previsioni</code> ha già presente al suo interno dei dati predefiniti dal 08/03/2021 al 18/03/2021 su alcune città come Naples, Milan, Rome, Miami, New York ecc. Controllare il file per visualizzare tutte le possibili scelte iniziali.</p>
<p>Questa rotta è di tipo <code>POST</code>. Per funzionare correttamente richiede l’inserimento di un <strong>body</strong> in formato <code>JSON</code> come indicato:</p>
<pre class=" language-json"><code class="prism  language-json"><span class="token punctuation">{</span>

<span class="token string">"nomiCitta"</span><span class="token punctuation">:</span> <span class="token string">"listaNomiDelleCittaSeparateDallaVirgola"</span><span class="token punctuation">,</span>

<span class="token string">"data"</span><span class="token punctuation">:</span> <span class="token string">"mm/yyyy"</span>

<span class="token punctuation">}</span>
</code></pre>
<p><em>NOTA:</em> bisogna rispettare necessariamente questo formato.</p>
<p>Il risultato della chiamata sarà un <code>JSONArray</code> i cui singoli elementi di tipo <code>JSONObject</code> contengono le statistiche mensili sulla nuvolosità delle città inserite nel relativo mese indicato.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://github.com/manuel-gizzarone/esame-OOP/blob/master/progettoDekGiz/Immagini/FiltersMensile.png?raw=true" alt="enter image description here"></p>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>InserimentoException</strong>: se l’utente dimentica di inserire le città o la data</li>
<li><strong>FiltersException</strong>: se le città o la data inseriti non sono presenti nel database</li>
<li><strong>GestisciStringaException</strong>: se si commettono errori nell’inserimento delle città (in particolare se vengono lasciati spazi tra le virgole durante l’inserimento)</li>
<li><strong>java.text.ParseException</strong>: nel caso la data viene inserita in un formato errato</li>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura del file contenente il database<br>
<br></li>
</ul>
<h2 id="rotta-filtrastatstotale">Rotta “/filtraStatsTotale”</h2>
<p>Il suo fine è quello di filtrare le statistiche totali sulla percentuale di nuvolosità per città. Saranno quindi visualizzate statistiche su tutti i dati presenti nel database, relativi alle città inserite. Verranno visualizzate le seguenti informazioni:</p>
<ul>
<li><em>Città</em></li>
<li><em>Valore minimo</em></li>
<li><em>Valore massimo</em></li>
<li><em>Media</em></li>
<li><em>Varianza</em></li>
</ul>
<p>Se non avete eseguito chiamate alla rotta <code>/nuvole5giorni</code>, il database da cui verranno filtrate le statistiche <code>Database_Previsioni</code> ha già presente al suo interno dei dati predefiniti su alcune città come Naples, Milan, Rome, Miami, New York ecc. Controllare il file per visualizzare tutte le possibili scelte iniziali.</p>
<p>Questa rotta è di tipo <code>POST</code>. Per funzionare correttamente richiede l’inserimento di un <strong>body</strong> in formato <code>JSON</code> come indicato:</p>
<pre class=" language-json"><code class="prism  language-json"><span class="token punctuation">{</span>
<span class="token string">"nomiCitta"</span><span class="token punctuation">:</span> <span class="token string">"listaNomiDelleCittaSeparateDallaVirgola"</span>
<span class="token punctuation">}</span>
</code></pre>
<p><em>NOTA:</em> bisogna rispettare necessariamente questo formato.</p>
<p>Il risultato della chiamata sarà un <code>JSONArray</code> i cui singoli elementi di tipo <code>JSONObject</code> contengono le statistiche totali sulla nuvolosità delle città inserite.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://github.com/manuel-gizzarone/esame-OOP/blob/master/progettoDekGiz/Immagini/FiltersTotali.png?raw=true" alt="enter image description here"></p>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>InserimentoException</strong>: se l’utente dimentica di inserire le città</li>
<li><strong>FiltersException</strong>: se una delle città inserite non sono presenti nei dati contenuti dal database</li>
<li><strong>GestisciStringaException</strong>: se si commettono errori nell’inserimento delle città (in particolare se vengono lasciati spazi tra le virgole durante l’inserimento)</li>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura del file contenente il database<br>
<br></li>
</ul>
<h2 id="rotta-getdatabase">Rotta “/getDatabase”</h2>
<p>Il suo fine è quello di dare la possibilità all’utente di visualizzare i dati meteo presenti all’interno di un qualsiasi database presente.</p>
<p>Questa rotta è di tipo <code>GET</code>. Per funzionare correttamente ha bisogno di ricevere un <strong>parametro</strong> del tipo<br>
<code>key:value</code><br>
<strong>nomeDatabase  :  nomeDelDatabaseDaVisualizzare</strong></p>
<p>Il risultato della chiamata sarà un <code>JSONArray</code> i cui singoli elementi di tipo <code>JSONObject</code> contengono i dati meteo presenti all’interno del database. Se quest’ultimo esiste ma non ha ancora nessun dato al suo interno, verrà visualizzato un <code>JSONArray</code> vuoto.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://github.com/manuel-gizzarone/esame-OOP/blob/master/progettoDekGiz/Immagini/getDatabase.png?raw=true" alt="enter image description here"></p>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>InserimentoException</strong>: se l’utente dimentica di inserire le nome del database</li>
<li><strong>DatabaseNotFoundException</strong>: se l’utente prova a visualizzare un database inesistente</li>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura del file contenente il database<br>
<br></li>
</ul>
<h2 id="rotta-deletedatabase">Rotta “/deleteDatabase”</h2>
<p>Il suo fine è quello di dare la possibilità all’utente di eliminare i dati meteo presenti all’interno di un database indicato. In particolare una volta eseguita la richiesta, il database, se esiste, verrà formattato, ma non verrà eliminato definitivamente il file che lo contiene. Se infatti, una volta eseguita la formattazione, proviamo a visualizzare tramite la rotta <code>/getDatabase</code> il suo contenuto, sarà visualizzato un <code>JSONArray</code> vuoto.</p>
<p>Questa rotta è di tipo <code>DELETE</code>. Per funzionare correttamente ha bisogno di ricevere un <strong>parametro</strong> del tipo<br>
<code>key:value</code><br>
<strong>nomeDatabase  :  nomeDelDatabaseDaFormattare</strong></p>
<p>Il risultato della chiamata sarà una stringa contenente un messaggio di avvenuta eliminazione dei dati.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://github.com/manuel-gizzarone/esame-OOP/blob/master/progettoDekGiz/Immagini/DeleteDatabase.png?raw=true" alt="enter image description here"></p>
<p>Il messaggio ricevuto relativo all’esempio qui indicato è il seguente:</p>
<pre><code>Il contenuto del database 'Ancona' è stato eliminato.
</code></pre>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>InserimentoException</strong>: se l’utente dimentica di inserire le nome del database</li>
<li><strong>DatabaseNotFoundException</strong>: se l’utente prova a visualizzare un database inesistente</li>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura del file contenente il database<br>
<br></li>
</ul>
<h2 id="rotta-previsionisoglia">Rotta “/previsioniSoglia”</h2>
<p>Il suo fine è quello di generare statistiche sulla quantità di previsioni azzeccate riguardo una città, in un dato periodo tra una data iniziale e finale e data una soglia di errore massima. In particolare verranno messi a confronto due database:</p>
<ul>
<li><code>Database_Previsioni</code> contenente i dati previsti</li>
<li><code>Database_Raccolta</code> contenente i dati reali</li>
</ul>
<p>Le date inizialmente disponibili vanno dal 08/03/2021 al 18/03/2021 e riguardano dati meteo soltanto per le seguenti città:</p>
<p>✔️ Rome</p>
<p>✔️ Naples</p>
<p>✔️ Milan</p>
<p>Successivamente sarà possibile generare statistiche anche su altre città e date. In particolare per fare ciò è necessario raccogliere altri dati, tramite le apposite rotte <code>/salvaOgniOra</code> e <code>/nuvoleCitta5giorni</code>, su altre città a vostra scelta. Una volta raccolti dati a sufficienza dovrete prendere manualmente i dati meteo da ogni file <code>nomeCitta.json</code> ed incollarli nel <code>Database_Raccolta</code>, facendo attenzione a non modificare il formato del file (controllare sempre che sia presente un unico <code>JSONArray</code> con all’interno i relativi <code>JSONObject</code> contenenti i dati meteo). Riguardo invece il <code>Database_Previsioni</code> non è necessario apportare nessuna modifica.</p>
<p>Questa rotta è di tipo <code>POST</code>. Per funzionare correttamente richiede l’inserimento di un <strong>body</strong> in formato <code>JSON</code> come indicato:</p>
<pre class=" language-json"><code class="prism  language-json"><span class="token punctuation">{</span>

<span class="token string">"nomeCitta"</span><span class="token punctuation">:</span> <span class="token string">"NomeDellaCitta"</span><span class="token punctuation">,</span>

<span class="token string">"dataInizio"</span><span class="token punctuation">:</span> <span class="token string">"dd/mm/yyyy"</span><span class="token punctuation">,</span>

<span class="token string">"dataFine"</span><span class="token punctuation">:</span> <span class="token string">"dd/mm/yyyy"</span><span class="token punctuation">,</span>

<span class="token string">"sogliaErrore"</span><span class="token punctuation">:</span> <span class="token string">"SogliaErroreMassima"</span>

<span class="token punctuation">}</span>
</code></pre>
<p><em>NOTA:</em> bisogna rispettare necessariamente questo formato.<br>
<em>NOTA</em>: La soglia di errore deve essere un numero compreso tra 1 e 99, estremi inclusi.</p>
<p>Il risultato della chiamata è un <code>JSONObject</code> contenente le relative informazioni sulle statistiche riguardo la qualità delle previsioni.</p>
<p>✅<strong>ESEMPIO</strong></p>
<p><img src="https://github.com/manuel-gizzarone/esame-OOP/blob/master/progettoDekGiz/Immagini/PrevisioniSoglia.png?raw=true" alt="enter image description here"></p>
<p>🔴 <strong>ECCEZIONI</strong></p>
<ul>
<li><strong>InserimentoException</strong>: se l’utente dimentica di inserire uno tra i 4 campi richiesti</li>
<li><strong>SogliaErroreNotValidException</strong>: se l’utente inserisce una soglia di errore non valida (cioè non compresa tra 1 e 99)</li>
<li><strong>PeriodNotValidException</strong>: se l’utente inserisce un periodo non valido (in particolare se la data di fine risulta precedente alla data di inizio del periodo)</li>
<li><strong>ParseException</strong>: nel caso in cui si verifichino errori durante il parsing dei dati</li>
<li><strong>IOException</strong>: nel caso si verifichino errori durante la lettura dei file contenenti i database<br>
<br></li>
</ul>
<h1 id="test">TEST</h1>
<p>Abbiamo implementato alcuni <code>test</code> per verificare il corretto funzionamento del codice. Per farlo abbiamo fatto uso della libreria <a href="https://junit.org/junit5/docs/current/user-guide/">JUNIT 5</a> specifica per eseguire test automatizzati in Java. Al seguente <a href="https://github.com/manuel-gizzarone/esame-OOP/tree/master/progettoDekGiz/src/test/java/com/OOPDekGiz/progettoDekGiz">link</a> è possibile visionare il codice implementato.<br>
<br></p>
<h1 id="documentazione">DOCUMENTAZIONE</h1>
<p>L’applicazione sviluppata presenta un’ apposita <a href="https://github.com/manuel-gizzarone/esame-OOP/tree/master/progettoDekGiz/doc">documentazione</a> del codice implementato.<br>
<br></p>
<h2 id="autori"><em>Autori</em></h2>
<p>😎 <em>Emanuele De Caro</em></p>
<p>😎 <em>Manuel Gizzarone</em></p>

