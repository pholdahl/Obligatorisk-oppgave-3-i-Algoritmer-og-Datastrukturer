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

I oppgave 5 så tenkte jeg fort at jeg måtte se litt tilbake til de oppgavene jeg tidligere hadde gjort i fra uke 41, og kø
og stack og sånt. Aller først misforstod jeg og trodde jeg skulle bruke en stack, men så gikk det opp for meg at det jo stod
i oppgaven at det skulle være en kø. Deretter tok jeg i bruk ArrayListe som kø, men fant etterhvert ut at det kanskje var mer 
riktig å bruke en ArrayDeque, spesielt i forbindelse med den delen av koden i serialize() som faktisk skal oppføre seg osm en kø.
Jeg fant også ut at jeg kunne legg inn antallet direkte, slik at hverken ArrayDeque eller ArrayList driver å utvider seg mens
metoden kjører. 
deserialize() metoden var mye enklere å kode, siden vi bare kunne ta verdiene direkte fra arrayet som går inni metoden og bruke 
tre objektets leggInn metode direkte i en for løkke. 

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

Warnings 8:
- 3 Non-ASCII characters in an identifier warnings på bruk av norske bokstaver.
- Private constructor 'Node(T, no.oslomet.cs.algdat.Oblig3.SBinTre.Node<T>)' is never used, og den blir aldri brukt!
- Private field 'endringer' is assigned but never accessed, og det stemmer da vi ikke har noen iteratorer
- Method 'inneholder(T)' is never used, som også stemmer da den aldri brukes
- Return value of the method is never used, som også stemmer, da den aldri brukes, kan jo bli brukt i en main metode!
- Argument 'p' might be null, tar høyde for det da løkken bare kjører hvis treet har mer enn 1 node
