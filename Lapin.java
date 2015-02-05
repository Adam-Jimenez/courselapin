public class Lapin{
    private int mCarottes;
    private int mPosition;
    public Lapin(){
        mCarottes = 0;
        mPosition = 0;
    }
    public void avance(){
        int deplacement;
        if(mCarottes=0)
            deplacement = (int)(Math.random()*3);
        else {
            deplacement = (int)(1+(Math.random()*3));
            mCarottes--;
        }
        mPosition += deplacement;
    }
    public void donneCarotte(){
        mCarottes++;
    }
    public String toString(){
        return numeroLapin+":"+mPosition+":"+mCarottes;
    }
    public int getNbCarrote(){
        return mCarrotes;
    }
    public int getPosition(){
        return mPosition;
    }
}
