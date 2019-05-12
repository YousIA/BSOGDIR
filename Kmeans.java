/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsogdir;
import java.util.*;
/**
 *
 * @author W7
 */
public class Kmeans {
    static int kmeans_results[]=new int[Format.nbr_doc];
static int var[]= new int[Format.nbr_ter+1];
 Centre centers[]=new Centre[BSO.k];
 int Documents[][]=new int[Format.nbr_doc][Format.nbr_ter+1];

 /***********/
 void grouped_terms()
{
        int freq[]=new int[BSO.k];
for (int i=0;i<Format.nbr_ter;i++)
{

   for (int l=0;l<BSO.k;l++)
   {
       freq[i]=0;
   }
   for (int j=0;j<Format.nbr_doc;j++)
  {
    if (Documents[j][i]!=0)
    {
     int indice=kmeans_results[j];
     freq[indice]++;
    }
  }

   int max=freq[0];
   int indice_max=0;
      for (int s=1;s<BSO.k;s++)
  {
    if (max<freq[s])
    {
   max=freq[s];
    indice_max=s;
    }
  }
   var[i]=indice_max;
}
}
 /*****************************/
 void transf()
{

   for (int i=0;i<Format.nbr_doc;i++)
   {
    for (int j=0;j<Format.nbr_ter;j++)
     {
     Documents[i][j]=Format.Doc[i][j];
     }    
     
   }
}

/******************************/
void init_kmeans()
{
    Random r=new Random();
for (int i=0;i<Format.nbr_doc;i++)
{
kmeans_results[i]=r.nextInt(BSO.k);
}
}
/*****************************calculate distance bettween two clauses*****************************************/
int distance(int t[], int v[])
{
int nvc=0;
  for (int i=0;i<Format.nbr_ter; i++)
   {
    if ((t[i]!=0 && v[i]!=0))
      {
      nvc++;
      }
   }
   int d=Format.nbr_ter-nvc;
   return d;
}
/*******************************/
/***************initaliser_ centers**********************/
void initialiser_centers()
{
for (int i=0;i<BSO.k;i++)
  {
      centers[i]=new Centre();
      for (int j=0;j<Format.nbr_ter;j++)
      {

	   centers[i].centre[j]=Documents[i][j];
      }
  kmeans_results[i]=i;
  }
}
/*****************************affectation des clusters************/
void assigned_clusters()
{
for (int i=0;i<Format.nbr_doc;i++)
{
    //float min=Manhattan(T_objet[i], centers[0]);
	//float min=Chebychev(T_objet[i], centers[0]);
//	printf("%f ",min);
    //float min=Euclidian(T_objet[i], centers[0]);
	//float min=Euclidian_squared(T_objet[i], centers[0]);
	//float min=Dist_aicha(T_objet[i], centers[0]);
	float min=distance(Documents[i], centers[0].centre);
    int indice=0;
	for (int j=1;j<BSO.k;j++)
    {
	//float d=Manhattan(T_objet[i], centers[j]);
	//float d=Chebychev(T_objet[i], centers[j]);
	//printf("%f ",d);
	//float d=Euclidian(T_objet[i], centers[j]);
	//float d=Euclidian_squared(T_objet[i], centers[j]);
	//float d=Dist_aicha(T_objet[i], centers[j]);
	float d=distance(Documents[i], centers[j].centre);
		if (d<min){indice=j;}
    }
    //printf("\n");
kmeans_results[i]=indice;
}
}
/****************calculate center computation of clauses*******/
void FC(int cluster)
{
	int nbr=0;
	int s=0;
	int card_center=0;
	Centre obj_temp;
        obj_temp=new Centre();
	for (int j=0;j<Format.nbr_ter;j++)
    {
		obj_temp.centre[j]=0;
		centers[cluster].centre[j]=0;
    }
  for (int i=0;i<Format.nbr_doc; i++)
{

    if (kmeans_results[i]==cluster)
    {
    	for (int j=0;j<Format.nbr_ter;j++)
        {
    		if (Documents[i][j]!=0)
    		{
    			s++;
    			obj_temp.centre[j]++;
    		}
        }
    	nbr++;
    }
}
  if (nbr!=0)
  {
   card_center=s/nbr;
  }

for (int i=0;i<card_center;i++)
{
	int max=obj_temp.centre[0];
    int indice_max=0;
	  for (int j=1;j<Format.nbr_ter;j++)
	  {
		  if (max<obj_temp.centre[j])
		  {
			  max=obj_temp.centre[j];
			  indice_max=j;
		  }

	  }
   centers[cluster].centre[indice_max]=1;
   obj_temp.centre[indice_max]=0;
}
}
/***************************Kmeans_function*****************************************/
void Kmeans_function()
{
int iteration=0;
transf();
initialiser_centers();
assigned_clusters();
while (iteration<2)
{
for (int i=0;i<BSO.k;i++)
{
  //classical_center_computation(i);
FC(i);
 //Uoi(i);
  //Iuc(i);
 //Uic(i);
}
assigned_clusters();
iteration++;
//printf("\n\t%d",iteration);
}
}
float euclidian(Objects O1, Objects O2)
{
float sum=(float)Math.pow((double)(O1.x-O2.x), 2);
float d=(float)Math.sqrt(sum);
return(d);
}
void Display_Kmeans_results()
{
System.out.println("*************the documents*************");


         for (int i = 0; i < BSO.k; i++) {
             System.out.println("Cluser "+i);
                      for (int j = 0; j < Format.nbr_doc; j++) {

                          if (kmeans_results[j]==i)
                          {

			System.out.print(j +"  ");
                          }
                                                  }
                      System.out.println();

	}

}
}
