# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Pål André Holdahl, S147098, s147098@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 så brukte jeg leggInn metoden fra kompendiet slik det er foreslått i oppgavebeskrivelsen, men så endret jeg 
i koden der jeg måtte legge inn flere referanser til høyre/venstre barn og forelder.

I oppgave 2 så endret jeg en inneholder(T verdi) metode fra kompendiet til å telle antallet av input verdi i en while løkke
og returnere dette antallet, i stedet for å returnere true/false.

Oppgave 3 sin førstePostorden(T verdi) minnet om en oppgave jeg allerede hadde gjort fra uke42 i fra kompendiet. Jeg endret den kodesnutten, som 
opprinnelig inneholdt en NoSuchElementException, til å i stedet returnere null hvis p var null. Det fungerte fint, deretter
begynte jeg å se på nestePostorden(T verdi). Jeg forstod fort at dette måtte han noe gjøre med p sin forelder, og tok utgangspunkt
i det når jeg fortsatte å kode metoden. Jeg prøvde å kode den slik at hvis p sin forelder sin høyre er p eller null,
da må vi befinne oss nederst til høyre på en gren, og kan da returnere forelderen, mens hvis ikke, altså hvis foreldrenoden til p
sin høyre eksisterer, så må vi traversere oss helt ned til venstre fra foreldrenoden sitt høyre barn. Jeg hadde tidligere
en versjon av denne metoden som passerte testene til oppgave 3, men som beskrevet i oppgave 6, likevel inneholdt feil. Det var under feilsøking
av oppgave 6 at jeg oppdaget at jeg i det siste tilfellet i nestePostorden metoden like gjerne kunne bruke den allerede
kodete førstePostorden metoden.

I oppgave 4 sin første postorden metoden gjenkjente jeg hvordan jeg måtte kode den ut i fra hva som allerede stod skrevet
av kode i toStringPostOrder metoden lengre opp i klassen. I stedet for å bruke en StringJoiner, tok jeg heller i bruk oppgave.
Deretter brukte jeg mer eller mindre direkte den rekursive postorden metoden jeg tidligere har kodet i kompendiet på 
den rekursive versjonen av postorden med oppgave.

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
