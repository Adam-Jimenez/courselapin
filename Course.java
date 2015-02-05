//this is a swag comment
public class Course{
    Lapin[] lapins;
    /**
    * Constructeur de la classe
    * @param pLongueur la longueur de la piste
    * @param pNbLapins le nombre de lapin qui seront places sur la piste
    */
    public Course(int pLongueur, int pNbLapins){
        //on generera de facon aleatoire les carottes. Chaque case de la piste a 10% 
        //de chance d'avoir une carotte
        //les lapins crees auront comme id leur index dans la piste de course
    }
    /**
    * Indique s'il y a une carotte aux coordonnees (pRangee, pColonne)
    * de la piste
    * @param pRangee  la rangee a laquelle on veut acceder 
    * @param pColonne la colonne a laquelle on veut acceder
    * @return boolean true s'il y a une carotte a la position donnee, false sinon
    */
    public boolean isCarotte(int pRangee, int pColonne){
        return false;
    }
    /**
    * Fonction d'acces au nombre de lapins dans la course
    * @return int le nombre de lapins
    */
    public int getNbLapins(){
        return 1;
    }
    /**
    * Fonction d'acces a la longueur de la piste de course
    * @return int la longueur de la piste 
    */
    public int getLongueur(){

        return 10;
    }
    /**
    * Fonction d'acces au nombre de lapin cumule par le lapin 
    * a l'index pIdxLapin dans la piste
    * @param pIdxLapin l'index du lapin
    * @return int le nombre de carottes accumulees par le lapin
    */
    public int getLapinNbCarotte(int pIdxLapin){
        return 0; 
    }

    /**
    * Fonction d'acces a la position d'un lapin donne
    * @param pIdxLapin l'index du lapin dans la piste
    * @return int la position du lapin
    */
   public int getPosition(int pIdxLapin){
        return -1;
    }	

    /**
    * Fonction permettant de faire avancer les lapins sur la piste. Cette fonction est responsable de:
    * 1. Faire avancer les lapins
    * 2. Donner le bon nombre de carottes a chacun d'eux. Ainsi, si un lapin avance de 3 cases, il 
    *    faudra calculer le nombre de carottes qu'il a cumule sur son passage et les enlever de la piste
    * 
    */
    public void Step(){

    }
    /**
    * Retourne une represention sous forme de chaine de caracteres de la piste de course. 
    * Vous pouvez simplement afficher chacun des lapins et le tableau de carottes pour voir les 
    * position des carottes
    * @return String la chaine de caractere representant la Course
    */
    public String toString(){
        return "";
    }
    
    /**
    * Fonction donnant le message final affiche par le GUI a la fin de la course
    * @return String le message a retourner: "Gagnants: <liste des id des gagnants en ordre croissant de position>"
    */
    public String getMessageFinal(){
        return "";
    }

    public boolean aGagnant(){
        return false;
    }
}
