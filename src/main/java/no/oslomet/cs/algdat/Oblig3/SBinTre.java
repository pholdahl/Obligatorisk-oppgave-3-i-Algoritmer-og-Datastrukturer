package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)     // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)         // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    // Oppgave 1: Ta utgangspunkt i Programkode 5.2.3 fra kompendiet
    public boolean leggInn(T verdi) {                                           // Tar utgangspunkt i koden fra kompendiet, tar inn en verdi
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");      // kan ikke være null
        Node<T> p = rot;                                                        // node p er lik rot til traversering
        Node<T> q = null;                                                       // node q er lik null er foreldrenoden til p
        int cmp = 0;                                                            // cmp variabel til å sammenlikne

        while (p != null) {                                                     // så lenge p ikke er null
            q = p;                                                              // lagrer p som neste forelder i q
            cmp = comp.compare(verdi, p.verdi);                                 // sammenlikner verdi input på p verdien
            p = cmp < 0 ? p.venstre : p.høyre;                                  // ut i fra sammenlikningen skal p traversere til venstre eller til høyre
        }
                                                                                // ute av while løkken her, p er nå lik null!
        p = new Node<>(verdi, null, null, q);                              // her er forskjellen fra koden i kompendiet, flere referanser!

        if(q == null) {                                                         // sjekk om q fortsatt er lik null
            rot = p;                                                            // i så fall lagres verdi som den første noden i treet, rot
        } else if(cmp < 0) {                                                    // ellers, hvis sammenlikningsvariabelen cmp er mindre enn 0
            q.venstre = p;                                                      // er p det venstre barnet til q
        } else {                                                                // ellers må det motsatte gjelde
            q.høyre = p;                                                        // og p er det høyre banret til q
        }
        endringer++;
        antall++;                                                               // øker antall
        return true;                                                            // en vellykket innlegging!
    }

    /**
     * Oppgave 6: fjern(T verdi)
     * fjerner input verdi fra treet
     * vi har tre ulike muligheter:
     * 1. p har ingen barn(p er en bladnode)
     * 2. p har nøyaktig ett barn(venstre eller høyre barn)
     * 3. p har to barn
     */
    public boolean fjern(T verdi) {                                             // tar utgangspunkt i kode fra kompendiet, tar inn verdien vi vil fjerne
        if (verdi == null) {
            return false;                                                       // treet har ingen nullverdier
        }
        Node<T> p = rot;                                                        // en p-node til traversering, vi starter i rot
        Node<T> q = null;                                                       // q skal være forelder til p
        while (p != null) {                                                     // leter etter verdi
            int cmp = comp.compare(verdi,p.verdi);                              // sammenlikner
            if (cmp < 0) {                                                      // hvis sammenlikningsvariabelen cmp er mindre enn 0
                q = p;                                                          // er q forelder til p
                p = p.venstre;                                                  // p går til venstre
            } else if (cmp > 0) {                                               // hvis sammenlikningsvariabelen cmp er større enn 0
                q = p;                                                          // er q forelder til p
                p = p.høyre;                                                    // p går til høyre
            } else break;                                                       // den søkte verdien ligger i p
        }
        if (p == null) {                                                        // hvis traverseringsvariabelen p er lik null
            return false;                                                       // så eksisterer ikke verdien vi vil fjerne i treet
        }
        if (p.venstre == null || p.høyre == null) {                             // Tilfelle 1) og 2) -> venstre eller høyre bladnode, eller 1 høyre eller venstre barn
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;                // b for barn
            if(b != null) {                                                     // her er den store forskjellen i koden fra kompendiet! (som ga meg mye hodepine!)
                b.forelder = q;                                                 // hvis b er ulik null, er q foreldrenoden til b
            }
            if (p == rot) {                                                     // hvis p er lik roten av treet
                rot = b;                                                        // så er b den første noden, roten
            } else if (p == q.venstre) {                                        // ellers, hvis p er lik foreldrenodens venstre barn
                q.venstre = b;                                                  // så er b venstrebarnet til q
            } else {                                                            // hvis ikke så er det motsatt
                q.høyre = b;                                                    // b er høyrebarnet til b
            }
        } else {                                                                // Tilfelle 3) hvis p har to barn
            Node<T> s = p;                                                      // s er lik p
            Node<T> r = p.høyre;                                                // finner neste i inorden r = p sitt høyre barn
            while (r.venstre != null) {                                         // så lenge r sitt venstrebarn ikke er null
                s = r;                                                          // s er forelder til r
                r = r.venstre;                                                  // r er lik r sitt venstre barn
            }
            p.verdi = r.verdi;                                                  // p = r = 4 kopierer verdien i r til p, fordi verdien til høyre for p er større enn verdien til venstre for p
            if (s != p) {                                                       // hvis s er ulik p
                s.venstre = r.høyre;                                            // er s sitt venstre barn lik r sitt høyre barn
            }
            else {
                s.høyre = r.høyre;                                              // ellers er s sitt høyre barn lik r sitt høyre barn
            }
        }
        endringer++;
        antall--;                                                               // reduserer antallet
        return true;
    }

    /**
     * Oppgave 6: fjernAlle(T verdi)
     * bruker fjern(T verdi), men looper til den har fjernet alle
     * returnerer antallet verdier som har blitt fjernet fra treet
     */
    // Oppgave 6: fjernAlle()
    public int fjernAlle(T verdi) {                                             // tar inn verdien vi vil fjerne alle av
        int antall = -1;                                                        // antall er -1 fordi while løkka kjører i hvert fall 1 gang og da blir antall minimum 0
        boolean fjernet = true;                                                 // fjernet er true, men blir false i løkka hvis verdien ikke finnes, eller
        while(fjernet) {                                                        // while løkke
            antall++;                                                           // øker antall
            fjernet = fjern(verdi);                                             // oppdaterer fjernet variabelen og fjerner vedi
        }
        return antall;                                                          // returnerer antallet
    }

    /**
     * Oppgave 6: nullstill()
     * en nullstillmetode som bruker første- og nestePostorden()
     */
    public void nullstill() {
        if(antall > 1){                                                         // hvis antallet er større enn 1
            Node<T> p = førstePostorden(rot);                                   // (barn) første bladnode
            while(!tom()) {                                                     // så lenge treet ikke er tomt
                Node<T> q = nestePostorden(p);                                  // (forelder) må ta vare på foreldrenoden, da vi nuller ut referansen
                if(p.forelder != null) {                                        // hvis p sin forelder ikke er null
                    p.forelder = null;                                          // sett foreldre referansen til null
                }
                p = q;                                                          // p er lik sin forelder q
                antall--;                                                       // 1 mindre node i treet
            }
        }
        rot = null;                                                             // siste node, rot er lik null
        endringer = 0;
        antall = 0;                                                             // antall er nå 0;
    }

    /**
     * Oppgave 2: antall(T verdi)
     * denne returnerer antallet forekomster av en input verdi i treet
     * tar hensyn til at det er lov med duplikater i treet
     * tar utgangspunkt i en inneholder(T verdi) metode fra kompendiet,
     * har gjort endringer slik at den teller opp, i stedenfor å returnere true/false
     */
    public int antall(T verdi) {                                                // tar inn den verdien en vil finne antallet av
        if (verdi == null) {                                                    // hvis verdien er null
            return 0;                                                           // returneres null, da treet ikke godtar null verdier
        }
        int antall = 0;                                                         // initierer tellevariabel
        int cmp;                                                            // sammenlikningsvariabel
        Node<T> p = rot;                                                        // p node for å traversere fra roten

        while (p != null) {                                                     // så lenge p er ulik null
            cmp = comp.compare(verdi, p.verdi);                                 // sammenlikner inputverdi med p sin verdi
            if (cmp < 0) {                                                      // hvis sammenliknigsvariabelen cmp er mindre enn 0
                p = p.venstre;                                                  // går p ned til venstre i treet
            }
            else {                                                              // hvis ikke er verdien større enn eller lik 0
                if(cmp == 0) {                                                  // sjekker først om om de er like
                    antall++;                                                   // i så fall øker vi antallet
                }
                p = p.høyre;                                                    // hvis ikke går p nedover mot høyre i treet
            }                                                                   // og looper gjennom inntil p blir null
        }
        return antall;                                                          // returnerer antallet
    }

    /**
     * Oppgave 3: førstePostorden(Node<T> p)
     * førstePostorden er den første noden i treet med hensyn på postorden traversering
     * der det ikke finnes venstre eller høyrebarn, altså den første bladnoden helt til venstre
     */
    private static<T> Node<T> førstePostorden(Node<T> p) {                      // tar inn en node p
        if(p == null) {                                                         // hvis p er null, er input enten en nullverdi, eller foreldrenoden til roten i treet
            return null;                                                        // returner null
        }
        while(true) {                                                           // while løkke som brytes return
            if(p.venstre != null) {                                             // hvis p sitt venstre barn ikke er null
                p = p.venstre;                                                  // da går p ned til sitt venstre barn
            } else if(p.høyre != null) {                                        // hvis p sitt venstre barn er null, og p sitt høyre barn ikke er null
                p = p.høyre;                                                    // da går p ned til sitt høyre barn
            } else {                                                            // ellers så må p sitt venstre og høyre barn være null, og da er det en bladnode
                return p;                                                       // den første noden i postorden returneres
            }
        }
    }

    /**
     * Oppgave 3: nestePostorden(Node<T> p)
     * nestePostorden henter den neste i rekke av en postorden traversering
     * gitt at input er den første eller den neste i postorden
     */
    private static <T> Node<T> nestePostorden(Node<T> p) {                      // det som skal inn her som p er den første eller neste i postorden
        Node<T> q = p.forelder;                                                 // henter ut forelder til p og legger i q
        if(q == null) {                                                         // sjekker om q er null, i så fall må p være rot
            return null;                                                        // så da returnerer vi null
        }
        if(q.høyre == p || q.høyre == null) {                                   // hvis ikke så sjekker vi om q sin høyre er lik p eller om q sin høyre er lik null
            return q;                                                           // hvis det gjelder så skal vi returnere forelder til p, altså q
        }
        return førstePostorden(q.høyre);                                        // hvis ikke så må vi finne q sitt høyre barns sitt venstre barn lengst ned til venstre
    }

    /**
     * Oppgave 4: postorden(Oppgave<? super T> oppgave)
     * en postorden metode som gjennomfører egendefinerte oppgaver på nodene
     * i en postorden traversering.
     * Tar i bruk førstePostorden() og nestePostorden() fra oppgave 3.
     */
    // Oppgave 4: postorden med oppgave
    public void postorden(Oppgave<? super T> oppgave) {                         // tar inn en oppgave
        if(tom()) {                                                             // hvis treet er tomt
            return;                                                             // returnerer, fordi da finnes ikke noder å gjøre oppgaver på
        }
        Node<T> p = førstePostorden(rot);                                       // finner første node i postorden
        while (p != null) {                                                     // så lenge p ikke er lik null
            oppgave.utførOppgave(p.verdi);                                      // utfør oppgave (den første gang er det den første i postorden)
            p = nestePostorden(p);                                              // bruker nestepostorden metoden ellers
        }                                                                       // tilslutt blir p lik null, og da bryter vi ut av while løkka
    }

    /**
     * Oppgave 4: postordenRecursive(Node<T> p, Oppgave<? super T> oppgave)
     * en privat rekursiv postorden metode som gjennomfører egendefinerte oppgaver på nodene
     * i en postorden traversering. Kalles av en public postorden metode.
     */
    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {    // tar inn en node og en oppgave
        if(p.venstre != null) {                                                 // hvis p sin venstre er ulik null
            postordenRecursive(p.venstre, oppgave);                             // send p sin venstre med oppgave rekursivt inn i metoden                                          // at p sitt venstre og p sitt høyre barn != null
        }                                                                       // foregår helt til p sin venstre er lik null
        if(p.høyre != null) {                                                   // hvis p sin høyre er ulik null
            postordenRecursive(p.høyre, oppgave);                               // send p sin høyre med oppgave rekursivt inn i metoden
        }                                                                       // foregår helt til p sin høyre er lik null
        oppgave.utførOppgave(p.verdi);                                          // hvis p sin venstre og p sin høyre er lik null
    }                                                                           // er det en bladnode, utfør oppgave!

    /**
     * Oppgave 4: postordenRecursive(Node<T> p, Oppgave<? super T> oppgave)
     * denne metoden kaller på den private postordenRecursive metoden
     */
    public void postordenRecursive(Oppgave<? super T> oppgave) {                // tar inn en oppgave
        if(!tom()){                                                             // så lenge treet ikke er tomt
            postordenRecursive(rot, oppgave);                                   // kaller den private metoden med rot og oppgave som input
        }
    }

    /**
     * Oppgave 5: serialize()
     * en metode som lagrer verdiene fra et binærtre i et array og returner
     */
    public ArrayList<T> serialize() {                                           // tar ikke inputs
        if (tom()) {                                                            // hvis binærtreet er tomt
            return null;                                                        // returner null;
        }
        ArrayDeque<Node<T>> kø = new ArrayDeque<>(antall);                      // ellers bruker jeg en ArrayDeque som kø for nodene
        ArrayList<T> a = new ArrayList<>(antall);                               // og lager en ArrayList a som skal returnere verdiene
        kø.add(rot);                                                            // legger til roten i køen
        while (!kø.isEmpty()) {                                                 // while løkke så lenge køen inneholder noder
            Node<T> p = kø.poll();                                              // tar ut den første i køen og lagrer i p
            a.add(p.verdi);                                                     // legger p sin verdi inn i a
            if (p.venstre != null) {                                            // sjekker om p sin venstre er ulik null
                kø.add(p.venstre);                                              // hvis den er det, så legger vi den inn i køen
            }                                                                   // hvis p sin venstre er null
            if (p.høyre != null) {                                              // sjekker vi om p sin høyre er ulik null
                kø.add(p.høyre);                                                // hvis den er det, så legger vi den inn i køen
            }                                                                   // og slik fortsetter vi helt til køen er tom
        }
        return a;                                                               // returnerer a med verdiene lagt inn i riktig rekkefølge
    }

    /**
     * Oppgave 5: deserialize(ArrayList<K> data, Comparator<? super K> c)
     * en metode som henter verdier fra et array og returnerer et SBinTre
     */
    static <T> SBinTre<T> deserialize(ArrayList<T> data,                        // tar inn et array, og en comparator
                                      Comparator<? super T> c) {
        SBinTre<T> tre = new SBinTre<>(c);                                      // lager et binærtre som skal returneres
        for (T d : data) {                                                      // bruker en for løkke til å hente ut fra arrayet
            tre.leggInn(d);                                                     // legger verdiene inn i treet
        }
        return tre;                                                             // returnerer treet
    }


} // ObligSBinTre
