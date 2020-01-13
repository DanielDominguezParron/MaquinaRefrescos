package accesoDatos;

import java.io.IOException;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


/**
 * Created by H3ku on 23/11/15.
 */
public class ApiRequests {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client;

    public ApiRequests(){
        client = new OkHttpClient();
    }

    /**
     * Metodo usado para hacer peticiones GET.
     * @param url URL a la que realizar la peticion.
     * @return Cuerpo de la respuesta.
     * @throws IOException
     */
    public String getRequest(String url) throws IOException {
        Request request= new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    /**
     * Metodo usado para hacer peticiones json POST.
     * @param url URL a la que realizar la peticion.
     * @param json Contenido de la request en JSON.
     * @return Cuerpo de la respuesta.
     * @throws IOException
     */
    public String postRequest(String url, String json) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    /**
     * Metodo usado para hacer una peticion POST mandando el json por un parametro llamado json
     * @param url URL a la que realizar la peticion.
     * @param json Contenido del parametro json
     * @return Cuerpo de la respuesta.
     * @throws IOException
     */
    public String postRequestWithParams(String url, String json) throws IOException {
		RequestBody formBody = new FormEncodingBuilder()
                .add("json", json)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }
}
