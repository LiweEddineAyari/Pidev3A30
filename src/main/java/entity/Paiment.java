package entity;

public class Paiment {
    int id ;
    int iduser;
    float montant;
    String cartname;
    String CartCode;

    public Paiment(int id, int iduser, float montant, String cartname, String cartCode) {
        this.id = id;
        this.iduser = iduser;
        this.montant = montant;
        this.cartname = cartname;
        this.CartCode = cartCode;
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
}
