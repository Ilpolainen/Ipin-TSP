#Testausdokumentti

###Tietorakenteet

####MyStack

On testattu, mutta testitiedosto katosi Git- ja Maven- sekoiluissa.

####Heap

Konstruktorien ja mekaanisten bugitestien lisäksi on keosta testattu olennaisesti seuraavia asioita: 

- Parent(), leftChild() ja rightChild() antavat kekotaulukon avaimistaq oikeita arvoja.

- Heapinsert() pitää lukua keon koosta, joka siis on eri kuin kekotaulukon koko.

- Heapinsert(): istä on tarkistettu, että kekoehto pysyy voimassa seitsemällä alkiolla. Monen alkion toiminnan testaaminen olisi todella työlästä, sillä homma täytyy käytännössä käydä aina paperilla läpi, ja erikseen kysyä jokainen arvo. Siksi on tyydytty siihen, että myöhemmät delmaxit toimivat suurillakin syötteillä, jolloin on erittäin epätodennäköistä, että insert toimisi väärin.

- Tyhjästä keosta saa ainoastaan null-arvon, eikä virheilmoitusta synny.

- Delmaxin toiminta on testattu siten, että syötetään kekoon paljon random-painoisia avaimia ja vedetään ne sitten järjestyksessä ulos tarkistaen, että seuraava alkio on aina edellistä pienempi. Tällöin maksimikeko toimii haluamallamme tavalla.


####WheelList

WheelLististä On testattu seuraavia asioita:

- Tyhjä konstruktori asettaa headiksi nullin ja listan kooksi 0.

- Alkioiden lisääminen ja poistaminen päivittää toivotusti listan kokoa.

- Toinen konstruktori asettaa oikean koon listalle, ja asettaa kooksi nolla, mikäli syöte on negatiivinen.

- Toinen konstruktori asettaa myös annettavan lukumäärän alkioita listaan numeroidussa järjestyksessä nollasta n:ään.

- Ensimmäinen insert toimii siten, että listan head on juuri syötetty alkio, ja sen prev ja next ovat alkio itse.

- Toinen insert toimii siten, että head on viimeisenä lisätty alkio ja sen next ja prev ovat ensimmäisenä lisätty alkio.

- Useampi insert toimii siten, että uudesta alkiosta tulee aina head, ja edellisestä headista sen prev. Headin next on aina listan viimeinen, eli ensimmäisenä lisätty alkio.

- On myös testattu, että alkioiden järjestys on muutenkin käänteinen lisäämisjärjestykseen nähden.

- Empty() tunnistaa tyhjän ja epätyhjän listan.

- TakeHeadista on tarkastettu, että se antaatyhjästä listasta nullin alkion, ja muuten se aina palauttaa headin samalla saattaen listan samaan tilaan, jossa lista oli ennen edellistä lisäystä.

- Rotate() asettaa headiksi entisen headin previn, ja pitää muuten listan järjestyksessä ja koon ennallaan.

- Lisäksi on kokeiltu useampia perättäisiä inserttejä, takeHeadeja ja rotateja integraatiotestauksena. 

####Union-Find

(sijaitsee väärässä pakkauksessa, sillä en uskalla muuttaa enää sijaintia).

- Find on tutkittu erikseen, ja myös sen poluntiivistys on testattu.

- Union -metodista on testattu, että se päivittää parents-taulukkoa ja heights taulukkoa oikein.

- Forming-Cycles on ollut hiukan vaikeampi testata, sillä se käyttää Union -metodia, joka muuttaa aina tilannetta. Siksi se on pitänyt testata yhtaikaa Union -metodin toimivuuden kanssa. On testattu, tunnistaako se, milloin yhteysjoukkoon lisättävä kaari muodostaa syklin. 


###Algortimit

####Simulated Annealing

- Algoritmista on toistaiseksi tutkittu ainoastaan se, että se antaa vakioasetuksilla 14 kaupungilla reitin, jonka pituus on korkeintaan 104 prosenttia optimaalisesta
, jonka arvo saadaan BruteForce -algoritmilla. BruteForce taas on testattu omalla tavallaan, joka näkyy alla.

- Lisäksi on tehty käytännössä nollatestaus, eli että kaupunkien lukumäärän kohotessa algoritmi toimii silmänräpäyksessä. Tämä on kuitenkin aika turhaa, sillä kaupunkien lukumäärä
 ei vaikuta algoritmin nopeuteen, vaan ainoastaan tarkuuteen, ja nopeus määräytyy muiden parametrien arvoista. Sen, kuinka hyviä arvioita algoritmi antaa minkäkinlaisilla arvoilla voi todistaa ainoastaan teoreettisesti, sillä ongelma on NP-täydellinen, eikä tunneta mitään tehokasta tapaa saada oikeaa vastausta, josta arvot voisi tarkistaa.  

####BruteForce

- Bruteforcen oikeellisuutta on tutkittu siten, että asetetaan kaupungit sellaisella tavalla, että on helppo todeta lyhimmän reitin todella löytyneen. Tämä tapahtuu asettamalla kaupungit ruudukkomuodostelmiin, joista matemaattisesti on kohtalaisen helppo todistaa, että lyhin reitti ei voi olla pienempää, kuin se, minkä olen antanut testeissä oikeana vastauksena. Todistusta en kuitenkaan nyt jaksa tehdä. Lisäksi on todettava, että tällainen ruudukko voi mahdollisesti olla algoritmin kannalta erikoistapaus, eikä yleistä toimivuutta pystytä näillä testeillä osoittamaan. Muodostelmia on koitettu erikokoisilla syötteillä (4, 8, 12, 14).

- Algoritmin nopeutta on tutkittu kahdellatoista random-kaupungilla. Se suoriutuu tehtävästä alle sekunnin. (Parikymmentä kaupunkia veisi mahdollisesti jo lähes miljoona vuotta ja 21 kaupunkie yli miljoona vuotta.)

- Algoritmi on rekursiivinen, eikä sillä ole apumetodeita, joten ainoastaan tuloksia on tutkittu.

####ClarkeWright

-ClarkeWright on toistaiseksi testaamatta. Sen tietorakenteet Heap ja Union-Find on kuitenkin testattu. Muuten algoritmi ei itsessään kuulu palautukseeni.
