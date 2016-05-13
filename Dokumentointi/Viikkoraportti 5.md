##Viikkoraportti

- Vielä vaan paljon testausta ja dokumentointia. 

-Nyt myös tilavaativuusanalyysiä, jossa törmäsin BruteForcessa suureen epäkohtaan. Olin käyttänyt array.clone -metodia, mikä lisäsi roskaa muistiin, ja jos Java ei siivoaisi niitä ajoissa kasvaisi tilavaativuuskin O(n!):ään. Tein siihen kohtaan manuaalisen arvojenkopioinnin, joka pitää tilavaativuuden O(n*n):ssä (joka tulee routehandlerin etäisyysmatriisista. Sinänsä BruteForcelleni riittäisi O(n), mikä tulisi rengaslistasta ja reitin esitystavoista).

- Lisäsin käyttöliittymään SimulatedAnnealingin parametrisyötteet.

- SimulatedAnnealingiin voisi tehdä vielä selkeän optioinnin, sillä swapin yhteydessä on turha laskea koko reittiä uudestaan, vaan riittäisi laskea vaihdettujen pätkien erotus summattuna vanhaan arvoon.

- Ehkä 20h kokonaisuudessaan.

- Iso ongelma minulla on siinä, että jostain syystä koulun koneella NetBeans ei löydä FRamen keskeisimpiä metodeja, minkä takia en pysty käynnistämään ohjelmaa, ja oma läppärini on poissa käytöstä sunnuntai-iltaan saakka. Toivottavasti pääsen vielä ohjelmoimaan.
