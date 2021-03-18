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
<p>L’interazione con l’applicazione, una volta avviata, viene gestita tramite protocollo <code>http</code> verso un server creato in maniera automatica sulla porta <code>8080</code> del vostro <code>localhost</code>. Perciò per utilizzare le sue funzionalità ed effettuare richieste è necessario un browser, ma consigliamo l’installazione del software <strong>Postman</strong> specifico per effettuare richieste a server Web. Il download è disponibile al seguente <a href="https://www.postman.com/downloads/">link</a>.</p>
<h2 id="diagrammi-uml-finali">DIAGRAMMI UML FINALI</h2>
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
<h1 id="rotte-disponibili">Rotte disponibili</h1>
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
<p><em>NOTA:<br>
Le statistiche sia filtrate che non fanno tutte riferimento ai dati presenti sul database <code>Database_Previsioni.json</code>.<br>
La rotta per la verifica della qualità delle previsioni con soglia di errore fa utilizzo sia del <code>Database_Previsioni.json</code> che del <code>Database_Raccolta.json</code> . Entrambi i file sono già riempiti con dati da noi raccolti sulle città Rome, Milan, Naples per consentire il diretto uso di tutte le rotte già al primo avvio dell’applicazione.</em><br>
<br></p>
<h2 id="rotta-nuvolecitta5giorni">Rotta “/nuvoleCitta5giorni”</h2>
<p>Il suo fine è quello di far visualizzare le previsioni meteo sulla nuvolosità percentuale di una o più città dall’istante in cui si esegue la chiamata fino 5 giorni successivi. I dati di previsione sono intervallati di un tempo pari a 3 ore l’uno all’altro ed inoltre sono automaticamente salvati su un database nella cartella del progetto denominata <code>Database_Previsioni.json</code>.</p>
<p>Questa rotta è di tipo <code>POST</code>. Per funzionare correttamente ha bisogno di ricevere un <strong>body</strong> in formato <code>JSON</code> come indicato:</p>
<pre class=" language-json"><code class="prism  language-json"><span class="token punctuation">{</span><span class="token string">"nomiCitta"</span> <span class="token punctuation">:</span> <span class="token string">"listaNomiDelleCittaSeparateDallaVirgola"</span><span class="token punctuation">}</span>
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
    <span class="token punctuation">}</span><span class="token punctuation">,</span>
<span class="token operator">...</span>
<span class="token punctuation">]</span>

  
<span class="token punctuation">:</span>red_circle<span class="token punctuation">:</span> <span class="token operator">**</span>ECCEZIONI<span class="token operator">**</span>

 <span class="token operator">-</span> <span class="token operator">**</span>InserimentoException<span class="token operator">**</span><span class="token punctuation">:</span> se l'utente dimentica di inserire i nomi delle città
 <span class="token operator">-</span> <span class="token operator">**</span>NomeCittaException<span class="token operator">**</span><span class="token punctuation">:</span> se l'utente inserisce il nome di una città non disponibile o commette errori di digitazione
 <span class="token operator">-</span> <span class="token operator">**</span>GestisciStringaException<span class="token operator">**</span><span class="token punctuation">:</span> se si commettono errori nell<span class="token string">'inserimento delle città (in particolare se vengano lasciati spazi tra le virgole durante l'</span>inserimento<span class="token punctuation">)</span>
 <span class="token operator">-</span> <span class="token operator">**</span>ConfigFileException<span class="token operator">**</span><span class="token punctuation">:</span> se sono presenti errori nel file di <span class="token function">configurazione</span> <span class="token punctuation">(</span>viene lanciata se non rispetta il formato <span class="token template-string"><span class="token string">`JSON`</span></span><span class="token punctuation">)</span>
  <span class="token operator">-</span> <span class="token operator">**</span>ParseException<span class="token operator">**</span><span class="token punctuation">:</span> nel caso <span class="token keyword">in</span> cui si verifichino errori durante il parsing dei dati
 <span class="token operator">-</span> <span class="token operator">**</span>IOException<span class="token operator">**</span><span class="token punctuation">:</span> nel caso si verifichino errori durante la lettura del file contenente il database
<span class="token operator">&lt;</span>br<span class="token operator">&gt;</span>
## Rotta <span class="token string">"/salvaOgniOra"</span>

