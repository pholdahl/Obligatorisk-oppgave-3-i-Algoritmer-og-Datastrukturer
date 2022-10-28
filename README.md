# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Pål André Holdahl, S147098, s147098@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 så brukte jeg leggInn metoden fra kompendiet slik det er foreslått i oppgavebeskrivelsen, men så endret jeg 
i koden der jeg måtte legge inn flere referanser til høyre/venstre barn og forelder.

I oppgave 2 så endret jeg en inneholder(T verdi) metode fra kompendiet til å telle antallet av input verdi i en while løkke
og returnere dette antallet, i stedet for å returnere true/false.

I oppgave 3 så...

I oppgave 4 så...

I oppgave 5 så...

I oppgave 6 så tok jeg utgangspunkt i fjern(T verdi) metoden fra kompendiet, men måtte finne ut av hvordan jeg skulle gjøre det
med foreldrereferansen. Etter mye om og men, og tegning av trær, og oppskriving av referanser for hånd, og testing med
egen kode i main klarte jeg sakte men sikkert å eliminere meg fram til at if(b != null) så må b.forelder = q. Det var ikke lett!
fjernAlle(T verdi) looper bare fjern(T verdi) til alle verdiene av den som er input er borte fra treet. Den begynner med antall
på -1, da while løkka kjører minst 1 gang, og øker antall til 0.
nullstill() metoden tar i bruk førstePostorden og nestePostorden metodene, den kjører i gang loop så lenge det er mer enn 1 node
i treet, hvis antallet er 1, så håndterer den den siste noden for seg selv etter loopen.

Etter å ha klødd meg i hodet en del fordi løsningen min ikke passerte testene, fant jeg ut at nestePostorden metoden min 
var feilkodet på en slik måte at det ikke slo ut på testene til oppgave 3, men gjorde at jeg konstant fikk feil når 
toStringPostOrder() ble kjørt i testene til oppgave 6. Etter å ha fikset opp i denne feilen, så løste hele floken seg.

Warnings:
