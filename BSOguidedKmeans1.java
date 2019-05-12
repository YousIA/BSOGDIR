/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bsogdir;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author W7
 */
import java.util.*;

public class BSOguidedKmeans1 {

// int iMax = 10000;
	int imax = 30;
	Solution[] dance = new Solution[BSO.k];
	int filp = 1;
	Solution Sref;

	public void process() {
		// for (int imax = 1; imax <= 100; imax++) {
		long moy_temps = 0;
		float moy_taux = 0;
                float best_results=0;
		for (int m = 0; m < 100; m++) {
			long startTime = System.currentTimeMillis();
			ArrayList<Solution> liste_taboue = new ArrayList<Solution>();

			// System.out.println(" " + Format.nbr_var + " " +
			// Format.nbr_clause);
			Create_Sref();
			int nbrItera = 0;
			Solution best_d = new Solution();
                         Solution best_g=new Solution();
                               best_g=Sref;
			while (nbrItera < imax) { // imax
				liste_taboue.add(Sref);

				Determination_Region1(Sref);
				for (int i = 0; i < BSO.k; i++) {
					Recherche_local_Region3(i);
				}


                                /*for (int j=0;j< Format.nbr_var; j++)
                                   {
                                    int ind=Kmeans.var[j];
                                    best_d.sol[j]=dance[ind].sol[j];
                                   }
                                evaluation(best_d, Format.t_c);
				*/
                               best_d = Best_Dance();
                               if (best_g.cost < best_d.cost){best_g=best_d;}
                      /*         if (!liste_taboue.contains(best_d)) {
					Sref = best_d;
				} else {
					Create_Sref();
				}

                       */
				nbrItera++;
			}
                           //    Local_search(best_g);
			long endTime = System.currentTimeMillis();
                         if (best_results<best_g.cost)
                        {best_results=best_g.cost;}
			moy_taux = moy_taux + best_g.cost;
			moy_temps = moy_temps + (endTime - startTime);
		}
		float taux=(float) moy_taux /100;
		taux=taux/Format.nbr_doc;
		float best_taux=(float) best_results/Format.nbr_doc;
                best_taux=best_taux*100;
		taux=taux*100;
		 moy_temps = moy_temps / 100;
		System.out.println(" BSOGD2  " + taux+ "   " + moy_temps+ "  "+best_taux);
		// Main1.s = Main1.s + best_d.cost;
		// System.out.println(" " + imax + " " + best_d.cost);

		// Main1.s = Main1.s / 10;
		// System.out.println(" " + filp + " " + Main1.s);
		// frg
		// }

	}

	public void Create_Sref() {
		Sref = new Solution();
		Random r = new Random();
		for (int i = 1; i <=Format.nbr_ter; i++) {
			Sref.sol[i] = r.nextInt(2);
                  //  Sref.sol[i] =1;
		}
		evaluation(Sref, Format.Doc);
	}

	public void evaluation(Solution solution, int[][] clause) {
		int evaluation_sol = 0;
		for (int i = 0; i < Format.nbr_doc; i++) {

			for (int j = 1; j < 4; j++) {

				int numclau = clause[i][j];
				if ((solution.sol[Math.abs(numclau)] == 1 && numclau > 0)
						|| (solution.sol[Math.abs(numclau)] == 0 && numclau < 0)) {
					evaluation_sol++;
					j = 4;
				}
			}
		}
		solution.cost = evaluation_sol;
	}
        /**********************************************************/
	public void Determination_Region1(Solution Sref) {
		// on genere k solution
            Random r=new Random();
		for (int i = 0; i < BSO.k; i++) {
			dance[i] = new Solution();
			dance[i] = Sref;
			for (int j = i+1; j <=Format.nbr_ter; j = j + filp) {
                            //dance[i].sol[j]=r.nextInt(2);

                        if (dance[i].sol[j] == 1) {
				dance[i].sol[j] = 0;
			} else {

				dance[i].sol[j] = 1;
			}
                        }

			evaluation(dance[i], Format.Doc);

                         }
	}
        /*********************************************************/
        public void Determination_Region2(Solution Sref) {
		// on genere k solution
		for (int i = 0; i < BSO.k; i++) {
			dance[i] = new Solution();
			dance[i] = Sref;
			//evaluation(dance[i], Format.t_c);
		}
	}

        public void Recherche_local_Region2(int indice) {
	Random r=new Random();
            Solution loc = new Solution();
		for (int i = 0; i < dance[indice].sol.length; i++) {
			loc = dance[indice];
                      if (Kmeans.var[i]==indice)
                           {
			/*if (loc.sol[i] == 1) {
				loc.sol[i] = 0;
			} else {
				loc.sol[i] = 1;
			}*/
                          loc.sol[i]=r.nextInt(2);
                    }
            evaluation(loc, Format.Doc);
			if (loc.cost > dance[indice].cost) {
				dance[indice] = loc;
			}
                }
    }


        public void Recherche_local_Region3(int indice) {
	Random r=new Random();
            Solution loc = new Solution();

            // compute the cost of each cluster according to the given solution
            int cost[]=new int[BSO.k];
            for (int i=0;i < BSO.k; i++)
            {
                cost[i]=0;
               for (int j=0;j<Format.nbr_doc;j++)
               {
                   if (Kmeans.kmeans_results[j]==i)
                   {
                for (int l = 1; l < 4; l++) {

				int numclau = Format.Doc[j][l];
				if ((dance[indice].sol[Math.abs(numclau)] == 1 && numclau > 0)
						|| (dance[indice].sol[Math.abs(numclau)] == 0 && numclau < 0)) {
					cost[i]++;
					l = 4;
				}
			}
                   }
               }
            }

            /// return the cluster having the minimum clusters.
            int min=cost[0];
            int indice_min=0;
            for (int h=1;h <BSO.k;h++)
            {
              if (cost[h]<min)
              {
              min=cost[h];
              indice_min=h;
              }
            }
             //System.out.println(indice_min);
		for (int i = 0; i < Format.nbr_ter; i++) {
			loc = dance[indice];
                      if (Kmeans.var[i]==indice_min)
                           {
			if (loc.sol[i] == 1) {
				loc.sol[i] = 0;
			} else {
				loc.sol[i] = 1;
			}
                          //loc.sol[i]=r.nextInt(2);
                    }
            evaluation(loc, Format.Doc);
			//if (loc.cost > dance[indice].cost) {
				dance[indice] = loc;
			//}
                }
    }

	public Solution Best_Dance() {
		Solution Best = new Solution();
		Solution.CopieSol(Best, dance[0]);
		for (int i = 1; i < dance.length; i++) {
			if (dance[i].cost > Best.cost) {
				Best = dance[i];
			}
		}
		// Solution.Affiche_solution(Best);
		return Best;
	}
 void Local_search(Solution S)
 {
Solution ng=new Solution();
float best_cst=S.cost;
int cst;
//int IMAX=100;
int i=1;
while (i<=Format.nbr_ter)
  {
  for (int j=1;j<=Format.nbr_ter;j++)
    {
    ng.sol[j]=S.sol[j];
    }
    ng.sol[i]=(S.sol[i]+1)%2;
    evaluation(ng,Format.Doc);
    //float x=(cst*100)/m;
   // printf("%f\n", x);
    if (best_cst<ng.cost)
    {
      for (int j=1;j<=Format.nbr_ter;j++)
       {
        S.sol[j]=ng.sol[j];
       }
      S.cost=ng.cost;
      best_cst=ng.cost;
    }
    i++;
}

}
}

