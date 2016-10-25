import javax.ws.rs.*;

import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * Created by egothelf on 9/28/16.
 */
@Path("/FencingCaddieService")
public class FencingCaddieService {

    @GET
    @Path("/Fencers")
    @Produces("application/json")
    public String getFencers(@QueryParam("search") String search, @QueryParam("id") String idStr, @QueryParam("ids") String idList) {
        DBImpl db = new DBImpl();
        db.connect();
        FencerResults fencerResults = new FencerResults();

        System.out.println("Search="+search+",id="+idStr+",idList="+idList);
        if (idStr != null && idStr.length() > 0)
        {
            int id=0;
            try {
                id = new Integer(idStr);
            }
            catch (Exception e)
            {
                // Do Nothing - no results will be found
            }

            fencerResults.setId(id);
            fencerResults.setFencers(db.getFencers(id));
        }
        else if (idList != null && idList.length() > 0)
        {
            System.out.println(idList);
            int id;
            ArrayList<Integer> ids = new ArrayList<Integer>();
            String[] s = idList.split(",");
            if (s != null && s.length > 0)
            {
                for (int i=0; i<s.length; i++) {
                    try {
                        id = new Integer(s[i]);
                        ids.add(id);
                    } catch (Exception e) {
                        // Do Nothing - no results will be found
                    }
                }
            }
            fencerResults.setIds(ids);
            fencerResults.setFencers(db.getFencers(ids));
        }
        else
        {
            fencerResults.setQuerySearch(search);
            fencerResults.setFencers(db.getFencers(search));
        }

        if (fencerResults.getFencers() != null)
            fencerResults.setResultCount(fencerResults.getFencers().size());

        db.close();
        Gson gson = new Gson();
        return gson.toJson(fencerResults);
    }

    @GET
    @Path("/Events")
    @Produces("application/json")
    public String getEvents(@QueryParam("fencerid") int fencerId, @QueryParam("past") boolean includePast, @QueryParam("current") boolean includeCurrent, @QueryParam("future") boolean includeFuture) {
        DBImpl db = new DBImpl();
        db.connect();
        EventResults eventResults = new EventResults();
        eventResults.setQueryFencerId(fencerId);
        eventResults.setIncludeCurrent(includeCurrent);
        eventResults.setIncludeFuture(includeFuture);
        eventResults.setIncludePast(includePast);
        eventResults.setEvents(db.getEvents(fencerId, includePast, includeCurrent, includeFuture));
        if (eventResults.getEvents() != null)
            eventResults.setResultCount(eventResults.getEvents().size());
        System.out.print("fencerid=" + fencerId);
        db.close();
        Gson gson = new Gson();
        return gson.toJson(eventResults);
    }

}
