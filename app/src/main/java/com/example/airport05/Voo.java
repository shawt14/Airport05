package com.example.airport05;

import java.sql.Time;
import java.io.Serializable;

public class Voo implements Serializable {
    private String m_Origem;
    private String m_num_voo;
    private String m_hora_chegada_prevista;
    private String  m_hora_chegada_final;
    private String m_data;

    public Voo(String origem, String num_voo, String chegada_prevista, String chegada_final, String data){
        m_Origem = origem;
        m_num_voo = num_voo;
        m_hora_chegada_prevista = chegada_prevista;
        m_hora_chegada_final = chegada_final;
        m_data = data;
    }

    public String GetOrigem(){
        return m_Origem;
    }

    public String GetNum_Voo(){
        return m_num_voo;
    }

    public String GetChegada_Prevista(){
        return m_hora_chegada_prevista;
    }

    public String GetChegada_Final(){
        return m_hora_chegada_final;
    }
    public String GetData(){
        return m_data;
    }
}
