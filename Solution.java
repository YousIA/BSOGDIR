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
public class Solution {
    float cost=0;
   static int l=20;
	int[] sol = new int[l];

	static public void CopieSol(Solution copie_vers, Solution copie) {
		copie_vers.cost = copie.cost;
		for (int i = 0; i < copie_vers.sol.length; i++) {
			copie_vers.sol[i] = copie.sol[i];
		}

	}

	static public void Affiche_solution(Solution sol1) {
		for (int i = 0; i < sol1.sol.length; i++) {
			System.out.print(sol1.sol[i] + " ");
		}
		System.out.print(" " + sol1.cost);
		System.out.println();
	}

}
