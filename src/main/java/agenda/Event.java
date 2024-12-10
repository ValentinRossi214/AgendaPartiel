package agenda;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class Event {

    /**
     * The myTitle of this event
     */
    private String myTitle;
    
    /**
     * The starting time of the event
     */
    private LocalDateTime myStart;

    /**
     * The durarion of the event 
     */
    private Duration myDuration;

    private Repetition myRepetition;


    /**
     * Constructs an event
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     */
    public Event(String title, LocalDateTime start, Duration duration) {
        this.myTitle = title;
        this.myStart = start;
        this.myDuration = duration;
    }

    public void setRepetition(ChronoUnit frequency) {
        myRepetition = new Repetition(frequency);
    }

    public void addException(LocalDate date) {
        myRepetition.addException(date);
    }

    public void setTermination(LocalDate terminationInclusive) {
        myRepetition.setTermination(new Termination(myStart.toLocalDate(), myRepetition.getFrequency(), terminationInclusive));
    }

    public void setTermination(long numberOfOccurrences) {
        myRepetition.setTermination(new Termination(myStart.toLocalDate(), myRepetition.getFrequency(), numberOfOccurrences));
    }

    public int getNumberOfOccurrences() {
        return (int) myRepetition.getNumberOfOccurrences();
    }

    public LocalDate getTerminationDate() {
        return myRepetition.getTerminationDate();
    }

    /**
     * Tests if an event occurs on a given day
     *
     * @param aDay the day to test
     * @return true if the event occurs on that day, false otherwise
     */
    public boolean isInDay(LocalDate aDay) {
        Repetition checkDay = new Repetition(ChronoUnit.DAYS);

        if(myRepetition == null)
            return myStart.toLocalDate().equals(aDay) || myStart.plusHours(myDuration.toHours()).toLocalDate().equals(aDay);
        else {
            if(myRepetition.getExceptions().contains(aDay) || checkDay.getFrequency().between(myStart.toLocalDate(), aDay) < 0) {
                return false;
            }

            return switch (myRepetition.getFrequency()) {
                case DAYS -> true;
                case WEEKS -> checkDay.getFrequency().between(myStart.toLocalDate(), aDay) / 7 == myRepetition.getFrequency().between(myStart.toLocalDate(), aDay);
                case MONTHS -> checkDay.getFrequency().between(myStart.toLocalDate(), aDay) / 30 == myRepetition.getFrequency().between(myStart.toLocalDate(), aDay);
                default -> false;
            };
        }
    }
   
    /**
     * @return the myTitle
     */
    public String getTitle() {
        return myTitle;
    }

    /**
     * @return the myStart
     */
    public LocalDateTime getStart() {
        return myStart;
    }


    /**
     * @return the myDuration
     */
    public Duration getDuration() {
        return myDuration;
    }

    @Override
    public String toString() {
        return "Event{title='%s', start=%s, duration=%s}".formatted(myTitle, myStart, myDuration);
    }
}
