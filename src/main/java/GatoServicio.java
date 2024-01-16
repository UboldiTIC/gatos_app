import com.google.gson.Gson;
import com.squareup.okhttp.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class GatoServicio {
    public static void verGatos() throws IOException {
        //1. Vamos a traer los datos de la API.
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search")
                .get()
                .build();
        Response response = client.newCall(request).execute();

        String elJson = response.body().string();
        //cortar los corchetes que trae el json:
        elJson = elJson.substring(1,elJson.length());
        elJson = elJson.substring(0,elJson.length()-1);

        //crear un objeto de la clase Gson
        Gson gson = new Gson();
        Gato gatos = gson.fromJson(elJson, Gato.class);

        //redimensionar en caso de necesitar:
        Image image = null;
        try {
            URL url = new URL(gatos.getUrl());
            image = ImageIO.read(url);

            ImageIcon fondoGato = new ImageIcon(image);

            if (fondoGato.getIconWidth()>800) {
                //redimensionamos
                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
            }

            String menu = "Opciones: ";
                    /*"/n"
                    +"1. Ver otra imagen /n"
                    +"2. Favorito /n"
                    +"3. Volver /n";*/
            String[] botones = {"Ver otra imagen", "Favorito", "Volver"};
            String id_gato = gatos.getId();
            String opcion = (String) JOptionPane.showInputDialog(null,menu,id_gato,JOptionPane.INFORMATION_MESSAGE, fondoGato, botones,botones[0]);
            int seleccion = -1;
            //validamos la opción seleccionada por el usuario.
            for (int i=0;i<botones.length;i++){
                if (opcion.equals(botones[i])){
                    seleccion = i;
                }
            }

            switch (seleccion){
                case 0:
                    verGatos();
                    break;
                case 1:
                    favoritoGato(gatos);
                    break;
                default:
                    break;
            }

        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static void favoritoGato(Gato gato){
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n    \"image_id\":\""+gato.getId()+"\"\n}\n");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.getApiKey())
                    .build();
            Response response = client.newCall(request).execute();
        }catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void verFavoritos(String apiKey) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .get()
                .addHeader("Content-Type","application/json")
                .addHeader("x-api-key", apiKey)
                .build();
        Response response = client.newCall(request).execute();

        //Guardamos el string con la respuesta.
        String elJson = response.body().string();

        //Creamos el objeto Gson:
        Gson gson = new Gson();

        GatoFavorito[] gatosArray = gson.fromJson(elJson,GatoFavorito[].class);

        if (gatosArray.length > 0){
            int min = 1;
            int max = gatosArray.length;
            int aleatorio = (int) (Math.random() * ((max-min)-1)) +min;
            int indice = aleatorio - 1;

            GatoFavorito gato_favorito = gatosArray[indice];


        }
    }
}
