import java.util.ArrayList;

/**
 * Created by egothelf on 10/11/16.
 */
public class EventResults {
    private int queryFencerId;
    private int resultCount = 0;
    private boolean includePast;
    private boolean includeCurrent;
    private boolean includeFuture;

    private ArrayList<Event> events;

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public int getQueryFencerId() {
        return queryFencerId;
    }

    public void setQueryFencerId(int queryFencerId) {
        this.queryFencerId = queryFencerId;
    }

    public boolean isIncludePast() {
        return includePast;
    }

    public void setIncludePast(boolean includePast) {
        this.includePast = includePast;
    }

    public boolean isIncludeCurrent() {
        return includeCurrent;
    }

    public void setIncludeCurrent(boolean includeCurrent) {
        this.includeCurrent = includeCurrent;
    }

    public boolean isIncludeFuture() {
        return includeFuture;
    }

    public void setIncludeFuture(boolean includeFuture) {
        this.includeFuture = includeFuture;
    }
}
