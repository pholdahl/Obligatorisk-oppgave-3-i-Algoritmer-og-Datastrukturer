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
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");     // kan ikke være null
        Node<T> p = rot;                                                        // node p er lik rot til traversering
        Node<T> q = null;                                                       // node q er lik null er foreldrenoden til p
        int cmp = 0;                                                            // cmp variabel til å sammenlikne

        while (p != null) {                                                     // så lenge p ikke er null
            q = p;                                                              // lagrer p som neste forelder i q
            cmp = comp.compare(verdi, p.verdi);                                 // sammenlikner verdi input på p verdien
            p = cmp < 0 ? p.venstre : p.høyre;                                  // ut i fra sammenlikningen skal p traversere til venstre eller til høyre
        }
                                                                                // ute av while løkken her, p er nå lik null!
        p = new Node<>(verdi, null, null, q);                             // her er forskjellen fra koden i kompendiet, flere referanser!

        if(q == null) {                                                         // sjekk om q fortsatt er lik null
            rot = p;                                                            // i så fall lagres verdi som den første noden i treet, rot
        } else if(cmp < 0) {                                                    // ellers, hvis sammenlikningsvariabelen cmp er mindre enn 0
            q.venstre = p;                                                      // er p det venstre barnet til q
        } else {                                                                // ellers må det motsatte gjelde
            q.høyre = p;                                                        // og p er det høyre banret til q
        }
        antall++;                                                               // øker antall
        return true;                                                            // en vellykket innlegging!
    }

    // Oppgave 6: fjern()
    // 1. p har ingen barn(p er en bladnode)
    // 2. p har nøyaktig ett barn(venstre eller høyre barn)
    // 3. p har to barn
    // alle p har forelder!
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
        antall--;                                                               // reduserer antallet
        return true;
    }

    // Oppgave 6: fjernAlle()
    public int fjernAlle(T verdi) {
        int antall = -1;
        boolean fjernet = true;
        while(fjernet) {
            antall++;
            fjernet = fjern(verdi);
        }
        return antall;
    }

    // Oppgave 6: nullstill(), denne her er noe annet en fjern
    // nullstill som bruker første og neste postorden
    public void nullstill() {
        if(antall > 1){                         // hvis antallet er større enn 1
            Node<T> p = førstePostorden(rot);   // (barn) første bladnode
            while(!tom()) {
                Node<T> q = nestePostorden(p);  // (forelder) må ta vare på foreldrenoden, da vi nuller ut referansen
                if(p.forelder != null) {        // hvis p sin forelder ikke er null
                    p.forelder = null;          // sett foreldre referansen til null
                }
                p = q;                          // p er lik sin forelder
                antall--;                       // 1 mindre node i treet
            }
        }
        rot = null;                             // siste node, rot er lik 0
        antall = 0;                             // antall er nå 0;
    }

    // Oppgave 2: Skal returnere antall forekomster av en verdi i treet, husk at duplikater er tillatt
    public int antall(T verdi) {
        // Har tatt utgangspunkt i inneholder() metoden fra kompendiet, da den finner verdier og returnerer true, verdien eksisterer
        // Har gjort endringer slik at den kan telle opp antallet av den verdien som sendes inn
        if (verdi == null) {
            return 0;
        }

        int antall = 0;                                                         // initierer tellevariabel
        int cmp = 0;                                                            // sammenlikningsvariabel
        Node<T> p = rot;                                                        // traverserer fra roten

        while (p != null) {                                                     // så lenge p er ulik null
            cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) {
                p = p.venstre;
            }
            else {                                                              // hvis ikke mindre enn 0, er verdien større eller lik p.verdi
                if(cmp == 0) {                                                  // sjekker først om om de er like
                    antall++;                                                   // i så fall øker vi antallet
                }
                p = p.høyre;
            }
        }
        return antall;                                                          // returnerer antallet
    }


    // Oppgave 3: første postorden er den første noden når en traverserer som er en bladnode
    private static<T> Node<T> førstePostorden(Node<T> p) {
        if(p == null) {                                                         // hvis p er null, er input enten en nullverdi, eller foreldrenoden til roten i treet
            return null;                                                        // returner null
        }
        while(true) {                                                           // while løkke som brytes ved break eller return
            if(p.venstre != null) {                                             // hvis p sitt venstre barn ikke er null
                p = p.venstre;                                                  // da er p lik sitt venstre barn
            } else if(p.høyre != null) {                                        // hvis p sitt venstre barn er null, og p sitt høyre barn ikke er null
                p = p.høyre;                                                    // da er p lik sitt høyre barn
            } else {                                                            // hvis p sitt venstre og høyre barn er null, er det en bladnode
                return p;                                                       // den første noden i postorden returneres
            }
        }
    }

    // Oppgave 3: neste postorden må ha noe å gjøre med forelder til første postorden
    private static <T> Node<T> nestePostorden(Node<T> p) {                      // det som skal inn her som p er den første i postorden
        Node<T> q = p.forelder;                                                 // henter ut forelder til p og legger i q
        if(q == null) {                                                         // sjekker om q er null, i så fall er p rot
            return null;                                                        // så da returnerer vi null
        }
        if(q.høyre == p || q.høyre == null) {                                   // hvis ikke så sjekker vi om q sin høyre er lik p eller om q sin høyre er lik null
            return q;                                                           // i så fall skal vi returnere q
        }
        return førstePostorden(q.høyre);                                        // hvis ikke må vi finne q sin høyre sitt barn lengst til venstre
    }

    // Oppgave 4: postorden med oppgave
    public void postorden(Oppgave<? super T> oppgave) {
        if(tom()) {                                                         // hvis treet er tomt
            return;                                                         // returner, fordi da finnes ikke noder å gjøre oppgaver på
        }
        Node<T> p = førstePostorden(rot);                                   // finner første node i postorden
        while (p != null) {                                                 // så lenge p ikke er lik null
            oppgave.utførOppgave(p.verdi);                                  // utfør oppgave (den første gang er det den første i postorden)
            p = nestePostorden(p);                                          // bruker nestepostorden metoden ellers
        }                                                                   // tilslutt blir p lik null, og da bryter vi ut av while løkka
    }

    // Oppgave 4: postorden rekursivt med oppgave
    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if(p.venstre != null) {                                                 // hvis p sin venstre er ulik null
            postordenRecursive(p.venstre, oppgave);                             // send p sin venstre med oppgave rekursivt inn i metoden                                          // at p sitt venstre og p sitt høyre barn != null
        }                                                                       // foregår helt til p sin venstre er lik null
        if(p.høyre != null) {                                                   // hvis p sin høyre er ulik null
            postordenRecursive(p.høyre, oppgave);                               // send p sin høyre med oppgave rekursivt inn i metoden
        }                                                                       // foregår helt til p sin høyre er lik null
        oppgave.utførOppgave(p.verdi);                                          // hvis p sin venstre og p sin høyre er lik null
    }                                                                           // er det en bladnode, utfør oppgave!

    // Oppgave 4: postorden rekursivt med oppgave, bytta om så private metoden ligger over public metoden
    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);                                       // kaller den private metoden med rot og oppgave som input
    }

    // Oppgave 5: Skal gjøre om et binærtre til en ArrayList (må ikke bruke en stack, SKAL BRUKE EN KØ!)
    public ArrayList<T> serialize() {
        if (tom()) {                                                            // hvis binærtreet er tomt
            return null;                                                        // returner null;
        }
        ArrayDeque<Node<T>> kø = new ArrayDeque<>(antall);                      // ellers bruker jeg en ArrayDeque som kø for nodene
        ArrayList<T> a = new ArrayList<>(antall);                               // og lager en Arraylist a som skal returnere verdiene
        kø.add(rot);                                                            // legger til roten i køen
        while (!kø.isEmpty()) {                                                 // while løkke så lenge køen har innhold
            Node<T> p = kø.poll();                                              // tar ut den første i køen og lagrer i p
            a.add(p.verdi);                                                     // legger p sin verdi inn i a
            if (p.venstre != null) {                                            // sjekker om p sin venstre er ulik null
                kø.add(p.venstre);                                              // hvis den er det, så legger vi den inn i køen
            }                                                                   // hvis p sin venstre er null
            if (p.høyre != null) {                                              // sjekker vi om p sin høyre er ulik null
                kø.add(p.høyre);                                                // hvis den er det, så legger vi den inn i køen
            }                                                                   // og lik fortsetter vi helt til køen er tom
        }
        return a;                                                               // returnerer a med verdiene lagt inn i riktig rekkefølge
    }

    // Oppgave 5: Skal gjøre om en ArrayList til et binærtre
    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        SBinTre<K> tre = new SBinTre<>(c);                                      // lager et binærtre som skal returneres
        for(int i = 0; i < data.size(); i++) {                                  // bruker en for løkke til å hente ut fra arrayet
            tre.leggInn(data.get(i));                                           // legger verdiene inn i treet
        }
        return tre;                                                             // returnerer treet
    }


} // ObligSBinTre