Il suo fine è quello di salvare ad intervalli regolari di un<span class="token string">'ora i dati meteo sulla nuvolosità di una città a partire dall'</span>istante della chiamata<span class="token punctuation">.</span> Tali dati verranno salvati su un apposito database<span class="token punctuation">.</span> Nel caso di chiamate multiple i dati di ogni città verranno salvati separatamente <span class="token keyword">in</span> un file apposito nella cartella del progetto col nome <span class="token template-string"><span class="token string">`"nomeCitta.json"`</span></span><span class="token punctuation">.</span>

Questa rotta è di tipo <span class="token template-string"><span class="token string">`GET`</span></span><span class="token punctuation">.</span> Per funzionare correttamente ha bisogno di ricevere un <span class="token operator">**</span>parametro<span class="token operator">**</span> del tipo
<span class="token template-string"><span class="token string">`key:value`</span></span>
<span class="token operator">**</span><span class="token string">"nomeCitta"</span> <span class="token punctuation">:</span> <span class="token string">"nomeDellaCittà"</span><span class="token operator">**</span>

Il risultato della chiamata sarà una stringa contenente il <span class="token template-string"><span class="token string">`path`</span></span> del database creato per il salvataggio dei dati<span class="token punctuation">.</span> 

<span class="token punctuation">:</span>white_check_mark<span class="token punctuation">:</span><span class="token operator">**</span>ESEMPIO<span class="token operator">**</span>

<span class="token operator">!</span><span class="token punctuation">[</span>salvaOgniOra<span class="token punctuation">]</span><span class="token punctuation">(</span>https<span class="token punctuation">:</span><span class="token operator">/</span><span class="token operator">/</span>raw<span class="token punctuation">.</span>githubusercontent<span class="token punctuation">.</span>com<span class="token operator">/</span>manuel<span class="token operator">-</span>gizzarone<span class="token operator">/</span>esame<span class="token operator">-</span>OOP<span class="token operator">/</span>master<span class="token operator">/</span>progettoDekGiz<span class="token operator">/</span>Immagini<span class="token operator">/</span>salvaOgniOra<span class="token punctuation">.</span>png<span class="token punctuation">)</span>

Un esempio di messaggio ricevuto è di seguito indicato<span class="token punctuation">:</span>
</code></pre>
<p>Path database:  C:\Users\manue\IdeaProjects\esame-OOP\progettoDekGiz\Ripalimosani.json</p>
<pre><code>
:red_circle: **ECCEZIONI**
 - **InserimentoException**: se l'utente dimentica di inserire il nome della città
 - **NomeCittaException**: se l'utente inserisce il nome di una città non disponibile o commette errori di digitazione
  - **ConfigFileException**: se sono presenti errori nel file di configurazione (viene lanciata se non rispetta il formato `JSON`)
  - **ParseException**: nel caso in cui si verifichino errori durante il parsing dei dati
 - **IOException**: nel caso si verifichino errori durante la lettura del file contenente il database
 &lt;br&gt;
## Rotta "/statsGiornaliere"

Il suo fine è quello di calcolare e restituire le statistiche sulla nuvolosità percentuale al giorno della data inserita. Verranno visualizzate le seguenti informazioni:
 - *Valore minimo* 
 - *Valore massimo*
 - *Media*
 - *Varianza*

Tali statistiche sono calcolate utilizzando i dati presenti sul database `Database_Previsioni.json`.
Se non avete eseguito chiamate alla rotta `/nuvole5giorni` il database ha già presente al suo interno i dati predefiniti sulle città Naples, Milan e Rome relativi alla nuvolosità del mese di marzo dal 8/03/2021 al 17/03/2021.

Questa rotta è di tipo `GET`. Per funzionare correttamente ha bisogno di ricevere un **parametro** del tipo
`key:value`
**"data" : "dd/mm/yyyy"**    
*NOTA:* bisogna rispettare necessariamente questo formato.

Il risultato della chiamata è un `JSONObject` contenente le relative informazioni sulle statistiche.

:white_check_mark:**ESEMPIO**

![enter image description here](https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/statsGiorn.png)

:red_circle: **ECCEZIONI**
 - **InserimentoException**: se l'utente dimentica di inserire la data
 - **DataMeteoException**: se la data inserita non è presente nei dati contenuti dal database
 - **java.text.ParseException**: nel caso la data viene inserita in un formato errato
  - **ParseException**: nel caso in cui si verifichino errori durante il parsing dei dati
 - **IOException**: nel caso si verifichino errori durante la lettura del file contenente il database
