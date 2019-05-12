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
import java.util.ArrayList;
import java.util.Random;

public class BSOguidedKmeans {

	// int iMax = 10000;
	int imax = 30;
	Solution[] dance = new Solution[BSO.k];
	int filp = 1;
	Solution Sref;
        Float [] Prob=new Float[BSO.k];

     public void ProbabilityComputing()
    {
      
     for (int i=0; i<BSO.k;i++)
     {
       int n=0;

         for (int p=0;p<Format.nbr_ter;p++)
         {
         if (Format.req[p]==1)
         {
        for (int j=0;j<FIM.listOfLists[i].Lists.size();j++)
        {
        String []s=new String[5];
        s=FIM.listOfLists[i].Lists.get(j);
        for (int h=0;h<s.length;h++)
        {
         if (s[h].compareTo(Format.Terms[p])==0)
         {
         n++;
         }
        }
      }
      
          }
         }
       Prob[i]=(float)n/Format.nbr_ter;
   }
/*
    for (int i=0; i<BSO.k;i++)
     {
     System.out.println(Prob[i]);
    }
 * 
 */
}
	public void process() {
		// for (int imax = 1; imax <= 100; imax++) {
		long moy_temps = 0;
		float moy_taux = 0;
                float best_results=0;
		for (int m = 0; m < 1; m++) {
			long startTime = System.currentTimeMillis();
			ArrayList<Solution> liste_taboue = new ArrayList<Solution>();

			// System.out.println(" " + Format.nbr_var + " " +
			// Format.nbr_clause);
			Create_Sref();
                                int nbrItera = 0;
			Solution best_d = new Solution();
                       best_d=Sref;

			while (nbrItera < imax) { // imax
				liste_taboue.add(Sref);

				Determination_Region1(Sref);
				for (int i = 0; i < BSO.k; i++) {
					FirstHeuristic(i);
				}

                             best_d = Best_Dance();

				nbrItera++;
			}
                    //   Local_search(best_d);
			long endTime = System.currentTimeMillis();
			if (best_results<best_d.cost)
                        {best_results=best_d.cost;}
                        moy_taux = moy_taux + best_d.cost;
			moy_temps = moy_temps + (endTime - startTime);
		}
		float taux=(float) moy_taux /100;
		taux=taux/Format.nbr_doc;
                float best_taux=(float) best_results/Format.nbr_doc;
                best_taux=best_taux*100;
		taux=taux*100;
		 moy_temps = moy_temps / 100;
		System.out.println(" BSO  " + taux+ "   " + moy_temps+ "  "+ best_taux);

	}


		public void Create_Sref() {
		Sref = new Solution();
		Random r = new Random();
		for (int i = 0; i <Solution.l; i++) {
			Sref.sol[i] = r.nextInt(Format.nbr_doc);
                   //Sref.sol[i] =0;
		}
		evaluation(Sref);
	}

	public void  evaluation(Solution solution) {
		float evaluation_sol = 0;
                float inter;

		for (int i = 0; i < Solution.l; i++) {

                        inter=0;
                        float sized=0;
                        float sizereq=0;

                     int d=solution.sol[i];
                     for (int j = 0; j < Format.nbr_ter; j++) {

                         if (Format.Doc[d][j]!=0 && Format.req[j]!=0)
                         {

                          inter++;
                         }
                         if (Format.Doc[d][j]!=0)
                         {
                         sized++;
                         }
                          if (Format.req[j]!=0)
                          {
                          sizereq++;
                          }


			                                       }
                 // System.out.println("inter  "+inter);
                 // System.out.println("sized "+sized);
                 // System.out.println("sizereq "+sizereq);

                  float r=(float)(inter/(sized*sizereq));
                  //System.out.println("rapport "+r);
                  evaluation_sol=evaluation_sol+r;
                }


		solution.cost = evaluation_sol;


    }


        public void Determination_Region1(Solution Sref) {
		// on genere k solution
            Random r=new Random();
		for (int i = 0; i < BSO.k; i++) {
			dance[i] = new Solution();
			dance[i] = Sref;
			for (int j = i; j < dance[i].sol.length; j = j + filp) {
				/*if (dance[i].sol[j] == 1) {
					dance[i].sol[j] = 0;
				} else
					dance[i].sol[j] = 1;*/
                            //dance[i].sol[j]=r.nextInt(2);
                            int nb=r.nextInt(Format.nbr_doc);
                            dance[i].sol[j]=(dance[i].sol[j]+nb)%Format.nbr_doc;
			}

			evaluation(dance[i]);

                         }
	}

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
            evaluation(loc);
			if (loc.cost > dance[indice].cost) {
				dance[indice] = loc;
			}
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
int MinProb()
{
Float min=Prob[0];
int indiceMin=0;
for (int i = 1; i < BSO.k; i++) {
			if (min > Prob[i]) {
                            min=Prob[i];
	                    indiceMin=i;
			}
		}


    return indiceMin;
}


void FirstHeuristic(int bee)
 {

int indice=MinProb();
int nb=0;
boolean find=false;
while (find==false && nb <Solution.l)
{
    int doc=dance[bee].sol[nb];
if (Kmeans.kmeans_results[doc]==indice);
    {
    find=true;
    }
nb++;
}
Random r=new Random();
int x=r.nextInt(20);
                     dance[indice].sol[nb]=(dance[indice].sol[nb]+x)%Format.nbr_doc;
                                 evaluation(dance[indice]);

}

void SecondHeuristic()
    {

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
    evaluation(ng);
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


