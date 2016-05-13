#Toteutusdokumentti

###Ohjelman yleisrakenne

Ohjelma rakentuu TSP-ongelman algoritmeista, niiden apuvälineluokista, itsetoteutetuista tietorakenteista ja visuaalisesta käyttöliittymästä.

Käyttöliittymä antaa mahdollisuuden painaa hiirellä piirtoalustalle pisteitä, jotka edustavat "kaupunkeja" koordinaatteinaan alustan x ja y -koordinaatit. Tämän jälkeen käyttäjä voi valita tällä hetkellä kolmesta algoritmista ratkaisutavan kauppamatkaajan ongelmalle. Käytössä ovat: Brute Force, Simulated Annealing ja Clarke-Wrightin "savings" -algoritmi. Viimeisenä mainittu on kuitenkin ongelman ratkaisuun huono, sillä se on kehitetty samaa sukua olevaan NP-tyäydelliseen Vehicle-Routing Problemiin. Niinpä se on jätetty ohjelmaan lähinnä jatkokehitystä varten, sekä siksi, että näin siinä kovasti vaivaa, ja ennenkaikkea toteutin muutaman kivan tietorakenteen (keko ja union-find). Lisäksi minusta tuntuu, ettei se vielä toimi niinkuin pitäisi.

Huvin vuoksi voi valita myös neljännen vaihtoehdon, jossa Simulated Annealing toteutetaan animaation kera.

###Algoritmien aika -ja tilavaativuudet. (n viittaa kaupunkien lukumäärään.)

Brute Force on aikavaativuudeltaan O(n!), mikä ei täytä aivan sitä mitä toivoin, sillä se on ilmeisesti mahdollista toteuttaa myös O((n-1)!). Muuten algoritmi on nyt O((n-1)!), mutta kirjanpito parhaasta löydetystä tapahtuu nyt kopioimalla lista rekursion päässä. Tämä räjäyttää luonnollisesti myös tilavaativuuden pilviin, minkä takia en ole alkuunkaan tyytyväinen algoritmiin.

ClarkeWright on aikavaativuudeltaan O(n^3*log(n)), sillä varsinainen Clarke-Wright -savings = O(n^2*log(n)), mutta versiossani tämä suoritetaan käyttäen keskuksena vuorollaan jokaista kaupunkia, joita siis on n kpl. Koko algoritmi on kuitenkin susi.

Simulated Annealing:in aikavaativuus ei riipu pelkästään kaupungeista vaan myös parametreistä starting-temperature (sT), cooling-rato (cR) ja final-temperature (fT).
 Nämä arvot suhteessa kaupunkien määrään taas vaikuttavat saadun reitin "hyvyyteen". 

Koodin JavaDocissa on yksityiskohtaisempaa analyysiä aikavaativuuksista, mutta toistaiseksi tilavaativuusanalyysi on tekemättä. O(n*log_{cR}(fT/sT)). Algoritmi vaikuttaisi olevan tehokkuudeltaan ja tarkkuudeltaan erittäin hyvä, ja se soveltuu myös ei-metrisiin TSP-ongelmiin.

Tarkemmat analyysit (myös tietorakenteista) JavaDocissa.
