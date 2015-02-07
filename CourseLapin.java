import java.awt.image.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Classe graphique permettant l'affichage d'une course de Lapins fous animee.
 * Lancement du programme : java CourseLapin (longueur de la piste de course) (nombre de lapins)
 * Par defaut, la longueur = 100 et le nombre de lapins = 10
 * @author Melissa Jourdain
 */
public class CourseLapin extends JPanel implements Runnable{

    //la piste de course sur laquelle les tortues courent
    private Course mPiste;
    private final static Color ORANGE_CAROTTE = new Color(255,140,0);
    //le thread de la course
    private Thread mAnimator;

    //le delai (en milliseconde), entre chaque "step" de la course
    private final int DELAY = 1000;

    //le nombre de pas complété de la course
    //utilise pour controler les bugs possibles de la classe Course
    //Ex: si la course ne finit jamais...
    private int mNbStep;
    //indique si l'animation est toujours en cours. Quand la course est termine, l'image fige
    //et l'animation se termine.
    private boolean mAnimationCourse;

    //les differentes images affichees dans l'interface graphique
    //et leur dimension
    private Image mImgLapin; 
    private Image mImgCarotte;
    private int mLapinWidth, mLapinHeight;
    private int mCarotteWidth, mCarotteHeight;

    /**
     * constructeur de l'interface
     * @param pLength la longueur de la piste de course
     * @param pNbLapins le nombre de tortue qui coureront
     */
    public CourseLapin(int pLength, int pNbLapins){
        //creation de la piste et des tortues (a vous de faire cette fonction)
        mPiste = new Course(pLength, pNbLapins);

        // avant le depart, le nombre de step est negatif
        // on fait un decompte tant que ce nombre est negatif
        mNbStep = -4;
        mAnimationCourse = true;
        setBackground(Color.GREEN);

        //chargement des images a afficher dans l'interface
        ImageIcon iiLapin = new ImageIcon(this.getClass().getResource("img/lapinFou.png"));
        mLapinWidth = iiLapin.getIconWidth();
        mLapinHeight =iiLapin.getIconHeight();
        mImgLapin= iiLapin.getImage();
        ImageIcon iiCarotte = new ImageIcon(this.getClass().getResource("img/carrot.png"));
        mCarotteWidth= iiCarotte.getIconWidth();
        mCarotteHeight =iiCarotte.getIconHeight();
        mImgCarotte= iiCarotte.getImage();
        System.out.println("carotte: "+mCarotteWidth+" "+mCarotteHeight);
        System.out.println("lapin: "+mLapinWidth+" "+mLapinHeight);
       

        //permet un rendu fixe pour le fond de l'image
        //utile pour une execution plus rapide
        setDoubleBuffered(true);
    }

    /**
     * Fonction appelee implicitement par le toolkit java. 
     * Elle est redefini afin de s'assurer que le thread de course soit lance 
     * lors de la construction de la fenetre d'affichage de la course
     */
    public void addNotify(){ 
        super.addNotify();
        mAnimator = new Thread(this);
        mAnimator.start();
    }


