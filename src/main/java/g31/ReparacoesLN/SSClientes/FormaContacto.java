package g31.ReparacoesLN.SSClientes;

import java.io.Serializable;
import java.util.Objects;

public class FormaContacto implements Serializable {

	private String email;
	private String numero;

    public FormaContacto(String em, String num) {
        this.email = em;
        this.numero = num;
    }

    public FormaContacto(FormaContacto fc) {
        this.email = fc.getEmail();
        this.numero = fc.getNumero();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public FormaContacto clone() {
        return new FormaContacto(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormaContacto that = (FormaContacto) o;
        return Objects.equals(email, that.email) && Objects.equals(numero, that.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, numero);
    }


}