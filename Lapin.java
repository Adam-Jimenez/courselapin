public class Lapin{
    private int numeroLapin;
    private int nbCarottes;
    private int position;
    public Lapin(int pNumeroLapin){
        numeroLapin = pNumeroLapin;
        nbCarottes = 0;
        position = 0;
    }
    public void avance(){
        int deplacement;
        if(nbCarottes=0)
            deplacement = (int)(Math.random()*3);
        else {
            deplacement = (int)(1+(Math.random()*3));
            nbCarottes--;
        }
        position += deplacement;
    }
    public void donneCarotte(){
        nbCarottes++;
    }
    public String toString(){
        return numeroLapin+":"+position+":"+nbCarottes;
    }

