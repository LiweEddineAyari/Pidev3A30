package entity;

import java.sql.Date;

public class Paiment {
    int id ;
    int iduser;
    float montant;
    String cartname;
    String CartCode;
    private Date date;

    public Paiment(int id, int iduser, float montant, String cartname, String cartCode,Date date) {
        this.id = id;
        this.iduser = iduser;
        this.montant = montant;
        this.cartname = cartname;
        this.CartCode = cartCode;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getIduser() {
        return iduser;
    }

    public float getMontant() {
        return montant;
    }

    public String getCartname() {
        return cartname;
    }

    public String getCartCode() {
        return CartCode;
    }

    public Date getDate() {
        return date;
    }
}
