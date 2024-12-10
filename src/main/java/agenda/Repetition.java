package agenda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Repetition {

    private List<LocalDate> exceptions = new ArrayList<>();
    private Termination termination;

    public ChronoUnit getFrequency() {
        return myFrequency;
    }

    public List<LocalDate> getExceptions() {
        return exceptions;
    }

    /**
     * Stores the frequency of this repetition, one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     */
    private final ChronoUnit myFrequency;

    public Repetition(ChronoUnit myFrequency) {
        this.myFrequency = myFrequency;
    }

    /**
     * Les exceptions à la répétition
     * @param date un date à laquelle l'événement ne doit pas se répéter
     */
    public void addException(LocalDate date) {
        this.exceptions.add(date);
    }

    /**
     * La terminaison d'une répétition (optionnelle)
     * @param termination la terminaison de la répétition
     */
    public void setTermination(Termination termination) {

        this.termination = termination;
    }

    public LocalDate getTerminationDate() {
        return termination.terminationDateInclusive();
    }

    public long getNumberOfOccurrences() {
        return termination.numberOfOccurrences();
    }
}
