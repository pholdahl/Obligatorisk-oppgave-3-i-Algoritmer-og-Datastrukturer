package no.oslomet.cs.algdat.Oblig3;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

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

        private Node(T verdi, Node<T> forelder)  // konstruktør
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

    public SBinTre(Comparator<? super T> c)    // konstruktør
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
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");
        Node<T> p = rot;
        Node<T> q = null;
        int cmp = 0;

        while (p != null) {
            q = p;
            cmp = comp.compare(verdi, p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }
                                                                                // ute av while løkken her, p er nå lik null!
        p = new Node<>(verdi, null, null, q);                             // Husk på at her trengs flere referanser enn i kompendiet

        if(q == null) {
            rot = p;
        } else if(cmp < 0) {
            q.venstre = p;
        } else {
            q.høyre = p;
        }
        antall++;
        return true;
    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
        Node<T> n = q.høyre;                                                    // hvis ikke, så må vi hente ut q sin høyre, legger den i n
        while(n.venstre != null) {                                              // while løkke for å traversere ned til venstre
            n = n.venstre;                                                      // til vi finner den noden lengst til venstre
        }
        return n;                                                               // returnerer noden lengst til venstre
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

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
