/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsogdir;

/**
 *
 * @author W7
 */
import java.io.BufferedReader;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Format {
    static int nbr_ter;  // number of terms
	static int nbr_doc; // number of documents
//String Terms[]=null;
static String[] Terms = new String[30];
static int [][]Doc=new int[100][30];
static int req[]=new int[30];
	

	/* Fonction de lecture de fichier cnf et extraction des informations nécessaires
	 						- Remplire la matrice des clause et le tableau de variable */

	@SuppressWarnings("unused")

/***********************initialize structure******/
public void init_struct()
{
            
for (int i=0;i<nbr_doc;i++)
{
    for (int j=0;j<nbr_ter;j++)
     {
     Doc[i][j]=0;
     }
     //relevent_docs1[i]=0;
     //relevent_docs2[i]=0;
}
             

for (int j=0;j<nbr_ter;j++)
     {
     //rules_base[j].packet=0;
     req[j]=0;
     }
}
        /****RI prototypes*********/
public void read_collection()
{
int i;

/*************for the terms file*******/

try {
    
BufferedReader f1= null;
 // fp = new File("C:\\Users\\mobntic\\Desktop\\abir\\IR\\terme.txt");
f1 = new BufferedReader(new FileReader("C:\\Users\\mobntic\\Desktop\\abir\\IR\\terme.txt"));
 Scanner scanner = new Scanner(f1);
 nbr_ter = scanner.nextInt();
  System.out.println(nbr_ter);
 i=0;
 while (true) {
				try {
                                               
                               //String s="";
                               //s=scanner.next();
                               //System.out.println(s);
                                         Terms[i]=scanner.next();
                              
					i++;
				} catch (NoSuchElementException exception) {
					break;
				}
			}

			scanner.close();

} catch (FileNotFoundException exception) {
			System.out.println("Le fichier n'a pas été trouvé");
		}
 /***********************for the request***********************/

 try {

BufferedReader f2= null;
f2 = new BufferedReader(new FileReader("C:\\Users\\mobntic\\Desktop\\abir\\IR\\request.txt"));
 Scanner scanner = new Scanner(f2);
String element;
 while (true) {
	try {
            element=scanner.next();
            int index=0;
          boolean found=false;
          while (index<nbr_ter && found==false)
           {
            if (Terms[index].compareTo(element)==0)
            {
             found=true;
            }
             else {
                 index++;
                  }
            }
            if (found==true)
              {
               req[index]=1;
              }
				} catch (NoSuchElementException exception) {
					break;
				}
			}

			scanner.close();

} catch (FileNotFoundException exception) {
			System.out.println("Le fichier n'a pas été trouvé");
		}

/***********************for the document***********************/

 try {

BufferedReader f2= null;
f2 = new BufferedReader(new FileReader("C:\\Users\\mobntic\\Desktop\\abir\\IR\\Document100.txt"));
 Scanner scanner = new Scanner(f2);
String element;
 i=0;
 nbr_doc = scanner.nextInt();
  System.out.println(nbr_doc);
 while (true) {
	try {

        element=scanner.next();
       if (element.compareTo("#")!=0)
       {
        int index=0;
        boolean found=false;
        while (index<nbr_ter && found==false)
        {
         if (Terms[index].compareTo(element)==0)
         {found=true;}
         else {index++;}
        }
        if (found==true)
        {
        Doc[i][index]++;
        }
      }
 else
            {
            i++;
            }
 }
   catch (NoSuchElementException exception) {
					break;
				}
			}

			scanner.close();

} catch (FileNotFoundException exception) {
			System.out.println("Le fichier n'a pas été trouvé");
		}


}
        

	// Fonction d'affichage de la matrice de clause et le tableau de variable

	public void affich_doc() {
		System.out.println("la recupération du fichiers terms, doc, req... ");
                 System.out.println("*************the terms*************");
		for (int i = 0; i < nbr_ter; i++) {
			System.out.println(Terms[i]);
                                                  }

                  System.out.println("*************the request*************");
		for (int i = 0; i < nbr_ter; i++) {
			System.out.println(req[i]);
                                                  }


                  System.out.println("*************the documents*************");
		for (int i = 0; i < nbr_doc; i++) {
                      for (int j = 0; j < nbr_ter; j++) {

                          if (Doc[i][j]!=0)
                          {

			System.out.print(Terms[j] +"  ");
                          }
                                                  }
                      System.out.println();

	}
      
    }
}

