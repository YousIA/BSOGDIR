/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bsogdir;

/**
 *
 * @author mobntic
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
           Format fo = new Format();
		//fo.lect_fich();
          //   fo.init_struct();
               fo.read_collection();
               fo.affich_doc();
                        //fo.random_dataset();

               Kmeans km=new Kmeans();
              // Code_Ml_kmeans cmlk = new Code_Ml_kmeans();
               long startTime = System.currentTimeMillis();

                km.Kmeans_function();
                km.Display_Kmeans_results();
                FIM f=new FIM();
                f.AllFIM();
               //cmlk .MLKmeans_function();

                long endTime = System.currentTimeMillis();
                long diff=endTime-startTime;

          //      Ml_Kmeans mlvl=new Ml_Kmeans();
      //    new Ml_Kmeans();

                //System.out.println("sat processing");
              //BSOguidedKmeans ff=new BSOguidedKmeans();
             // ff.process();
              //BSOguidedKmeans1 fff=new BSOguidedKmeans1();
               //fff.process();
               //System.out.println(diff);
                System.out.println("***********begin BSO**************");
              BSO bso = new BSO();
	        bso.process();
                System.out.println("***********end BSO**************");
              System.out.println("***********begin BSOGDKmeans1**************");
                 BSOguidedKmeans bsogd = new BSOguidedKmeans();
	        //bsogd.ProbabilityComputing();
                bsogd.ProbabilityComputing();
                bsogd.process();
                 System.out.println("***********end BSOGDKmeans1**************");

                // GA a =new GA();
              //a.crossover();
              // for (BSO.k = 1; BSO.k <= 20; BSO.k++) {

		// for (BSO.filp= 1;BSO.filp <= 20; BSO.filp ++) {
		// s = 0;

			//BSO f = new BSO();
			//f.process();



		// }

		// }

		 //Taboue t= new Taboue();
		 //t.process();
    }

}
