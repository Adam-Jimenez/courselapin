public class Lapin{
    private int mCarottes;
    private int mPosition;
    private int mNumero;
    // besoin de numero pour constructeur
    public Lapin(int pNumero){
        mNumero = pNumero;
        mCarottes = 0;
        mPosition = 0;
    }
    public int avance(){
        int deplacement;
        if(mCarottes==0)
            deplacement = (int)(Math.random()*3);
        else {
            deplacement = (int)(1+(Math.random()*3));
            mCarottes--;
        }
        mPosition += deplacement;

        return deplacement;
    }
    public void donneCarotte(){
        mCarottes++;
    }
    public String toString(){
        return mNumero+":"+mPosition+":"+mCarottes;
    }
    public int getNbCarrote(){
        return mCarottes;
    }
    public void setPosition(int pPosition){
        mPosition=pPosition;
    }
    public int getPosition(){
        return mPosition;
    }
}
