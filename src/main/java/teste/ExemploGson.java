/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teste;

import com.google.gson.Gson;

/**
 *
 * @author Geison Quevedo
 */
public class ExemploGson {

    public static void main(String[] args) {
        Usuario usuario = new Usuario("João", 30);
        Gson gson = new Gson();
        String json = gson.toJson(usuario); // Converte para JSON
        System.out.println(json);

        usuario = gson.fromJson("{\"nome\":\"Maria\",\"idade\":25}", Usuario.class);
        System.out.println(usuario.nome);

    }
}