    /**
     * Fonction executee par le thread de l'interface
     * Assure un fonctionnement synchronisée de la course et 
     * en charge de l'avancement des coureurs
     */
    public void run() {
        long beforeTime, timeDiff, sleep;
        //on fait un decompte avant le depart de la course
        //le thread doit s'arreter entre chaque chiffre du decompte pour des questions d'affichage
        for(int i = 0; i<4; i++){
            beforeTime = System.currentTimeMillis();
            repaint();
            timeDiff = System.currentTimeMillis()-beforeTime;
            mNbStep++;
            sleep = 1000-timeDiff;
            if(sleep<0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

        }
        //on lance la course
        beforeTime = System.currentTimeMillis();
        while (mAnimationCourse){
            if(mPiste.aGagnant())
                mAnimationCourse = false;
            else{
                mPiste.Step();
                mNbStep++;
                if(mNbStep > mPiste.getLongueur()*20){
                    System.err.println("***** Probleme au niveau de la fonction aGagnant de la classe mPiste");
                    System.exit(0);
                }

            }

            repaint();
            System.out.println(mPiste); //DEBUG

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }

    /**
     * Fonction qui dessine l'ecran de course
     * @param g l'objet Graphics a mettre a jour
     */
    public void paint(Graphics g){ 
        super.paint(g);
        /*
         * Definitions des positions, dimensions des objets graphiques 
         * a dessiner
         */
        //configuration de l'environnement de dessin
        Graphics2D g2d = (Graphics2D) g;
        Font font= new Font("font",Font.BOLD,26); 
        g2d.setFont(font);
        g2d.setColor(Color.red);
        g2d.setColor(Color.black);

        //si la course n'a pas commence, on affiche le decompte
        if(mNbStep <= 0)
            drawDecompte(g2d);
        //initialisation des parametres graphiques (calcule de la largeur des cases,etc...)
        int widthArriveeDepart= (int)(0.1*getWidth());
        int widthPiste = getWidth()-2*widthArriveeDepart;
        int heightCorridor= getHeight()/mPiste.getNbLapins();
        int widthCase = widthPiste/mPiste.getLongueur();

        drawBackground(g2d);
        drawCarotte(g2d,widthArriveeDepart,heightCorridor, widthCase);
        drawLapins(g2d,widthArriveeDepart,heightCorridor,widthCase);
        //si la course est termine, on affiche les gagnants
        if(!mAnimationCourse && mPiste.aGagnant()){
            g.drawString(mPiste.getMessageFinal(),getWidth()/2,getHeight()/2);
        }

        //assure le synchronisme avec le systeme d'affichage
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private void drawDecompte(Graphics2D g) {
        g.setColor(Color.BLACK);
        if(mNbStep <= 0) {
            if(mNbStep != 0)
                g.drawString(""+(-1*(mNbStep+1)), getWidth()/2, getHeight()/2);
            else                
                g.drawString("GO!!!!!!",getWidth()/2, getHeight()/2);
        }

    }

    private void drawBackground(Graphics2D g) {
        //position lignes arrivee et depart
        int widthArriveeDepart= (int)(0.1*getWidth());
        int widthPiste = getWidth()-2*widthArriveeDepart;

        //ajout du dessin des lignes de depart et d'arrivee
        g.setColor(Color.red);
        g.drawLine(getWidth()-widthArriveeDepart,0,getWidth()-widthArriveeDepart,getHeight());
        g.drawLine(widthArriveeDepart,0,widthArriveeDepart,getHeight());
        g.setColor(Color.black);

    }
    private void drawCarotte(Graphics2D g, int pDebutPiste, int pHeigthRangee, int pWidthCase){
      float scale = pHeigthRangee/(float)mCarotteHeight>0.5f?0.5f:pHeigthRangee/(float)mCarotteHeight; 
      int heightCarotte = (int)(scale*mCarotteHeight);
      int widthCarotte = (int)(scale*mCarotteWidth);
      for(int i = 0; i<mPiste.getNbLapins(); i++)
          for(int j = 0; j<mPiste.getLongueur(); j++){
            if(mPiste.isCarotte(i, j)){
                g.drawImage(mImgCarotte,pDebutPiste+j*pWidthCase,i*pHeigthRangee,widthCarotte,heightCarotte,this);
//            g.drawImage(mImgLapin, newPosLapin, i*pHeightRangee, widthLapin, heightLapin, this);

            }
          }
    }

    private void drawLapins(Graphics2D g, int pDebutPiste, int pHeightRangee, int pWidthCase) {

        //determination de la taille des lapins
        if(mPiste.getNbLapins() <=0){
          System.out.println("Probleme avec la fonction getNbLapins de la classe Course");
          System.exit(0);
        }
        float scale = pHeightRangee/(float)mLapinHeight>0.5f?0.5f: pHeightRangee/(float)mLapinHeight;
        
        int heightLapin= (int)(scale*mLapinHeight); 
        int widthLapin = (int)(scale*mLapinWidth); 

        //determination de la taille des unites d'avancement des lapins
        if(mPiste.getLongueur()<=0){
          System.out.println("Probleme avec la fonction getLongueur() de la classe Course");
          System.exit(0);
        }

        //on redessine les lapins a leurs nouvelles positions
        int nbLapins = mPiste.getNbLapins();
        int newPosLapin, prevPosLapin, curPosition;
      
        for(int i = 0; i<nbLapins;i++) {

            //affichage du numero du lapin au debut de la piste
            //si le lapin a une carotte, on affiche ses infos en orange
            if(mPiste.getLapinNbCarotte(i) > 0)
                g.setColor(ORANGE_CAROTTE);
            else
                g.setColor(Color.BLACK);
            g.drawString(i+":"+mPiste.getPosition(i),pWidthCase/2,i*pHeightRangee+pHeightRangee/2);
            //affichage de l'image de lapin
            newPosLapin = mPiste.getPosition(i);
            //si le lapin est en position de depart, on l'affiche avant la ligne de depart
            if(newPosLapin == -1)
                newPosLapin = pDebutPiste-widthLapin;
            else
                newPosLapin= mPiste.getPosition(i)*pWidthCase+pDebutPiste;
            //si le lapin a franchi la ligne d'arrivee et qu'il sort de la fenetre graphique,
            //on le garde dans les limites de la fenetre graphique
            if(newPosLapin> getWidth()-widthLapin)
                newPosLapin= getWidth()-widthLapin;
            //si le lapin a une carotte, on peint du orange autour de son image
            if(mPiste.getLapinNbCarotte(i)> 0){
                g.setColor(ORANGE_CAROTTE);
                g.fillRect(newPosLapin,i*pHeightRangee,widthLapin,heightLapin);
            }
            g.drawImage(mImgLapin, newPosLapin, i*pHeightRangee, widthLapin, heightLapin, this);
        }
    }

    /**
     * Le main: initialise les composantes graphiques et lance la course
     * @param args type String[] un tableau d'arguments donnes au lancement du programme
     */
    public static void main(String[] args){ 
        int longueur, nbLapins;
        if(args.length != 2){
            longueur = 10;
            nbLapins = 10;
        }
        else {
        longueur = Integer.parseInt(args[0]);
        nbLapins = Integer.parseInt(args[1]);
        }
        if(nbLapins < 0 || nbLapins > 30 ){
            System.out.println("Erreur: vous ne pouvez pas avoir une course de "+nbLapins+" lapins. Le nombre de lapins doit etre en 1 et 30 (inclusivement)");
            System.exit(0);
        }
        if(longueur< 10 || longueur >  400 ){
            System.out.println("Erreur: vous ne pouvez pas avoir une piste de longueur" +longueur+". La longueur doit etre entre 10 et 600  (inclusivement)");
            System.exit(0);
        }

        if(longueur <= 0){
            System.out.println("Le nombre de cases doit etre superieur ou egal a 1");
            System.exit(0);
        }

        //initialisation des composantes graphiques
        JFrame f = new JFrame("Course de Lapins fous!");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //ajout de la course au frame
        f.add(new CourseLapin(longueur, nbLapins));
        f.setSize(1024,1024);
        f.setVisible(true);
    }
} 
