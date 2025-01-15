package com.literatura.libreriasgandhi.service;

public interface IConvertirDatos {

    <T> T convertirDatosJsonAJava(String json , Class<T> clase);

}
