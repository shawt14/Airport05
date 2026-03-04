package com.example.airport05;

import java.io.Serializable;

public class Voo implements Serializable {
    private String m_Origem;
    private String m_Destino;
    private String m_num_voo;
    private String m_hora_partida;
    private String m_hora_chegada_prevista;
    private String  m_hora_chegada_final;
    private String m_data;
    private String m_companhia;
    private String m_terminal;

    public Voo(String origem, String destino, String num_voo, String partida, String chegada_prevista, String chegada_final, String data, String companhia, String terminal){
        m_Origem = origem;
        m_Destino = destino;
        m_num_voo = num_voo;
        m_hora_partida = partida;
        m_hora_chegada_prevista = chegada_prevista;
        m_hora_chegada_final = chegada_final;
        m_data = data;
        m_companhia = companhia;
        m_terminal = terminal;
    }

    public String GetOrigem(){
        return m_Origem;
    }

    public String GetDestino(){
        return m_Destino;
    }

    public String GetNum_Voo(){
        return m_num_voo;
    }

    public String GetPartida(){
        return m_hora_partida;
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

    public String GetCompanhia(){
        return m_companhia;
    }

    public String GetTerminal(){
        return m_terminal;
    }
}