&lt;br&gt;
## Rotta "/statsSettimanali"

Il suo fine è quello di calcolare e restituire le statistiche sulla nuvolosità percentuale nella settimana (del mese) della data inserita. Verranno visualizzate le seguenti informazioni:
 - *Valore minimo* 
 - *Valore massimo*
 - *Media*
 - *Varianza*

Tali statistiche sono calcolate utilizzando i dati presenti sul database `Database_Previsioni.json`.
Se non avete eseguito chiamate alla rotta `/nuvole5giorni` il database ha già presente al suo interno i dati predefiniti sulle città Naples, Milan e Rome relativi alla nuvolosità del mese di marzo dal 8/03/2021 al 17/03/2021

Questa rotta è di tipo `GET`. Per funzionare correttamente ha bisogno di ricevere un **parametro** del tipo
`key:value`
**"data" : "dd/mm/yyyy"**    
*NOTA:* bisogna rispettare necessariamente questo formato.

Il risultato della chiamata è un `JSONObject` contenente le relative informazioni sulle statistiche.

:white_check_mark:**ESEMPIO**

![enter image description here](https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/statsSett.png)

:red_circle: **ECCEZIONI**
 - **InserimentoException**: se l'utente dimentica di inserire la data
 - **DataMeteoException**: se la data inserita non è presente nei dati contenuti dal database
 - **java.text.ParseException**: nel caso la data viene inserita in un formato errato
  - **ParseException**: nel caso in cui si verifichino errori durante il parsing dei dati
 - **IOException**: nel caso si verifichino errori durante la lettura del file contenente il database
&lt;br&gt;
## Rotta "/statsMensili"

Il suo fine è quello di calcolare e restituire le statistiche sulla nuvolosità percentuale nel mese dell' anno inserito. Verranno visualizzate le seguenti informazioni:
 - *Valore minimo* 
 - *Valore massimo*
 - *Media*
 - *Varianza*

Tali statistiche sono calcolate utilizzando i dati presenti sul database `Database_Previsioni.json`.
Se non avete eseguito chiamate alla rotta `/nuvole5giorni` il database ha già presente al suo interno i dati predefiniti sulle città Naples, Milan e Rome relativi alla nuvolosità del mese di marzo dal 8/03/2021 al 17/03/2021

Questa rotta è di tipo `GET`. Per funzionare correttamente ha bisogno di ricevere un **parametro** del tipo
`key:value`
**"data" : "mm/yyyy"**
*NOTA:* bisogna rispettare necessariamente questo formato.

Il risultato della chiamata è un `JSONObject` contenente le relative informazioni sulle statistiche.

:white_check_mark:**ESEMPIO**

![enter image description here](https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/statsMens.png)

:red_circle: **ECCEZIONI**
 - **InserimentoException**: se l'utente dimentica di inserire la data
 - **DataMeteoException**: se la data inserita non è presente nei dati contenuti dal database
 - **java.text.ParseException**: nel caso la data viene inserita in un formato errato
 - **ParseException**: nel caso in cui si verifichino errori durante il parsing dei dati
 - **IOException**: nel caso si verifichino errori durante la lettura del file contenente il database
 &lt;br&gt;
## Rotta "/statsTotali"

Il suo fine è quello di calcolare e restituire le statistiche sulla nuvolosità percentuale su tutti i dati meteo presenti nel database `Database_Previsioni.json`. Verranno visualizzate le seguenti informazioni:
 - *Valore minimo* 
 - *Valore massimo*
 - *Media*
 - *Varianza*

Questa rotta è di tipo `GET`. Per funzionare correttamente **non ha bisogno di ricevere alcun parametro**.

Il risultato della chiamata è un `JSONObject` contenente le relative informazioni sulle statistiche.

:white_check_mark:**ESEMPIO**

![enter image description here](https://raw.githubusercontent.com/manuel-gizzarone/esame-OOP/master/progettoDekGiz/Immagini/statsTotali.png)

:red_circle: **ECCEZIONI**

 - **ParseException**: nel caso in cui si verifichino errori durante il parsing dei dati
 - **IOException**: nel caso si verifichino errori durante la lettura del file contenente il database

</code></pre>

