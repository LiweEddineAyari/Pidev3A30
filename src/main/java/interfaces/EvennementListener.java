package interfaces;


import entity.Evennement;

public interface EvennementListener {
    void onViewAvis(Evennement evennement);
    void onParticiper(Evennement evennement);
}
