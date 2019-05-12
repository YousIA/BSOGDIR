/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bsogdir;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author mobntic
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


public class FIM {
    int frequence=Format.nbr_doc/3;
List<String[]> candidates=new ArrayList<String[]>();

int frequences[]=new int[BSO.k];
static Frequent []listOfLists=new Frequent[BSO.k];

        List<Integer> freq=new ArrayList<Integer>();
         List<String[]> F=new ArrayList<String[]>();
     
void AddingElement(String s2, int iter)
{
    String s[] =s2.split("#");
int i=0;
boolean find=false;
if (iter!=0)
{
  while (find==false && i<candidates.size())
  {
  boolean equal=true;
  //String s1[]=new String[100];
  String s1[]=candidates.get(i);
  int j=0;
  while(equal==true && j!=s1.length)
  {
   if (s[j].compareTo(s1[j])!=0)
   {
   equal=false;
   }
     else
     {
     j++;
     }
  }
  if (equal==true)
  {
  find=true;
  int x=freq.remove(i);
  x++;
  freq.add(i, x);
  }
i++;
  }
 }

if (find==false)
{

candidates.add(s);
freq.add(1);
}
}

public String [] toString(int t[])
    {
String []s=new String[30];
int j=0;
for (int i = 0; i < t.length; i++) {
       if (t[i]!=0)
       {
           s[j] = Format.Terms[i];
    j++;
       }
     }
return s;

}
public int sizeDoc(int t[])
{
int nb=0;
for (int i = 0; i < t.length; i++) {

    if (t[i]!=0)
      nb++;
        }
return nb;
}
public void  freqClusters()
{
    int s;
for (int i = 0; i < BSO.k; i++) {
  s=0;
    for (int j=0;j <  Format.nbr_doc ; j++)
    {
    if (Kmeans.kmeans_results[j]==i)
     s++;
    }
frequences[i]=s;
                                }
}



    public void Generate(int cluster){

String arr[];
        int size=Format.nbr_doc;
     int entry=0;
    
    for(int i=0;i<Format.nbr_doc ;i++){
        if (Kmeans.kmeans_results[i]==cluster)
        {
        Permutation P =new Permutation();
       arr=toString(Format.Doc[i]);
        int n = sizeDoc(Format.Doc[i]);
        //System.out.println(n);
        for (int j=2; j<=n;j++)
        {
            if (j%2==0)
            {
         P.printCombination(arr,n , j);
            }
       }

        for (int index=0;index<P.cpt; index++)
            AddingElement(P.All[index], i);
      // System.out.println(P.cpt);
        //entry=entry+P.cpt;

       /* for(int m=0;m<P.cpt;m++){
        System.out.println(P.All[m]);
             }*/
        }
    }

     for (int index=0; index<candidates.size(); index++)
     {
         if ((frequences[cluster])/4 <=freq.get(index))
         {
          F.add(candidates.get(index));
         }
     }

listOfLists[cluster]=new Frequent();
//listOfLists[cluster].Lists= F;
       
     for (int index=0; index<F.size(); index++)
     {
             listOfLists[cluster].Lists.add(F.get(index));

     }

     String s[];
    System.out.println("the number of frequent terms is"+listOfLists[cluster].Lists.size());
     for (int index=0; index< listOfLists[cluster].Lists.size(); index++)
     {
         s=listOfLists[cluster].Lists.get(index);
      for (int i=0;i<s.length;i++)
         System.out.print(s[i]+ "  ");
     System.out.println("freq is: "+ freq.get(index));

     }
F.clear();
candidates.clear();
freq.clear();
}

void AllFIM()
    {
    freqClusters();
for (int i=0; i<BSO.k; i++)
{
    System.out.println("FIM of cluster" +i);
Generate(i);
}
    }



}

