#Aihemäärittely

##Kauppamatkaajan ongelma

- Kauppamatkaajan ongelma on yleisesti tunnettu NP-täydellinen ongelma, jossa pyritään ratkaisemaan kaikista kevein Hamiltonin kierros suuntaamattomassa painotetussa verkossa. Ongelman aikavaativuus kasvaa siis eksponentiaalisesti suhteessa solmujen lukumäärään. On kuitenkin mahdollista ratkaista suhteellisen tarkkoja optimointi-approksimaatioita tehokkaasti, jolloin syötteiden koko voi olla jopa satoja tuhansia siinä, missä täydellisen ratkaisun löytäminen onnistuu vain muutamalla kymmenellä solmulla.

- Ohjelmani tarkoituksena on pystyä laskemaan erilaisia approksimaatioita metriseen kauppamatkustajan ongelmaan käyttäen erityyppisiä algoritmeja. Metrisyys tarkoittaa sitä, että painot (solmujen etäisyydet) toteuttavat kolmioepäyhtälön, eli että kahden solmun välinen etäisyys ei voi lyhentyä siten, että käydään välissä toisessa solmussa. Mainittakoon käytännönsovelluksena mahdollisimman lyhyen/nopean reitin laskeminen pizzatoimituksessa useampaan osoitteeseen.

###Syöte

- Ohjelmaa on tarkoitus voida käyttää visuaalisen käyttöliittymän kautta, jolloin ainakin yksi tapa antaa syöte tapahtuisi klikkailemalla hiirellä solmuja koordinaatistoon. Näin varmistuttaisiin ainakin ongelman metrisyydestä.

- Mikäli laajennan ohjelmaa yleisemmäksi, saatan mahdollistaa muunkinlaisia syötteitä, mm. painotaulukoita, joita voi täyttää visuaalisesta käyttöliittymästä.

- Tuloste voisi olla varsinainen reitti, sen pituus ja ehkäpä jonkinlainen visualisaatio algoritmin edistymisestä. Visualisaatio saattaa olla ei-metrisessä tapauksessa hankalampi.

###Aikavaativuustavoitteet

- Ohjelman valittavilla algoritmeilla tulee olemaan erilaiset aikavaativuudet. Myös approksimaation tarkkuustavoitteet määräytyvät näiden mukaan.
 
###Tarvittavat tietorakenteet

- Tietorakenteet, joita tulen tarvitsemaan riippuvat luonnollisesti myös valitusta algoritmista.
