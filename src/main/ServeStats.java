package main;

/**
 * This class represents the serving statistics for a player
 * We consider four important statistics:
 * First serve percentage = # first service attempts that land in bounds / # first service attempts
 * Ace rate = # of aces / # of first serves
 * Service winner rate = # of service winners / # first serves
 * Double fault rate = # of second serves missed / # second serve attempts
 *
 * If the serve is not an ace or a service winner, it's treated as a neutral shot to either zone 4 (if serving to the deuce side)
 * or zone 6 (if serving to the ad side)
 *
 */
public class ServeStats {
    private double firstServePct;
    private double aceRate;
    private double svcWinnerRate;
    private double dblFaultRate;

    public ServeStats(double firstServePct, double aceRate, double svcWinnerRate, double dblFaultRate) {
        this.firstServePct = firstServePct;
        this.aceRate = aceRate;
        this.svcWinnerRate = svcWinnerRate;
        this.dblFaultRate = dblFaultRate;
    }

    public double getFirstServePct() {
        return firstServePct;
    }

    public double getAceRate() {
        return aceRate;
    }

    public double getSvcWinnerRate() {
        return svcWinnerRate;
    }

    public double getDblFaultRate() {
        return dblFaultRate;
    }
}
