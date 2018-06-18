package t7;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class API {
    private HttpJSONService http = new HttpJSONService();
    
    private Map muhJSON(String url) {
        Map json = null;
        try {
            json = http.sendGet(url);
        } catch (Exception e) {
            System.out.println("Exceção: não foi possível conectar-se ao servidor" + 
                               "\nPor favor, cheque sua conexão com a internet ou" +
                               " tente novamente mais tarde!");
        }
        return json;
    }
}

class HttpJSONService {
    private final String USER_AGENT = "Mozilla/5.0";
    private JSONParsing engine = new JSONParsing();

    // HTTP GET request
    public Map sendGet(String url) throws Exception {     
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        System.out.println("\n'GET' request sent to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        StringBuffer response = new StringBuffer();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Print result
        // System.out.println(response.toString());

        // Parse JSON result
        JSONParsing engine = new JSONParsing();
        return engine.parseJSON(response.toString());
    } 
}

class JSONParsing {
    private ScriptEngine engine;

    public JSONParsing() {
        ScriptEngineManager sem = new ScriptEngineManager();
        this.engine = sem.getEngineByName("javascript");
    }

    public Map parseJSON(String json) throws IOException, ScriptException {
        String script = "Java.asJSONCompatible(" + json + ")";
        Object result = this.engine.eval(script);
        Map contents = (Map) result;
        return contents;
    }
}
