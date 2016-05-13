#Käyttöohje

- Aja jar-tiedosto.

- Aukeavaan ikkunaan voit painela hiirellä pisteitä.

- Alhaalla näkyvillä painikkeilla saat laskettua ja piirrettyä Hamiltonin kierroksen, joka on mahdollisimman lyhyt, eli joka käy kaikissa pisteissä kerran ja palaa sitten takaisin lähtöpisteeseen.

- ClarkeWright on käytännössä hyödytön ja antaa sinulle täysin käyttökelvottoman reitin.

- Brute Force antaa sinulle tarkan reitin, mutta HUOM!!!! Älä syötä enempää kuin 14 kaupunkia BruteForceen, sillä koneesi menee jumiin, koska ongelma on NP-täydellinen ja BruteForcen aikavaativuus on O(n!). Vähän yli parikymmentä kaupunkia kestää yli miljoona vuotta.

- SimulatedAnnealingista on kaksi versiota, joissa algoritmi on sama, mutta toinen on animointi siten, että näet algoritmin reitinetsinnän. Etsintä perustuu edellistä huonompien reittien hyväksymistodennäköisyyksille, joihin  vaikuttavat uuden reitin pituus, sekä jatkuvasti vähenevä temperature-arvo. 

- Voit vaikuttaa siihen, mistä lämpötilasta algoritmi aloittaa, ja mihin se päättyy, sekä kuinka nopeasti lämpötila laskee. Lämpötilan vähenemiskerroin alphan tulee olla nollan ja yhden avoimella välillä, eikä ohjelma muuta arvoa, mikäli syöte on virheellinen. Lisäksi lähtölämpötilan tulle olla pienempi kuin päätymislämpötilan, ja kummankin on oltava yli nolla. Jos et syötä arvoja, on ne konfiguroitu valmiinanäkyvien arvojen mukaisesti.

- Simulated Annealing ei välttämättä anna lyhintä reittiä, mutta se antaa sopivilla parametreillä yleensä noin 102% reitin lyhimmästä.

- Jos saan NetBeansin toimimaan tulee ohjelmaan myös parhaan löydetyn reitin esiinantava nappi.
