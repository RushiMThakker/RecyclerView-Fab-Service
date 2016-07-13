package Service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Astound Rushi on 13-07-2016.
 */
public class HttpCalls
{
    HttpResponse hr=null;
    DefaultHttpClient hc=new DefaultHttpClient();
    HttpEntity entity=null;
    HttpPost hp=new HttpPost("http://192.168.0.11/RecyclerView/index.php");
    String jsonResponse;

    public String getJSON()
    {
        try
        {
            hr = hc.execute(hp);
            entity = hr.getEntity();
            jsonResponse= EntityUtils.toString(entity);

        } catch (Exception e)
        {

        }
        return jsonResponse;
    }
}
