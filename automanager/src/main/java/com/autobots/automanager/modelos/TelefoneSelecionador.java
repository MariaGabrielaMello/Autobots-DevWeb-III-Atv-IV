package com.autobots.automanager.modelos;

import com.autobots.automanager.entidades.Telefone;
import org.springframework.stereotype.Component;


@Component
public class TelefoneSelecionador {

        public Telefone selecionar(Telefone[] telefones, long id) {
            Telefone telefone = null;
            for (Telefone t : telefones) {
                if (t.getId() == id) {
                    telefone = t;
                    break;
                }
            }
            return telefone;
        }
}
