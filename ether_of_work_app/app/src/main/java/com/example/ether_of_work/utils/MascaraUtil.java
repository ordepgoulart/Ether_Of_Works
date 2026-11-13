package com.example.ether_of_work.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

// formata o texto enquanto o usuário digita, no mesmo formato que o validar() exige
public final class MascaraUtil {
    public static final String CPF = "###.###.###-##";
    public static final String TELEFONE_FIXO = "(##) ####-####";
    public static final String TELEFONE_CELULAR = "(##) #####-####";

    private MascaraUtil() {
    }

    public static void cpf(EditText campo) {
        campo.addTextChangedListener(new Mascara(campo, false));
    }

    public static void telefone(EditText campo) {
        campo.addTextChangedListener(new Mascara(campo, true));
    }

    // encaixa os dígitos nas posições "#" da máscara
    public static String aplicar(String digitos, String mascara) {
        StringBuilder formatado = new StringBuilder();
        int posDigito = 0;
        int posMascara = 0;
        while (posMascara < mascara.length() && posDigito < digitos.length()) {
            char simbolo = mascara.charAt(posMascara);
            if (simbolo == '#') {
                formatado.append(digitos.charAt(posDigito));
                posDigito++;
            } else {
                formatado.append(simbolo);
            }
            posMascara++;
        }
        return formatado.toString();
    }

    private static class Mascara implements TextWatcher {
        private final EditText campo;
        private final boolean telefone;
        // evita que o setText chame a máscara de novo
        private boolean editando = false;

        Mascara(EditText campo, boolean telefone) {
            this.campo = campo;
            this.telefone = telefone;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!editando) {
                editando = true;
                String digitos = s.toString().replaceAll("\\D", "");
                String mascara = CPF;
                if (telefone) {
                    mascara = digitos.length() > 10 ? TELEFONE_CELULAR : TELEFONE_FIXO;
                }
                String formatado = aplicar(digitos, mascara);
                campo.setText(formatado);
                campo.setSelection(formatado.length());
                editando = false;
            }
        }
    }
}
