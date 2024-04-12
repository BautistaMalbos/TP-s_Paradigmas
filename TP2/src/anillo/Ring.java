package anillo;

import java.util.ArrayList;
import java.util.List;

public class Ring {


    public Ring(){
        this.listaDeObjetos = new ArrayList<>();
        this.posicion = 0;
    }

    public List<Object> listaDeObjetos;
    public int posicion = 0;

    public Ring next() {
        if (listaDeObjetos.isEmpty()) {
            throw new RuntimeException();
        } else {
            posicion = (posicion + 1) % listaDeObjetos.size();
            return this;
        }
    }

    public Object current() {
        if (listaDeObjetos.isEmpty()) {
            throw new RuntimeException();
        } else {
            return listaDeObjetos.get(posicion);
        }
    }

    public Ring add(Object cargo) {
        listaDeObjetos.add(posicion, cargo);
        return this;
    }


    public Ring remove() {
        listaDeObjetos.remove(posicion);
        if (listaDeObjetos.size() != 0){
            posicion = posicion % listaDeObjetos.size();
        }
        return this;
    }

}