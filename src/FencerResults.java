/**
 * Created by egothelf on 10/11/16.
 */
import java.util.ArrayList;

public class FencerResults {
    private String querySearch;
    private int id;
    private ArrayList<Integer> ids;
    private int resultCount = 0;
    private ArrayList<Fencer> fencers;

    public ArrayList<Fencer> getFencers() {
        return fencers;
    }

    public void setFencers(ArrayList<Fencer> fencers) {
        this.fencers = fencers;
    }

    public String getQuerySearch() {
        return querySearch;
    }

    public void setQuerySearch(String querySearch) {
        this.querySearch = querySearch;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }
}
